package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.VNDFormatter;
import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.ThemNhaCungCap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContextMapper;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.ThemNhaCungCap_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LapPhieuNhapHang_Ctrl{

    //  1. KHAI BÁO THÀNH PHẦN GIAO DIỆN (FXML)
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colSTT;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colMaThuoc;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colTenThuoc;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colLoHang;
    public TableColumn<CTPN_TSPTL_CHTDVT, LocalDate> colHanSuDung;
    public TableColumn<CTPN_TSPTL_CHTDVT, Integer> colSoLuong;
    public TableColumn<CTPN_TSPTL_CHTDVT, Double> colDonGiaNhap;
    public TableColumn<CTPN_TSPTL_CHTDVT, Float> colChietKhau;
    public TableColumn<CTPN_TSPTL_CHTDVT, Float> colThue;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colXoa;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colDonViNhap;
    public TableColumn<CTPN_TSPTL_CHTDVT, LocalDate> colNSX;
    public TableColumn<CTPN_TSPTL_CHTDVT, String> colThanhTien;
    public TableView<CTPN_TSPTL_CHTDVT> tblNhapThuoc;
    public ComboBox<String> cbxNCC;
    public TextField txtMaPhieuNhap;
    public DatePicker txtNgayNhap;
    public TextArea txtGhiChu;
    public TextField txtTongGiaNhap;
    public TextField txtTongTienChietKhau;
    public TextField txtTongTienThue;
    public TextField txtThanhTien;
    public TextField txtTimKiemChiTietDonViTinh;
    public ListView<String> listViewNhaCungCap;
    public ListView<ChiTietDonViTinh> listViewChiTietDonViTinh;

    //  2. KHAI BÁO BIẾN TOÀN CỤC
    private ObservableList<ChiTietDonViTinh> allChiTietDonViTinh;
    private ObservableList<NhaCungCap> listNCC;
    private NhaCungCap ncc;
    private int maLoHienTai = 0;
    private ChiTietPhieuNhap_Dao ctpn_dao;
    private ObservableList<CTPN_TSPTL_CHTDVT> listNhapThuoc;
    private PhieuNhap_Dao phieuNhapDao;

    //  3. PHƯƠNG THỨC KHỞI TẠO
    public void initialize() {
        loadTable();
        taiDanhSachNCC();

        ncc = new  NhaCungCap();
        ctpn_dao = new ChiTietPhieuNhap_Dao();
        phieuNhapDao = new PhieuNhap_Dao();
        listNhapThuoc = FXCollections.observableArrayList();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                String lastMaLH = ctpn_dao.generateMaLH();
                int maLoTmp = 0;
                if (lastMaLH != null && lastMaLH.startsWith("LH")) {
                    maLoTmp = Integer.parseInt(lastMaLH.substring(2)) - 1;
                }
                final int maLoFinal = maLoTmp;

                Platform.runLater(() -> maLoHienTai = maLoFinal);
                return null;
            }
        };
        new Thread(task).start();


        setLoading(txtTimKiemChiTietDonViTinh,true);
        Platform.runLater(this::loadDataAsync);

        timKiemNhaCungCap();
        timKiemDonViTinh();
        suKienThemChiTietDonViTinhVaoBang();
        suKienThemMotDongMoiVaoBang();
        listenerListNhapThuoc();
    }


    private void loadDataAsync() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                var data = new ChiTietDonViTinh_Dao().selectAll();
                Platform.runLater(() -> {
                    allChiTietDonViTinh = FXCollections.observableArrayList(data);
                    listViewChiTietDonViTinh.setItems(allChiTietDonViTinh);
                    setLoading(txtTimKiemChiTietDonViTinh,false);
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    public void setLoading(TextField tf, boolean loading) {
        if (loading) {
            tf.setDisable(true);
            tf.setPromptText("Đang xử lý...");
        } else {
            tf.setDisable(false);
            tf.setPromptText("");
        }
    }

    //  4. PHƯƠNG THỨC XỬ LÝ SỰ KIỆN VÀ HÀM HỖ TRỢ
//  4.1. Tải danh sách nhà cung cấp vào ComboBox
    public void taiDanhSachNCC() {

//      lấy danh sách nhà cung cấp từ cơ sở dữ liệu
        listNCC = FXCollections.observableArrayList(new NhaCungCap_Dao().selectAll());
        ObservableList<String> nccList = FXCollections.observableArrayList();

//      thêm nhà cung cấp vào danh sách hiển thị
        for (NhaCungCap ncc : listNCC) {
            nccList.add(ncc.getMaNCC() + " - " + ncc.getTenNCC());
        }

//      thiết lập danh sách cho ComboBox
        cbxNCC.setItems(nccList);
        cbxNCC.setEditable(true);
    }

    //  4.2. Tìm kiếm nhà cung cấp trong ComboBox
    public void timKiemNhaCungCap() {

//      Chỉnh style cho list view nhà cung cấp
        listViewNhaCungCap.setVisible(false);
        listViewNhaCungCap.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; " + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 6, 0, 0, 2);" + "-fx-border-color: #cccccc;" + "-fx-border-width: 1;");
        listViewNhaCungCap.setFocusTraversable(false);

//      =======================Thêm listener cho giá trị được chọn trong ComboBox
        cbxNCC.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cbxNCC.getEditor().setText(newVal);

//              Tìm nhà cung cấp tương ứng với giá trị được chọn
                ncc = listNCC.stream()
                        .filter(item -> (item.getMaNCC() + " - " + item.getTenNCC()).equals(newVal))
                        .findFirst()
                        .orElse(null);
                listViewNhaCungCap.setVisible(false);
            }
        });

