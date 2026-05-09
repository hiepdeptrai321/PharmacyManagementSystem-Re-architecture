package com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc;

import com.example.pharmacy.common.model.ChiTietHoatChat;
import com.example.pharmacy.common.model.HoatChat;
import com.example.pharmacy.common.model.KeHang;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.NhomDuocLy;
import com.example.pharmacy.common.model.Thuoc_SanPham;
import com.example.pharmacy.client.service.KeHangService;
import com.example.pharmacy.client.service.NhomDuocLyService;
import com.example.pharmacy.client.service.ThuocService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SuaXoaThuoc_Ctrl {
    public TextField txtTenThuoc;
    public ComboBox<String> cbxLoaiHang;
    public ComboBox<String> cbxViTri;
    public TextField txtHamLuong;
    public TextField txtHangSanXuat;
    public ComboBox<String> cbxNhomDuocLy;
    public TextField txtNuocSanXuat;
    public TextField txtQuyCachDongGoi;
    public TextField txtSDK_GPNK;
    public CheckBox cbETC;
    public TableView<ChiTietHoatChat> tblHoatChat;
    public TableColumn<ChiTietHoatChat, String> colMaHoatChat;
    public TableColumn<ChiTietHoatChat, String> colTenHoatChat;
    public TableColumn<ChiTietHoatChat, Float> colHamLuong;
    public TableColumn<ChiTietHoatChat, String> colXoa;
    public TextField txtDuongDung;
    public TextField txtDonViHamLuong;
    public ListView<HoatChat> listViewHoatChat;
    public TextField txtTimKiemHoatChat;
    public TextField txtMaThuoc;
    public ImageView imgThuoc_SanPham;
    public Button btnXoa;
    public Button btnHuy;
    public Button btnLuu;
    public Button btnChonAnh;

    private final ThuocService thuocService = new ThuocService();
    private final KeHangService keHangService = new KeHangService();
    private final NhomDuocLyService nhomDuocLyService = new NhomDuocLyService();

    private ObservableList<HoatChat> allHoatChat;
    private Thuoc_SanPham thuocTempDeXemSoLuongTon;
    private DanhMucThuoc_Ctrl danhMucThuoc_Ctrl;

    public void initialize() {
        if (listViewHoatChat != null) {
            listViewHoatChat.setVisible(false);
        }

        listView();

        if (txtTimKiemHoatChat != null) {
            txtTimKiemHoatChat.textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && !newVal.trim().isEmpty()) {
                    listViewHoatChat.setVisible(true);
                    locDanhSachHoatChat(newVal);
                } else {
                    listViewHoatChat.setVisible(false);
                }
            });
        }

        if (listViewHoatChat != null) {
            listViewHoatChat.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null || thuocTempDeXemSoLuongTon == null || tblHoatChat == null) {
                    Platform.runLater(() -> {
                        listViewHoatChat.getSelectionModel().clearSelection();
                        listViewHoatChat.setVisible(false);
                    });
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
                    chiTietHoatChat.setThuoc(thuocTempDeXemSoLuongTon);

                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Nhập hàm lượng");
                    dialog.setHeaderText("Vui lòng nhập hàm lượng cho hoạt chất: " + hoatChat.getTenHoatChat());
                    dialog.setContentText("Hàm lượng:");
                    dialog.showAndWait().ifPresent(hamLuong -> {
                        try {
                            chiTietHoatChat.setHamLuong(Float.parseFloat(hamLuong));
                            tblHoatChat.getItems().add(chiTietHoatChat);
                        } catch (NumberFormatException ex) {
                            showErr("Hàm lượng không hợp lệ! Vui lòng nhập số.");
                        }
                    });
                } else {
                    showInfo("Hoạt chất đã tồn tại trong danh sách!");
                }

                Platform.runLater(() -> {
                    listViewHoatChat.getSelectionModel().clearSelection();
                    listViewHoatChat.refresh();
                });
            });
        }

        btnLuu.setOnAction(event -> btnCapNhat());
        btnHuy.setOnAction(event -> btnHuy());
        btnXoa.setOnAction(event -> btnXoa());
        btnChonAnh.setOnAction(event -> chonFile());
    }

    public void load(Thuoc_SanPham thuoc) {
        if (tblHoatChat == null || colMaHoatChat == null || colTenHoatChat == null || colHamLuong == null || colXoa == null) {
            throw new IllegalStateException("Hãy gọi gui.showWithController(stage, ctrl) để inject UI trước, rồi mới gọi ctrl.load(thuoc).");
        }

        thuocTempDeXemSoLuongTon = thuoc;
        tblHoatChat.setEditable(true);
        colHamLuong.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colHamLuong.setOnEditCommit(event -> event.getRowValue().setHamLuong(event.getNewValue()));

        cbxLoaiHang.getItems().setAll("Chọn loại hàng");
        cbxLoaiHang.getItems().addAll(thuocService.getAllLoaiHang());

        cbxViTri.getItems().setAll("Chọn vị trí");
        cbxViTri.getItems().addAll(keHangService.getAllTenKe());

        cbxNhomDuocLy.getItems().setAll("Chọn nhóm dược lý");
        cbxNhomDuocLy.getItems().addAll(nhomDuocLyService.getAllTenNhomDuocLy());

        loadDuLieuThuoc(thuoc);
    }

    public void loadDuLieuThuoc(Thuoc_SanPham thuoc) {
        txtMaThuoc.setText(thuoc.getMaThuoc());
        txtTenThuoc.setText(thuoc.getTenThuoc());
        cbxLoaiHang.setValue(thuoc.getLoaiHang() != null ? thuoc.getLoaiHang().getTenLoaiHang() : "Chọn loại hàng");
        cbxViTri.setValue(thuoc.getVitri() != null ? thuoc.getVitri().getTenKe() : "Chọn vị trí");
        txtHamLuong.setText(String.valueOf(thuoc.getHamLuong()));
        txtHangSanXuat.setText(thuoc.getHangSX());
        txtDonViHamLuong.setText(thuoc.getDonViHamLuong());
        txtDuongDung.setText(thuoc.getDuongDung());
        cbETC.setSelected(thuoc.isETC());
        cbxNhomDuocLy.setValue(thuoc.getNhomDuocLy() != null ? thuoc.getNhomDuocLy().getTenNDL() : "Chọn nhóm dược lý");
        txtNuocSanXuat.setText(thuoc.getNuocSX());
        txtQuyCachDongGoi.setText(thuoc.getQuyCachDongGoi());
        txtSDK_GPNK.setText(thuoc.getSDK_GPNK());

        try {
            if (thuoc.getHinhAnh() == null) {
                imgThuoc_SanPham.setImage(new Image(
                        getClass().getResource("/com/example/pharmacy/client/img/noimage.jpg").toExternalForm()));
            } else {
                imgThuoc_SanPham.setImage(new Image(new ByteArrayInputStream(thuoc.getHinhAnh())));
            }
        } catch (Exception e) {
            imgThuoc_SanPham.setImage(new Image(
                    getClass().getResource("/com/example/pharmacy/client/img/noimage.jpg").toExternalForm()));
        }

        List<ChiTietHoatChat> listHoatChat = thuocService.findChiTietHoatChatByMaThuoc(thuoc.getMaThuoc());
        ObservableList<ChiTietHoatChat> data = FXCollections.observableArrayList(listHoatChat);
        colMaHoatChat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHoatChat().getMaHoatChat()));
        colTenHoatChat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHoatChat().getTenHoatChat()));
        colHamLuong.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getHamLuong()));
        colXoa.setCellFactory(celldata -> new TableCell<>() {
            private final Button btn = new Button("Xóa");

            {
                btn.setOnAction(event -> {
                    ChiTietHoatChat chiTietHoatChat = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(chiTietHoatChat);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tblHoatChat.setItems(data);
    }

    public void listView() {
        List<HoatChat> listHoatChat = thuocService.getAllHoatChat();
        allHoatChat = FXCollections.observableArrayList(listHoatChat);
        listViewHoatChat.setItems(allHoatChat);
        listViewHoatChat.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(HoatChat item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaHoatChat() + " - " + item.getTenHoatChat());
            }
        });
    }

    private void locDanhSachHoatChat(String keywordRaw) {
        if (keywordRaw == null || keywordRaw.isEmpty()) {
            Platform.runLater(() -> listViewHoatChat.setItems(allHoatChat));
            return;
        }
        String keyword = keywordRaw.toLowerCase();
        ObservableList<HoatChat> filtered = FXCollections.observableArrayList();
        for (HoatChat hoatChat : allHoatChat) {
            if (hoatChat.getMaHoatChat().toLowerCase().contains(keyword)
                    || hoatChat.getTenHoatChat().toLowerCase().contains(keyword)) {
                filtered.add(hoatChat);
            }
        }
        Platform.runLater(() -> listViewHoatChat.setItems(filtered.isEmpty()
                ? FXCollections.observableArrayList()
                : filtered));
    }

    public void btnCapNhat() {
        if (btnLuu == null || btnLuu.getScene() == null) {
            return;
        }

        AnchorPane root = (AnchorPane) btnLuu.getScene().getRoot();
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
        overlay.getChildren().add(new ProgressIndicator());
        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);
        root.getChildren().add(overlay);

        if (!kiemTraHopLe()) {
            root.getChildren().remove(overlay);
            return;
        }

        new Thread(() -> {
            Thuoc_SanPham thuoc = new Thuoc_SanPham();
            thuoc.setMaThuoc(txtMaThuoc.getText());
            thuoc.setTenThuoc(txtTenThuoc.getText().trim());
            thuoc.setLoaiHang(resolveLoaiHang());
            thuoc.setVitri(resolveKeHang());
            thuoc.setHamLuong(Float.parseFloat(txtHamLuong.getText().trim()));
            thuoc.setHangSX(txtHangSanXuat.getText().trim());
            thuoc.setDonViHamLuong(txtDonViHamLuong.getText().trim());
            thuoc.setDuongDung(txtDuongDung.getText().trim());
            thuoc.setNhomDuocLy(resolveNhomDuocLy());
            thuoc.setNuocSX(txtNuocSanXuat.getText().trim());
            thuoc.setQuyCachDongGoi(txtQuyCachDongGoi.getText().trim());
            thuoc.setSDK_GPNK(txtSDK_GPNK.getText().trim());
            thuoc.setETC(cbETC.isSelected());
            thuoc.setHinhAnh(extractImageBytes());

            List<ChiTietHoatChat> chiTietHoatChats = new ArrayList<>(tblHoatChat.getItems());
            boolean success = thuocService.update(thuoc, chiTietHoatChats);

            Platform.runLater(() -> {
                root.getChildren().remove(overlay);
                if (success) {
                    if (danhMucThuoc_Ctrl != null) {
                        danhMucThuoc_Ctrl.refestTable();
                    }
                    dong();
                } else {
                    showErr("Không thể cập nhật thuốc. Vui lòng kiểm tra lại kết nối server hoặc dữ liệu nhập.");
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

    private void dong() {
        Stage stage = (Stage) txtMaThuoc.getScene().getWindow();
        stage.close();
    }

    public void btnHuy() {
        dong();
    }

    public void setParent(DanhMucThuoc_Ctrl parent) {
        danhMucThuoc_Ctrl = parent;
    }

    public void btnXoa() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xóa thuốc này?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn != ButtonType.YES || thuocTempDeXemSoLuongTon == null) {
                return;
            }

            int soLuongTon = thuocService.getTongSoLuongTonByMaThuoc(thuocTempDeXemSoLuongTon.getMaThuoc());
            if (soLuongTon > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận xóa");
                alert.setHeaderText(null);
                alert.setContentText("Thuốc " + thuocTempDeXemSoLuongTon.getTenThuoc() + " hiện đang có tồn kho. Bạn vẫn muốn xóa?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        xoaThuoc();
                    }
                });
            } else {
                xoaThuoc();
            }
        });
    }

    private void xoaThuoc() {
        if (thuocTempDeXemSoLuongTon == null) {
            return;
        }
        boolean success = thuocService.softDelete(thuocTempDeXemSoLuongTon.getMaThuoc());
        if (success) {
            if (danhMucThuoc_Ctrl != null) {
                danhMucThuoc_Ctrl.refestTable();
            }
            dong();
        } else {
            showErr("Không thể xóa thuốc. Vui lòng kiểm tra lại kết nối server.");
        }
    }

    public void chonFile() {
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

    public boolean kiemTraHopLe() {
        if (txtTenThuoc.getText().isEmpty()) {
            showErr("Tên thuốc không được để trống!");
            return false;
        } else if (txtHamLuong.getText().isEmpty()) {
            showErr("Hàm lượng không được để trống!");
            return false;
        } else if (!txtHamLuong.getText().matches("\\d+(\\.\\d+)?")) {
            showErr("Hàm lượng không hợp lệ! Vui lòng nhập số.");
            return false;
        } else if (txtDonViHamLuong.getText().isEmpty()) {
            showErr("Đơn vị hàm lượng không được để trống!");
            return false;
        } else if (txtDuongDung.getText().isEmpty()) {
            showErr("Đường dùng không được để trống!");
            return false;
        } else if (txtSDK_GPNK.getText().isEmpty()) {
            showErr("SĐK/GPNK không được để trống!");
            return false;
        }
        return true;
    }

    private void showErr(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
