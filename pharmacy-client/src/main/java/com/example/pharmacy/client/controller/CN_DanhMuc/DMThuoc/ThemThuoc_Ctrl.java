package com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc;

import com.example.pharmacy.common.model.ChiTietHoatChat;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.HoatChat;
import com.example.pharmacy.common.model.KeHang;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.NhomDuocLy;
import com.example.pharmacy.common.model.Thuoc_SanPham;
import com.example.pharmacy.client.service.DonViTinhService;
import com.example.pharmacy.client.service.KeHangService;
import com.example.pharmacy.client.service.NhomDuocLyService;
import com.example.pharmacy.client.service.ThuocService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemThuoc_Ctrl {

    public Button btnHuy;
    public Button btnThem;
    public TextField txtTenThuoc;
    public ComboBox<String> cbxLoaiHang;
    public ComboBox<String> cbxViTri;
    public TextField txtMaThuoc;
    public ImageView imgThuoc_SanPham;
    public TextField txtDonViHamLuong;
    public TextField txtHamLuong;
    public TextField txtHangSanXuat;
    public ComboBox<String> cbxNhomDuocLy;
    public TextField txtNuocSanXuat;
    public TextField txtQuyCachDongGoi;
    public TextField txtSDK_GPNK;
    public TextField txtDuongDung;
    public CheckBox cbETC;
    public ComboBox<String> cbDVTCB;
    public TableColumn<ChiTietHoatChat, String> colMaHoatChat;
    public TableColumn<ChiTietHoatChat, String> colTenHoatChat;
    public TableColumn<ChiTietHoatChat, String> colHamLuong;
    public TableColumn<ChiTietHoatChat, String> colXoa;
    public TextField txtTimKiemHoatChat;
    public ListView<HoatChat> listViewHoatChat;
    public TableView<ChiTietHoatChat> tblHoatChat;
    public Button btnChonFile;
    public Button btnLuu;

    private final ThuocService thuocService = new ThuocService();
    private final KeHangService keHangService = new KeHangService();
    private final NhomDuocLyService nhomDuocLyService = new NhomDuocLyService();
    private final DonViTinhService donViTinhService = new DonViTinhService();

    private ObservableList<HoatChat> allHoatChat;
    private final List<ChiTietHoatChat> listChiTietHoatChat = new ArrayList<>();
    private DanhMucThuoc_Ctrl parentController;
    private final Thuoc_SanPham thuocThem = new Thuoc_SanPham();

    public void initialize() {
        cbxLoaiHang.getItems().add("Chọn loại hàng");
        cbxViTri.getItems().add("Chọn vị trí");
        cbxNhomDuocLy.getItems().add("Chọn nhóm dược lý");
        cbxLoaiHang.getItems().addAll(thuocService.getAllLoaiHang());
        cbxViTri.getItems().addAll(keHangService.getAllTenKe());
        cbxNhomDuocLy.getItems().addAll(nhomDuocLyService.getAllTenNhomDuocLy());
        cbxLoaiHang.getSelectionModel().selectFirst();
        cbxViTri.getSelectionModel().selectFirst();
        cbxNhomDuocLy.getSelectionModel().selectFirst();

        for (DonViTinh donViTinh : donViTinhService.findAll()) {
            cbDVTCB.getItems().add(donViTinh.getTenDonViTinh());
        }

        loadBangThuoc();

        listViewHoatChat.setVisible(false);
        listView();
        txtTimKiemHoatChat.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.trim().isEmpty()) {
                listViewHoatChat.setVisible(true);
                locDanhSachHoatChat(newVal);
            } else {
                listViewHoatChat.setVisible(false);
            }
        });

        listViewHoatChat.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                return;
            }
            HoatChat hoatChat = newVal;
            txtTimKiemHoatChat.clear();
            listViewHoatChat.setVisible(false);

            boolean notExists = tblHoatChat.getItems().stream()
                    .noneMatch(item -> item.getHoatChat() != null
                            && item.getHoatChat().getMaHoatChat().equals(hoatChat.getMaHoatChat()));

            if (notExists) {
                ChiTietHoatChat chiTietHoatChat = new ChiTietHoatChat();
                chiTietHoatChat.setHoatChat(hoatChat);

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Nhập hàm lượng");
                dialog.setHeaderText("Vui lòng nhập hàm lượng cho hoạt chất: " + hoatChat.getTenHoatChat());
                dialog.setContentText("Hàm lượng:");
                dialog.showAndWait().ifPresent(hamLuong -> {
                    if (!hamLuong.matches("\\d+(\\.\\d+)?")) {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Hàm lượng không hợp lệ! Vui lòng nhập số.");
                        return;
                    }
                    chiTietHoatChat.setHamLuong(Float.parseFloat(hamLuong));
                    listChiTietHoatChat.add(chiTietHoatChat);
                    tblHoatChat.getItems().add(chiTietHoatChat);
                });

                Platform.runLater(() -> {
                    listViewHoatChat.getSelectionModel().clearSelection();
                    listViewHoatChat.refresh();
                });
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Hoạt chất đã tồn tại trong danh sách!");
                Platform.runLater(() -> {
                    listViewHoatChat.getSelectionModel().clearSelection();
                    listViewHoatChat.refresh();
                });
            }
        });

        txtMaThuoc.setText(thuocService.generateNewMaThuoc());
    }

    public void loadBangThuoc() {
        colMaHoatChat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoatChat().getMaHoatChat()));
        colTenHoatChat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoatChat().getTenHoatChat()));
        colHamLuong.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHamLuong())));
        colXoa.setCellFactory(celldata -> new TableCell<>() {
            private final Button btn = new Button("Xóa");

            {
                btn.setOnAction(event -> {
                    ChiTietHoatChat chiTietHoatChat = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(chiTietHoatChat);
                    listChiTietHoatChat.removeIf(item ->
                            item.getHoatChat().getMaHoatChat().equals(chiTietHoatChat.getHoatChat().getMaHoatChat()));
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    public void btnHuyClick() {
        dong();
    }

    public void chonFile(ActionEvent actionEvent) {
        Stage stage = (Stage) txtMaThuoc.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh thuốc");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgThuoc_SanPham.setImage(image);
        }
    }

    public void btnThemThuoc(ActionEvent actionEvent) {
        if (!kiemTraHopLe()) {
            return;
        }

        thuocThem.setMaThuoc(txtMaThuoc.getText());
        thuocThem.setTenThuoc(txtTenThuoc.getText());
        thuocThem.setHamLuong(Float.parseFloat(txtHamLuong.getText()));
        thuocThem.setDonViHamLuong(txtDonViHamLuong.getText());
        thuocThem.setHangSX(txtHangSanXuat.getText());
        thuocThem.setNhomDuocLy(resolveNhomDuocLy());
        thuocThem.setNuocSX(txtNuocSanXuat.getText());
        thuocThem.setQuyCachDongGoi(txtQuyCachDongGoi.getText());
        thuocThem.setSDK_GPNK(txtSDK_GPNK.getText());
        thuocThem.setDuongDung(txtDuongDung.getText());
        thuocThem.setLoaiHang(resolveLoaiHang());
        thuocThem.setVitri(resolveKeHang());
        thuocThem.setETC(cbETC.isSelected());
        thuocThem.setHinhAnh(extractImageBytes());

        DonViTinh donViTinhCoBan = cbDVTCB.getSelectionModel().getSelectedItem() == null
                ? null
                : donViTinhService.selectByTenDVT(cbDVTCB.getSelectionModel().getSelectedItem());

        AnchorPane root = (AnchorPane) btnThem.getScene().getRoot();
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
        overlay.getChildren().add(new ProgressIndicator());
        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);
        root.getChildren().add(overlay);

        new Thread(() -> {
            boolean success = thuocService.create(
                    thuocThem,
                    new ArrayList<>(listChiTietHoatChat),
                    donViTinhCoBan != null ? donViTinhCoBan.getMaDVT() : null
            );

            Platform.runLater(() -> {
                root.getChildren().remove(overlay);
                if (success) {
                    if (parentController != null) {
                        parentController.refestTable();
                    }
                    dong();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm thuốc. Vui lòng kiểm tra lại kết nối server hoặc dữ liệu nhập.");
                }
            });
        }).start();
    }

    private byte[] extractImageBytes() {
        Image image = imgThuoc_SanPham.getImage();
        if (image == null) {
            return null;
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LoaiHang resolveLoaiHang() {
        String tenLoaiHang = cbxLoaiHang.getSelectionModel().getSelectedItem();
        if (tenLoaiHang == null || "Chọn loại hàng".equals(tenLoaiHang)) {
            return null;
        }
        return thuocService.findByTenLoaiHang(tenLoaiHang);
    }

    private KeHang resolveKeHang() {
        String tenKe = cbxViTri.getSelectionModel().getSelectedItem();
        if (tenKe == null || "Chọn vị trí".equals(tenKe)) {
            return null;
        }
        return keHangService.selectByTenKe(tenKe);
    }

    private NhomDuocLy resolveNhomDuocLy() {
        String tenNhomDuocLy = cbxNhomDuocLy.getSelectionModel().getSelectedItem();
        if (tenNhomDuocLy == null || "Chọn nhóm dược lý".equals(tenNhomDuocLy)) {
            return null;
        }
        return nhomDuocLyService.selectByTenNhomDuocLy(tenNhomDuocLy);
    }

    public void listView() {
        List<HoatChat> listHoatChat = thuocService.getAllHoatChat();
        allHoatChat = FXCollections.observableArrayList(listHoatChat);
        listViewHoatChat.setItems(allHoatChat);
        listViewHoatChat.setCellFactory(data -> new ListCell<>() {
            @Override
            protected void updateItem(HoatChat item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getMaHoatChat() + " - " + item.getTenHoatChat());
                }
            }
        });
    }

    private void locDanhSachHoatChat(String keywordRaw) {
        if (keywordRaw == null || keywordRaw.isEmpty()) {
            Platform.runLater(() -> listViewHoatChat.setItems(allHoatChat));
            return;
        }

        String keyword = keywordRaw.toLowerCase();
        ObservableList<HoatChat> danhSachHoatChatDaLoc = FXCollections.observableArrayList();
        for (HoatChat hoatChat : allHoatChat) {
            if (hoatChat.getMaHoatChat().toLowerCase().contains(keyword)
                    || hoatChat.getTenHoatChat().toLowerCase().contains(keyword)) {
                danhSachHoatChatDaLoc.add(hoatChat);
            }
        }

        Platform.runLater(() -> listViewHoatChat.setItems(
                danhSachHoatChatDaLoc.isEmpty() ? FXCollections.observableArrayList() : danhSachHoatChatDaLoc));
    }

    public boolean kiemTraHopLe() {
        if (txtTenThuoc.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Tên thuốc không được để trống!");
            return false;
        } else if (txtHamLuong.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Hàm lượng không được để trống!");
            return false;
        } else if (!txtHamLuong.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Hàm lượng không hợp lệ! Vui lòng nhập số.");
            return false;
        } else if (txtDonViHamLuong.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Đơn vị hàm lượng không được để trống!");
            return false;
        } else if (txtDuongDung.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Đường dùng không được để trống!");
            return false;
        } else if (txtSDK_GPNK.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "SĐK/GPNK không được để trống!");
            return false;
        } else if (cbDVTCB.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn đơn vị tính cơ bản!");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setParent(DanhMucThuoc_Ctrl parent) {
        parentController = parent;
    }

    public Thuoc_SanPham getThuocThem() {
        return thuocThem;
    }

    public void dong() {
        Stage stage = (Stage) btnThem.getScene().getWindow();
        stage.close();
    }
}