//      Thêm listener cho txt của ComboBox
        cbxNCC.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {

//          Nếu txt rỗng thì ẩn list view
            if (newVal == null || newVal.trim().isEmpty()) {
                listViewNhaCungCap.setVisible(false);
                return;
            }

//          Ẩn cbx khi mà đang hiển thị list view
            cbxNCC.hide();

//          Lọc danh sách nhà cung cấp dựa trên từ khóa nhập vào
            String keyword = newVal.toLowerCase().trim();
            ObservableList<String> danhSachDaLoc = FXCollections.observableArrayList();

//          Lặp qua tất cả nhà cung cấp và thêm vào danh sách lọc nếu khớp với từ khóa
            for (NhaCungCap ncc : listNCC) {
                String nccDaLoc = ncc.getMaNCC() + " - " + ncc.getTenNCC();
                if (nccDaLoc.toLowerCase().contains(keyword)) {
                    danhSachDaLoc.add(nccDaLoc);
                }
            }

//          Cập nhật danh sách hiển thị trong list view
            listViewNhaCungCap.setItems(danhSachDaLoc);

//          Nếu ko có kết quả thì ẩn list view
            listViewNhaCungCap.setVisible(!danhSachDaLoc.isEmpty());
        });

//      Xử lý sự kiện khi chọn một nhà cung cấp từ list view
        listViewNhaCungCap.setOnMouseClicked(e -> {
            String selected = listViewNhaCungCap.getSelectionModel().getSelectedItem();
            if (selected != null) {
                cbxNCC.getEditor().setText(selected);
                listViewNhaCungCap.setVisible(false);

//              Tìm nhà cung cấp tương ứng với giá trị được chọn
                ncc = listNCC.stream()
                        .filter(item -> (item.getMaNCC() + " - " + item.getTenNCC()).equals(selected))
                        .findFirst()
                        .orElse(null);

//              Nếu tìm thấy thì hiển thị trong txt của cbxNCC
                if (ncc != null) {
                    cbxNCC.getEditor().setText(ncc.getMaNCC() + " - " + ncc.getTenNCC());
                }
            }
        });


//      Ẩn list view khi ComboBox mất focus
        cbxNCC.showingProperty().addListener((obs, wasShowing, isShowing) -> {
            if (isShowing) listViewNhaCungCap.setVisible(false);
        });

    }

    //  4.3. Thiết lập chức năng tìm kiếm chi tiết đơn vị tính
    public void timKiemDonViTinh() {

//      Lấy tất cả chi tiết đơn vị tính từ cơ sở dữ liệu và chỉnh style cho list view Chi Tiết Đơn Vị Tính
//        allChiTietDonViTinh = FXCollections.observableArrayList(new ChiTietDonViTinh_Dao().selectAll());
        listViewChiTietDonViTinh.setItems(allChiTietDonViTinh);
        listViewChiTietDonViTinh.setVisible(false);
        listViewChiTietDonViTinh.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; " + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 6, 0, 0, 2);" + "-fx-border-color: #cccccc;" + "-fx-border-width: 1;");

//      Hiển thị trong list view Chi Tiết Đơn Vị Tính
        listViewChiTietDonViTinh.setCellFactory(data -> new ListCell<>() {
            @Override
            protected void updateItem(ChiTietDonViTinh item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getThuoc().getMaThuoc() + " - " +
                            item.getThuoc().getTenThuoc() + " - " +
                            item.getDvt().getTenDonViTinh());
                }
            }
        });

//      Thêm listener cho txt tìm kiếm Chi Tiết Đơn Vị Tính
        txtTimKiemChiTietDonViTinh.textProperty().addListener((obs, oldVal, newVal) -> {

//          Nếu txt rỗng thì ẩn list view
            if (newVal == null || newVal.trim().isEmpty()) {
                listViewChiTietDonViTinh.setVisible(false);
                return;
            }

//          Lọc danh sách Chi Tiết Đơn Vị Tính dựa trên từ khóa nhập vào
            String keyword = newVal.toLowerCase();
            ObservableList<ChiTietDonViTinh> filtered = FXCollections.observableArrayList();

//          Lặp qua tất cả chi tiết đơn vị tính và thêm vào danh sách lọc nếu khớp với từ khóa
            for (ChiTietDonViTinh item : allChiTietDonViTinh) {
                if (item.getThuoc().getMaThuoc().toLowerCase().contains(keyword)
                        || item.getThuoc().getTenThuoc().toLowerCase().contains(keyword)) {
                    filtered.add(item);
                }
            }

//          Cập nhật danh sách hiển thị trong list view
            listViewChiTietDonViTinh.setItems(filtered);
            listViewChiTietDonViTinh.setVisible(!filtered.isEmpty());
        });
    }

    //  4.4. Chuyển giao diện xử lý thêm thuốc
    public void btnThemThuocClick(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();

            // (tuỳ chọn) set owner & modality như dialog
            if (mouseEvent.getSource() instanceof javafx.scene.Node node) {
                stage.initOwner(node.getScene().getWindow());
                stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            }

            // ✅ Không dùng FXML: tạo GUI + Controller thuần Java
            var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang.ThemThuoc_LapPhieuNhapHang_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.ThemThuoc_LapPhieuNhapHang_Ctrl();

            // truyền parent controller như trước
            ctrl.setParentCtrl(this);

            // hiển thị và gán toàn bộ control cho ctrl (giống showWithController ở form NCC)
            gui.showWithController(stage, ctrl);

            stage.setTitle("Thêm thuốc");
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  4.5. Thiết lập bảng nhập thuốc
    public void loadTable() {
        colSTT.setCellFactory(col -> new TableCell<CTPN_TSPTL_CHTDVT, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        colMaThuoc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChiTietDonViTinh().getThuoc().getMaThuoc()));
        colTenThuoc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChiTietDonViTinh().getThuoc().getTenThuoc()));
        colDonViNhap.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChiTietDonViTinh().getDvt().getTenDonViTinh()));
        colLoHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChiTietSP_theoLo().getMaLH()));

        colNSX.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, LocalDate>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setEditable(false);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//              Định dạng ngày tháng cho DatePicker
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                datePicker.setConverter(new StringConverter<LocalDate>() {
                    @Override
                    public String toString(LocalDate date) {
                        return date != null ? date.format(formatter) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, formatter)
                                : null;
                    }
                });
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

//              Kiểm tra nếu ô trống hoặc dòng trống
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();

//              Lấy giá trị sau khi chọn ngày
                Date NSX = rowItem.getChiTietSP_theoLo() != null ? rowItem.getChiTietSP_theoLo().getNsx() : null;
                datePicker.setValue(NSX != null ? NSX.toLocalDate() : null);


//              Xóa listener cũ
                datePicker.valueProperty().removeListener((obs, oldVal, newVal) -> {
                });

//              Thêm listener mới
                datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                    if (newDate != null) {
                        if (rowItem.getChiTietSP_theoLo() == null)
                            rowItem.setChiTietSP_theoLo(new Thuoc_SP_TheoLo());

                        rowItem.getChiTietSP_theoLo().setNsx(Date.valueOf(newDate));
                        getTableView().refresh();
                        if (newDate.isAfter(Date.valueOf(LocalDate.now()).toLocalDate())) {
                            rowItem.getChiTietSP_theoLo().setNsx(null);
                            getTableView().refresh();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thông báo");
                            alert.setHeaderText(null);
                            alert.setContentText("Ngày sản xuất không được sau ngày hiện tại!");
                            alert.getButtonTypes().setAll(ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                });

//              Hiển thị DatePicker trong ô
                setGraphic(datePicker);
            }

            @Override
            public void startEdit() {
                super.startEdit();
                Platform.runLater(() -> datePicker.requestFocus());
            }
        });

        colHanSuDung.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, LocalDate>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setEditable(false);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//              Định dạng ngày tháng cho DatePicker
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                datePicker.setConverter(new StringConverter<LocalDate>() {
                    @Override
                    public String toString(LocalDate date) {
                        return date != null ? date.format(formatter) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, formatter)
                                : null;
                    }
                });
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();

//              Lấy giá trị sau khi chọn ngày
                Date hsdDate = rowItem.getChiTietSP_theoLo() != null ? rowItem.getChiTietSP_theoLo().getHsd() : null;
                datePicker.setValue(hsdDate != null ? hsdDate.toLocalDate() : null);

//              Xóa listener cũ
                datePicker.valueProperty().removeListener((obs, oldVal, newVal) -> {
                });

//              Thêm listener mới
                datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                    if (newDate != null) {
                        if (rowItem.getChiTietSP_theoLo() == null)
                            rowItem.setChiTietSP_theoLo(new Thuoc_SP_TheoLo());

                        rowItem.getChiTietSP_theoLo().setHsd(Date.valueOf(newDate));
                        getTableView().refresh();
                        if (newDate.isBefore(Date.valueOf(LocalDate.now()).toLocalDate())) {
                            rowItem.getChiTietSP_theoLo().setHsd(null);
                            getTableView().refresh();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thông báo");
                            alert.setHeaderText(null);
                            alert.setContentText("Ngày sản xuất không được sau ngày hiện tại!");
                            alert.getButtonTypes().setAll(ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                });

                setGraphic(datePicker);
            }

            @Override
            public void startEdit() {
                super.startEdit();
                Platform.runLater(() -> datePicker.requestFocus());
            }
        });

        colXoa.setCellFactory(cellData -> new TableCell<CTPN_TSPTL_CHTDVT, String>() {
            private final Button btn = new Button("Xóa");

            {
                btn.setOnAction(event -> {
                    CTPN_TSPTL_CHTDVT item = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(item);
                });
                btn.setStyle("-fx-background-color:#de2c2c ; -fx-text-fill: white;");
                btn.getStyleClass().add("btn");

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        colSoLuong.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, Integer>() {
            private final TextField textField = new TextField();

            {
                textField.setAlignment(Pos.CENTER_RIGHT);
                textField.setPrefWidth(80);

                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                        if (rowItem != null) {
                            String txt = textField.getText();
                            try {
                                int sl = Integer.parseInt(txt);
                                if (sl < 0) {
                                    textField.setText("0");
                                } else {
                                    rowItem.getChiTietPhieuNhap().setSoLuong(sl);
                                    suKienThemMotDongMoiVaoBang();
                                    tblNhapThuoc.refresh();
                                }
                            } catch (NumberFormatException ex) {
                                Integer cur = rowItem.getChiTietPhieuNhap().getSoLuong();
                                textField.setText(cur != null ? cur.toString() : "0");
                            }
                        }
                    }
                });

                textField.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ENTER, TAB -> {
                            CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                            if (rowItem != null) {
                                String txt = textField.getText();
                                try {
                                    int sl = Integer.parseInt(txt);
                                    if (sl < 0) {
                                        textField.setText("0");
                                    } else {
                                        rowItem.getChiTietPhieuNhap().setSoLuong(sl);
                                        suKienThemMotDongMoiVaoBang();
                                        tblNhapThuoc.refresh();
                                    }
                                } catch (NumberFormatException ex) {
                                    Integer cur = rowItem.getChiTietPhieuNhap().getSoLuong();
                                    textField.setText(cur != null ? cur.toString() : "0");
                                }
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();
                    Integer soLuong = rowItem.getChiTietPhieuNhap().getSoLuong();
                    textField.setText(soLuong != null ? soLuong.toString() : "0");
                    setGraphic(textField);
                }
            }
        });

        colDonGiaNhap.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, Double>() {
            private final TextField textField = new TextField();
            private final VNDFormatter vndFormatter = new VNDFormatter();

            {
                textField.setAlignment(Pos.CENTER_RIGHT);
                textField.setPrefWidth(100);
                vndFormatter.applyNumberFormatter(textField);

                // Khi rời ô (người dùng nhập xong) -> parse & cập nhật model 1 lần
                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) { // focus lost
                        CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                        if (rowItem != null) {
                            String txt = textField.getText();
                            try {
                                double gia = vndFormatter.parseFormattedNumber(txt);
                                if (gia < 0) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Thông báo");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Giá nhập phải lớn hơn hoặc bằng 0");
                                    alert.getButtonTypes().setAll(ButtonType.OK);
                                    alert.showAndWait();
                                } else {
                                    rowItem.getChiTietPhieuNhap().setGiaNhap(gia);
                                    suKienThemMotDongMoiVaoBang();
                                    tblNhapThuoc.refresh();
                                }
                            } catch (NumberFormatException ex) {
                                // revert về giá model cũ
                                Double current = rowItem.getChiTietPhieuNhap().getGiaNhap();
                                textField.setText(current != null ? String.format("%.0f", current) : "0");
                            }
                        }
                    }
                });
                textField.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ENTER, TAB -> {
                            CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                            if (rowItem != null) {
                                String txt = textField.getText();
                                try {
                                    double gia = vndFormatter.parseFormattedNumber(txt);
                                    if (gia < 0) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Thông báo");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Giá nhập phải lớn hơn hoặc bằng 0");
                                        alert.getButtonTypes().setAll(ButtonType.OK);
                                        alert.showAndWait();
                                    } else {
                                        rowItem.getChiTietPhieuNhap().setGiaNhap(gia);
                                        suKienThemMotDongMoiVaoBang();
                                        tblNhapThuoc.refresh();
                                    }
                                } catch (NumberFormatException ex) {
                                    // revert về giá model cũ
                                    Double current = rowItem.getChiTietPhieuNhap().getGiaNhap();
                                    textField.setText(current != null ? String.format("%.0f", current) : "0");
                                }
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();
                    Double giaNhap = rowItem.getChiTietPhieuNhap().getGiaNhap();
                    textField.setText(giaNhap != null ? String.format("%.0f", giaNhap) : "0");
                    setGraphic(textField);
                }
            }
        });

        colChietKhau.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, Float>() {
            private final TextField textField = new TextField();

            {
                textField.setAlignment(Pos.CENTER_RIGHT);
                textField.setPrefWidth(80);

                // Khi người dùng rời ô nhập → parse giá trị và cập nhật model
                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) { // Focus lost
                        CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                        if (rowItem != null) {
                            String txt = textField.getText().trim();
                            try {
                                float ck = Float.parseFloat(txt);
                                if (ck < 0 || ck > 100) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Thông báo");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Chiết khấu phải nằm trong khoảng 0 - 100%");
                                    alert.showAndWait();
                                    // reset về giá trị cũ
                                    textField.setText(String.valueOf(rowItem.getChiTietPhieuNhap().getChietKhau()));
                                } else {
                                    rowItem.getChiTietPhieuNhap().setChietKhau(ck);
                                    suKienThemMotDongMoiVaoBang(); // Cập nhật tổng sau khi chỉnh xong
                                    tblNhapThuoc.refresh();
                                }
                            } catch (NumberFormatException ex) {
                                // nếu nhập không hợp lệ → revert
                                float cur = rowItem.getChiTietPhieuNhap().getChietKhau();
                                textField.setText(String.valueOf(cur));
                            }
                        }
                    }
                });
                textField.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ENTER, TAB -> {
                            CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                            if (rowItem != null) {
                                String txt = textField.getText().trim();
                                try {
                                    float ck = Float.parseFloat(txt);
                                    if (ck < 0 || ck > 100) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Thông báo");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Chiết khấu phải nằm trong khoảng 0 - 100%");
                                        alert.showAndWait();
                                        // reset về giá trị cũ
                                        textField.setText(String.valueOf(rowItem.getChiTietPhieuNhap().getChietKhau()));
                                    } else {
                                        rowItem.getChiTietPhieuNhap().setChietKhau(ck);
                                        suKienThemMotDongMoiVaoBang(); // Cập nhật tổng sau khi chỉnh xong
                                        tblNhapThuoc.refresh();
                                    }
                                } catch (NumberFormatException ex) {
                                    // nếu nhập không hợp lệ → revert
                                    float cur = rowItem.getChiTietPhieuNhap().getChietKhau();
                                    textField.setText(String.valueOf(cur));
                                }
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();
                    float ck = rowItem.getChiTietPhieuNhap().getChietKhau();
                    textField.setText(String.valueOf(ck));
                    setGraphic(textField);
                }
            }
        });

        colThue.setCellFactory(column -> new TableCell<CTPN_TSPTL_CHTDVT, Float>() {
            private final TextField textField = new TextField();

            {
                textField.setAlignment(Pos.CENTER_RIGHT);
                textField.setPrefWidth(80);

                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) { // Khi focus bị mất
                        CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                        if (rowItem != null) {
                            String txt = textField.getText().trim();
                            try {
                                float thue = Float.parseFloat(txt);
                                if (thue < 0 || thue > 100) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Thông báo");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Thuế phải nằm trong khoảng 0 - 100%");
                                    alert.showAndWait();
                                    textField.setText(String.valueOf(rowItem.getChiTietPhieuNhap().getThue()));
                                } else {
                                    rowItem.getChiTietPhieuNhap().setThue(thue);
                                    suKienThemMotDongMoiVaoBang(); // Gọi lại hàm tổng
                                    tblNhapThuoc.refresh();
                                }
                            } catch (NumberFormatException ex) {
                                float cur = rowItem.getChiTietPhieuNhap().getThue();
                                textField.setText(String.valueOf(cur));
                            }
                        }
                    }
                });
                textField.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ENTER, TAB -> {
                            CTPN_TSPTL_CHTDVT rowItem = getTableRow() != null ? getTableRow().getItem() : null;
                            if (rowItem != null) {
                                String txt = textField.getText().trim();
                                try {
                                    float thue = Float.parseFloat(txt);
                                    if (thue < 0 || thue > 100) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Thông báo");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Thuế phải nằm trong khoảng 0 - 100%");
                                        alert.showAndWait();
                                        textField.setText(String.valueOf(rowItem.getChiTietPhieuNhap().getThue()));
                                    } else {
                                        rowItem.getChiTietPhieuNhap().setThue(thue);
                                        suKienThemMotDongMoiVaoBang(); // Gọi lại hàm tổng
                                        tblNhapThuoc.refresh();
                                    }
                                } catch (NumberFormatException ex) {
                                    float cur = rowItem.getChiTietPhieuNhap().getThue();
                                    textField.setText(String.valueOf(cur));
                                }
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    CTPN_TSPTL_CHTDVT rowItem = getTableRow().getItem();
                    float thue = rowItem.getChiTietPhieuNhap().getThue();
                    textField.setText(String.valueOf(thue));
                    setGraphic(textField);
                }
            }
        });

        colThanhTien.setCellValueFactory(cellData -> {
            CTPN_TSPTL_CHTDVT item = cellData.getValue();
            if (item == null || item.getChiTietPhieuNhap() == null) {
                return new SimpleStringProperty("0");
            }

            ChiTietPhieuNhap ctpn = item.getChiTietPhieuNhap();

            double giaNhap = ctpn.getGiaNhap();
            int soLuong = ctpn.getSoLuong();
            float ck = ctpn.getChietKhau();
            float thue = ctpn.getThue();

            // Tính tiền hàng, chiết khấu, thuế và tổng
            double tienHang = giaNhap * soLuong;
            double tienCK = tienHang * (ck / 100);
            double tienThue = (tienHang - tienCK) * (thue / 100);
            double tong = tienHang - tienCK + tienThue;

            VNDFormatter vndFormatter = new VNDFormatter();
            return new SimpleStringProperty(vndFormatter.format(tong));
        });

//      Gán list vào bảng
        tblNhapThuoc.setItems(listNhapThuoc);
    }

    //  4.6. Thiết lập sự kiện thêm chi tiết đơn vị tính vào bảng
    public void suKienThemChiTietDonViTinhVaoBang() {
        listViewChiTietDonViTinh.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {

                ChiTietDonViTinh chiTietDonViTinh = (ChiTietDonViTinh) newVal;
                txtTimKiemChiTietDonViTinh.clear();
                listViewChiTietDonViTinh.setVisible(false);

//                  Tạo 3 đối tượng con
                ChiTietDonViTinh ctdvt = new ChiTietDonViTinh();
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                Thuoc_SP_TheoLo tsptl = new Thuoc_SP_TheoLo();

                maLoHienTai++;
                String maLH = String.format("LH%05d", maLoHienTai);

//                  Gán thông tin vào từng đối tượng
                ctdvt.setDvt(chiTietDonViTinh.getDvt());
                ctdvt.setThuoc(chiTietDonViTinh.getThuoc());

                tsptl.setThuoc(chiTietDonViTinh.getThuoc());
                tsptl.setMaLH(maLH);

                ctpn.setThuoc(chiTietDonViTinh.getThuoc());
                ctpn.setMaLH(maLH);

//                  Gộp vào 1 đối tượng model tổng hợp
                CTPN_TSPTL_CHTDVT newItem = new CTPN_TSPTL_CHTDVT();
                newItem.setChiTietDonViTinh(ctdvt);
                newItem.setChiTietPhieuNhap(ctpn);
                newItem.setChiTietSP_theoLo(tsptl);

//                  Thêm vào danh sách chính
                listNhapThuoc.add(newItem);
                tblNhapThuoc.setItems(listNhapThuoc);
                tblNhapThuoc.refresh();

                Platform.runLater(() -> {
                    listViewChiTietDonViTinh.getSelectionModel().clearSelection();
                    listViewChiTietDonViTinh.refresh();
                });
            }
        });

    }

    //  4.7. Thiết lập sự kiện tính tổng khi thêm một dòng mới vào bảng
    public void suKienThemMotDongMoiVaoBang() {
        double tongGiaNhap = 0.0;
        double tongTienChietKhau = 0.0;
        double tongTienThue = 0.0;
        double thanhTien = 0.0;

//      Tính tổng từ danh sách nhập thuốc
        for (CTPN_TSPTL_CHTDVT item : listNhapThuoc) {
            if (item == null || item.getChiTietPhieuNhap() == null) continue;

            ChiTietPhieuNhap ctpn = item.getChiTietPhieuNhap();

//          Lấy các giá trị cần thiết
            double giaNhap = ctpn.getGiaNhap();
            int soLuong = ctpn.getSoLuong();
            float ck = ctpn.getChietKhau();
            float thue = ctpn.getThue();

//          Tính tiền hàng, chiết khấu, thuế và tổng
            double tienHang = giaNhap * soLuong;
            double tienCK = tienHang * (ck / 100);
            double tienThue = (tienHang - tienCK) * (thue / 100);
            double tong = tienHang - tienCK + tienThue;

            tongGiaNhap += tienHang;
            tongTienChietKhau += tienCK;
            tongTienThue += tienThue;
            thanhTien += tong;
        }

        VNDFormatter vndFormatter = new VNDFormatter();

//      Cập nhật các trường tổng
        txtTongGiaNhap.setText(vndFormatter.format(tongGiaNhap));
        txtTongTienChietKhau.setText(vndFormatter.format(tongTienChietKhau));
        txtTongTienThue.setText(vndFormatter.format(tongTienThue));
        txtThanhTien.setText(vndFormatter.format(thanhTien));
    }

    //  4.8. Lưu phiếu nhập
    public void btnLuu(MouseEvent mouseEvent) {

//      Kiểm tra điều kiện trước khi lưu
//      Kiểm tra danh sách nhập thuốc không được rỗng
        if (listNhapThuoc.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng thêm thuốc vào phiếu nhập trước khi lưu!");
            alert.showAndWait();
            return;
        }

//      Kiểm tra nhà cung cấp đã được chọn
        if (cbxNCC.getValue() == null || cbxNCC.getValue().trim().isEmpty() || ncc == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn nhà cung cấp trước khi lưu!");
            alert.showAndWait();
            return;
        }

        for (CTPN_TSPTL_CHTDVT item : listNhapThuoc) {
//          Kiểm tra ngày sản xuất & hạn sử dụng
            if (item.getChiTietSP_theoLo().getNsx() == null || item.getChiTietSP_theoLo().getHsd() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập ngày sản xuất và hạn sử dụng cho: " + item.getChiTietDonViTinh().getThuoc().getMaThuoc());
                alert.showAndWait();
                return;
            }
//          Kiểm tra hạn sử dụng phải sau ngày sản xuất
            if (item.getChiTietPhieuNhap().getSoLuong() == 0 || item.getChiTietPhieuNhap().getSoLuong() <= 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Số lượng nhập của thuốc:" + item.getChiTietDonViTinh().getThuoc().getMaThuoc() + " phải lớn hơn 0!");
                alert.showAndWait();
                return;
            }
//          Kiểm tra giá nhập phải lớn hơn hoặc bằng 0
            if (item.getChiTietPhieuNhap().getGiaNhap() == 0 || item.getChiTietPhieuNhap().getGiaNhap() < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Giá nhập của thuốc:" + item.getChiTietDonViTinh().getThuoc().getMaThuoc() + " phải lớn hơn hoặc bằng 0!");
                alert.showAndWait();
                return;
            }
        }

//      Xác nhận lưu
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận lưu");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc chắn muốn lưu phiếu nhập này không?");

        Optional<ButtonType> resultConfirm = confirm.showAndWait();

//      Kiểm tra kết quả xác nhận
        if (resultConfirm.isEmpty() || resultConfirm.get() != ButtonType.OK) {
            return;
        }
        try {
//          Tạo đối tượng phiếu nhập
            PhieuNhap phieuNhap = new PhieuNhap();
            phieuNhap.setNhaCungCap(ncc);
            phieuNhap.setNgayNhap(txtNgayNhap.getEditor().getText().isEmpty() ? LocalDate.now() : txtNgayNhap.getValue());
            String maPN = txtMaPhieuNhap.getText().trim();

            if (!maPN.isEmpty()) {
                // viết 1 hàm trong DAO: boolean existsByMaPN(String maPN)
                if (phieuNhapDao.existsByMaPN(maPN)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Mã phiếu nhập đã tồn tại. Vui lòng nhập mã khác!");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }

//          Tự động sinh mã nếu người dùng không nhập
            if (maPN.isEmpty()) {
                maPN = phieuNhapDao.generateMaPN();
            }

//          Gán các thông tin còn lại
            phieuNhap.setMaPN(maPN);
            phieuNhap.setGhiChu(txtGhiChu.getText());
            phieuNhap.setTrangThai(true);

//          Lấy nhân viên hiện tại (giả sử là NV001)
            UserContext currentUser = SessionContext.getCurrentUser();
            if (currentUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Phiên đăng nhập không hợp lệ. Vui lòng đăng nhập lại.");
                alert.showAndWait();
                return;
            }

            NhanVien nv = UserContextMapper.toNhanVienReference(currentUser);
            phieuNhap.setNhanVien(nv);

//          Lưu từng chi tiết phiếu nhập
            boolean allSuccess = true;
            for (CTPN_TSPTL_CHTDVT itemTemp : listNhapThuoc) {
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                ctpn.setPhieuNhap(phieuNhap);
                ctpn.setThuoc(itemTemp.getChiTietPhieuNhap().getThuoc());
                ctpn.setMaLH(itemTemp.getChiTietSP_theoLo().getMaLH());
                ctpn.setSoLuong(itemTemp.getChiTietPhieuNhap().getSoLuong());
                ctpn.setGiaNhap(itemTemp.getChiTietPhieuNhap().getGiaNhap());
                ctpn.setChietKhau(itemTemp.getChiTietPhieuNhap().getChietKhau());
                ctpn.setThue(itemTemp.getChiTietPhieuNhap().getThue());

                Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                lo.setThuoc(itemTemp.getChiTietSP_theoLo().getThuoc());
                lo.setMaLH(itemTemp.getChiTietSP_theoLo().getMaLH());
                lo.setSoLuongTon(itemTemp.getChiTietPhieuNhap().getSoLuong());
                lo.setNsx(itemTemp.getChiTietSP_theoLo().getNsx());
                lo.setHsd(itemTemp.getChiTietSP_theoLo().getHsd());

                String maDVT = itemTemp.getChiTietDonViTinh().getDvt().getMaDVT();

//              Gọi DAO để lưu
                boolean result = phieuNhapDao.luuPhieuNhap(phieuNhap, ctpn, lo, maDVT);
                if (!result) {
                    allSuccess = false;
                }
            }

//          Thông báo kết quả lưu
            if (allSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Lưu phiếu nhập thành công!");
                alert.showAndWait();
                clearAll();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Một số chi tiết không được lưu thành công!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi khi lưu phiếu nhập!");
            alert.showAndWait();
        }
    }

    //  4.9. Hàm xóa tất cả dữ liệu và reset form
    private void clearAll() {
        txtMaPhieuNhap.clear();
        txtGhiChu.clear();
        cbxNCC.getEditor().clear();
        txtNgayNhap.setValue(LocalDate.now());
        listNhapThuoc.clear();
        tblNhapThuoc.refresh();
        txtNgayNhap.getEditor().clear();
        suKienThemMotDongMoiVaoBang();
    }

    //  4.10. Hủy phiếu nhập
    public void btnHuy(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận hủy");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn hủy phiếu nhập này không? Tất cả dữ liệu sẽ bị xóa!");

//      Hiển thị hộp thoại và chờ người dùng chọn
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            clearAll();

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Đã hủy");
            info.setHeaderText(null);
            info.setContentText("Phiếu nhập đã được hủy.");
            info.showAndWait();
        }
    }

    //  4.11. Thêm nhà cung cấp mới
    public void btnThemNCCClick(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();

            // ✅ Không dùng FXML, gọi UI thuần Java:
            ThemNhaCungCap_GUI gui = new ThemNhaCungCap_GUI();
            ThemNhaCungCap_Ctrl ctrl = new ThemNhaCungCap_Ctrl();

            gui.showWithController(stage, ctrl);

            // Set reference tới LapPhieuNhapHang_Ctrl nếu cần
            ctrl.setLapPhieuNhapHang_Ctrl(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenerListNhapThuoc(){
        listNhapThuoc.addListener((javafx.collections.ListChangeListener<CTPN_TSPTL_CHTDVT>) change -> {
            boolean shouldUpdate = false;
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    shouldUpdate = true;
                }
            }
            if (shouldUpdate) {
                // đảm bảo chạy trên UI thread
                suKienThemMotDongMoiVaoBang();
            }
        });
    }

    public void btnThemThuocByExcel(MouseEvent mouseEvent) {
        try{
            var gui  = new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang.NhapHangBangExcel_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.NhapHangBangExcel_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(tblNhapThuoc.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Nhập thuốc bằng Excel");
            ctrl.setLapPhieuNhapHangCtrl(this);
            gui.showWithController(dialog, ctrl);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isSameThuoc(Thuoc_SanPham o, Thuoc_SanPham i) {
        if (o == null || i == null) return false;

        return eq(i.getTenThuoc(), o.getTenThuoc())
                && eq(i.getDonViHamLuong(), o.getDonViHamLuong())
                && eq(i.getDuongDung(), o.getDuongDung())
                && eq(i.getQuyCachDongGoi(), o.getQuyCachDongGoi())
                && eq(i.getHangSX(), o.getHangSX())
                && eq(i.getNuocSX(), o.getNuocSX())
                && Math.abs(i.getHamLuong() - o.getHamLuong()) < 0.0001;
    }

    private boolean eq(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.trim().equalsIgnoreCase(b.trim());
    }



    public void themThuocTuExcel(List<CTPN_TSPTL_CHTDVT> excelList) {

        Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();
        List<Thuoc_SanPham> thuocDB = thuocDao.selectAll();

        for (CTPN_TSPTL_CHTDVT ct : excelList) {

            Thuoc_SanPham thuocExcel = ct.getChiTietPhieuNhap().getThuoc();
            Thuoc_SanPham thuocTonTai = null;

            // 🔍 SO SÁNH FULL FIELD
            for (Thuoc_SanPham tDB : thuocDB) {
                if (isSameThuoc(thuocExcel,tDB)) {
                    thuocTonTai = tDB;
                    break;
                }
            }

            // ❌ CHƯA CÓ
            if (thuocTonTai == null) {
                hienThongBaoThuocMoi(thuocExcel);
                return; // DỪNG TOÀN BỘ
            }

            // ✅ ĐÃ CÓ → GÁN LẠI THUỐC DB
            ct.getChiTietPhieuNhap().setThuoc(thuocTonTai);
            ct.getChiTietSP_theoLo().setThuoc(thuocTonTai);
            ct.getChiTietDonViTinh().setThuoc(thuocTonTai);

// 🔥 TẠO LÔ TỰ ĐỘNG (QUAN TRỌNG)
            maLoHienTai++;
            String maLH = String.format("LH%05d", maLoHienTai);

            ct.getChiTietSP_theoLo().setMaLH(maLH);
            ct.getChiTietPhieuNhap().setMaLH(maLH);

// ➕ FILL VÀO BẢNG
            themVaoBangNhap(ct);
        }
    }

    private void hienThongBaoThuocMoi(Thuoc_SanPham t) {

        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thuốc chưa tồn tại");
            alert.setHeaderText("Phát hiện thuốc chưa có trong hệ thống");

            alert.setContentText(
                    "Tên thuốc: " + t.getTenThuoc() +
                            "\nHàm lượng: " + t.getHamLuong() + " " + t.getDonViHamLuong() +
                            "\nĐường dùng: " + t.getDuongDung() +
                            "\nHãng SX: " + t.getHangSX() +
                            "\nXuất xứ: " + t.getNuocSX() +
                            "\n\nVui lòng nhập thuốc mới trước khi nhập hàng!"
            );

            alert.showAndWait();
        });
    }

    public void themVaoBangNhap(CTPN_TSPTL_CHTDVT ct) {
        listNhapThuoc.add(ct);
        tblNhapThuoc.setItems(listNhapThuoc);
    }

}
