package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.ThemKhuyenMai_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemKhuyenMai_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThemKhuyenMai_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(948, 453);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Thêm khuyến mãi");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(925, 25);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 18));

        // VBox container
        VBox vbox = new VBox();
        vbox.setLayoutX(14.857142857142858);
        vbox.setLayoutY(36);
        vbox.setPrefSize(932, 364);
        vbox.setSpacing(8);
        VBox.setMargin(vbox, new Insets(0));
        AnchorPane.setTopAnchor(vbox, 36.0);
        AnchorPane.setLeftAnchor(vbox, 15.0);
        AnchorPane.setRightAnchor(vbox, 4.0);
        AnchorPane.setBottomAnchor(vbox, 44.0);

        // GridPane with column constraints
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPrefWidth(925);
        ColumnConstraints c0 = new ColumnConstraints();
        c0.setPercentWidth(9);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(41);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(8);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(42);
        grid.getColumnConstraints().addAll(c0, c1, c2, c3);

        // Row placeholders (not strictly required)
        for (int i = 0; i < 5; i++) grid.getRowConstraints().add(new RowConstraints());

        // Form fields
        Label lblTen = new Label("Tên KM:");
        TextField tfTenKM = new TextField();
        tfTenKM.setPrefHeight(25);
        tfTenKM.setPromptText("Nhập tên khuyến mãi");
        GridPane.setColumnIndex(lblTen, 0);
        GridPane.setRowIndex(lblTen, 0);
        GridPane.setColumnIndex(tfTenKM, 1);
        GridPane.setRowIndex(tfTenKM, 0);
        GridPane.setColumnSpan(tfTenKM, 3);

        Label lblLoai = new Label("Loại KM:");
        ComboBox<LoaiKhuyenMai> cbLoaiKM = new ComboBox<>();
        cbLoaiKM.setPrefHeight(25);
        cbLoaiKM.setPromptText("Chọn loại khuyến mãi");
        GridPane.setColumnIndex(lblLoai, 0);
        GridPane.setRowIndex(lblLoai, 1);
        GridPane.setColumnIndex(cbLoaiKM, 1);
        GridPane.setRowIndex(cbLoaiKM, 1);

        Label lblGiaTri = new Label("Giá trị KM:");
        TextField tfGiaTri = new TextField();
        tfGiaTri.setPromptText("Nhập giá trị khuyến mãi");
        GridPane.setColumnIndex(lblGiaTri, 2);
        GridPane.setRowIndex(lblGiaTri, 1);
        GridPane.setColumnIndex(tfGiaTri, 3);
        GridPane.setRowIndex(tfGiaTri, 1);

        Label lblTu = new Label("Từ:");
        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setPrefHeight(25);
        GridPane.setColumnIndex(lblTu, 0);
        GridPane.setRowIndex(lblTu, 2);
        GridPane.setColumnIndex(dpTuNgay, 1);
        GridPane.setRowIndex(dpTuNgay, 2);

        Label lblDen = new Label("Đến:");
        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setPrefHeight(25);
        GridPane.setColumnIndex(lblDen, 2);
        GridPane.setRowIndex(lblDen, 2);
        GridPane.setColumnIndex(dpDenNgay, 3);
        GridPane.setRowIndex(dpDenNgay, 2);

        Label lblMoTa = new Label("Mô tả:");
        TextField tfMoTa = new TextField();
        tfMoTa.setPrefHeight(25);
        GridPane.setColumnIndex(lblMoTa, 0);
        GridPane.setRowIndex(lblMoTa, 3);
        GridPane.setColumnIndex(tfMoTa, 1);
        GridPane.setRowIndex(tfMoTa, 3);
        GridPane.setColumnSpan(tfMoTa, 3);

        dpTuNgay.setPrefWidth(1000);
        dpDenNgay.setPrefWidth(1000);
        cbLoaiKM.setPrefWidth(1000);

        grid.getChildren().addAll(lblTen, tfTenKM, lblLoai, cbLoaiKM, lblGiaTri, tfGiaTri, lblTu, dpTuNgay, lblDen, dpDenNgay, lblMoTa, tfMoTa);
        VBox.setMargin(grid, new Insets(8, 0, 0, 0));

        // TabPane and tabs
        TabPane tabPaneProducts = new TabPane();
        tabPaneProducts.setPrefSize(933, 216);

        // Tab: Danh sách thuốc
        Tab tabDanhSach = new Tab("Danh sách thuốc");
        tabDanhSach.setClosable(false);
        AnchorPane paneThuocArea = new AnchorPane();
        // Search HBox
        HBox hbSearch = new HBox(8);
        hbSearch.setLayoutX(8);
        hbSearch.setLayoutY(8);
        Label lblFind = new Label("Tìm thuốc:");
        TextField tfTimThuoc = new TextField();
        tfTimThuoc.setPrefWidth(854);
        tfTimThuoc.setPromptText("Nhập mã, tên thuốc");
        HBox.setHgrow(tfTimThuoc, Priority.ALWAYS);
        hbSearch.getChildren().addAll(lblFind, tfTimThuoc);

        TableView<Object> tbDSThuoc = new TableView<>();
        tbDSThuoc.setLayoutX(8);
        tbDSThuoc.setLayoutY(43);
        tbDSThuoc.setPrefSize(915, 133);

        TableColumn<Object, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setPrefWidth(115);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        colTenThuoc.setPrefWidth(290);
        TableColumn<Object, String> colSLAP = new TableColumn<>("Số lượng áp dụng");
        colSLAP.setPrefWidth(161);
        colSLAP.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colSLTD = new TableColumn<>("Số hóa đơn áp dụng");
        colSLTD.setPrefWidth(162);
        colSLTD.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colDonVi = new TableColumn<>("Đơn vị (cơ bản)");
        colDonVi.setPrefWidth(115);
        colDonVi.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colXoaCT = new TableColumn<>("");
        colXoaCT.setPrefWidth(71);
        colXoaCT.setStyle("-fx-alignment: CENTER;");

        tbDSThuoc.getColumns().addAll(colMaThuoc, colTenThuoc, colSLAP, colSLTD, colDonVi, colXoaCT);

        ListView<Thuoc_SanPham> listViewThuoc = new ListView<>();
        listViewThuoc.setLayoutX(72);
        listViewThuoc.setLayoutY(32);
        listViewThuoc.setPrefSize(853, 135);

        paneThuocArea.getChildren().addAll(hbSearch, tbDSThuoc, listViewThuoc);
        tabDanhSach.setContent(paneThuocArea);

        // Tab: Sản phẩm tặng kèm
        Tab tabTangKem = new Tab("Sản phẩm tặng kèm");
        tabTangKem.setClosable(false);
        AnchorPane paneTangKem = new AnchorPane();

        HBox hbGiftSearch = new HBox(8);
        hbGiftSearch.setLayoutX(8);
        hbGiftSearch.setLayoutY(8);
        Label lblTimQua = new Label("Tìm quà tặng:");
        TextField tfTimQua = new TextField();
        tfTimQua.setPrefHeight(25);
        tfTimQua.setPrefWidth(844);
        tfTimQua.setPromptText("Nhập mã, tên thuốc");
        HBox.setHgrow(tfTimQua, Priority.ALWAYS);
        hbGiftSearch.getChildren().addAll(lblTimQua, tfTimQua);

        TableView<Object> tbTangKem = new TableView<>();
        tbTangKem.setLayoutX(8);
        tbTangKem.setLayoutY(42);
        tbTangKem.setPrefSize(922, 133);

        TableColumn<Object, String> colMaQua = new TableColumn<>("Mã thuốc");
        colMaQua.setPrefWidth(120);
        colMaQua.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colTenQua = new TableColumn<>("Tên thuốc");
        colTenQua.setPrefWidth(400);
        TableColumn<Object, String> colSLTang = new TableColumn<>("Số lượng tặng");
        colSLTang.setPrefWidth(150);
        colSLTang.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colDonViQua = new TableColumn<>("Đơn vị (cơ bản)");
        colDonViQua.setPrefWidth(183);
        colDonViQua.setStyle("-fx-alignment: CENTER;");
        TableColumn<Object, String> colXoaQua = new TableColumn<>("");
        colXoaQua.setPrefWidth(68);
        colXoaQua.setStyle("-fx-alignment: CENTER;");

        tbTangKem.getColumns().addAll(colMaQua, colTenQua, colSLTang, colDonViQua, colXoaQua);

        ListView<Thuoc_SanPham> listViewQua = new ListView<>();
        listViewQua.setLayoutX(90);
        listViewQua.setLayoutY(33);
        listViewQua.setPrefSize(838, 135);

        paneTangKem.getChildren().addAll(hbGiftSearch, tbTangKem, listViewQua);
        tabTangKem.setContent(paneTangKem);

        // Tab: Giảm theo giá trị hóa đơn
        Tab tabHoaDon = new Tab("Giảm theo giá trị hóa đơn");
        tabHoaDon.setClosable(false);
        AnchorPane paneHoaDon = new AnchorPane();
        GridPane gpHoaDon = new GridPane();
        gpHoaDon.setLayoutX(7);
        gpHoaDon.setLayoutY(60);
        gpHoaDon.setHgap(10);
        gpHoaDon.setVgap(8);
        gpHoaDon.setPrefWidth(922);

        ColumnConstraints ch0 = new ColumnConstraints();
        ch0.setPercentWidth(20);
        ColumnConstraints ch1 = new ColumnConstraints();
        ch1.setPercentWidth(80);
        ColumnConstraints ch2 = new ColumnConstraints();
        ch2.setPercentWidth(0);
        gpHoaDon.getColumnConstraints().addAll(ch0, ch1, ch2);

        Label lblGiaTriHoaDon = new Label("Giá trị hóa đơn áp dụng (VNĐ):");
        TextField tfGiaTriHoaDon = new TextField();
        tfGiaTriHoaDon.setPrefHeight(42);
        tfGiaTriHoaDon.setPrefWidth(722);

        GridPane.setColumnIndex(lblGiaTriHoaDon, 0);
        GridPane.setRowIndex(lblGiaTriHoaDon, 0);
        GridPane.setColumnIndex(tfGiaTriHoaDon, 1);
        GridPane.setRowIndex(tfGiaTriHoaDon, 0);

        gpHoaDon.getChildren().addAll(lblGiaTriHoaDon, tfGiaTriHoaDon);
        paneHoaDon.getChildren().add(gpHoaDon);
        tabHoaDon.setContent(paneHoaDon);

        tabPaneProducts.getTabs().addAll(tabDanhSach, tabTangKem, tabHoaDon);

        vbox.getChildren().addAll(grid, tabPaneProducts);

        // Buttons
        Button btnThem = new Button("Thêm");
        btnThem.setLayoutX(859);
        btnThem.setLayoutY(410);
        btnThem.setPrefWidth(80);
        btnThem.setStyle("-fx-text-fill: white; -fx-background-color: green;");
        btnThem.getStyleClass().add("btnthemhuy");

        Button btnHuy = new Button("Hủy");
        btnHuy.setLayoutX(768);
        btnHuy.setLayoutY(410);
        btnHuy.setPrefWidth(80);
        btnHuy.getStyleClass().add("btnthemhuy");

        // Assemble root
        root.getChildren().addAll(lbTitle, vbox, btnThem, btnHuy);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());

        // Inject into controller (unchecked casts where necessary)
        try {
            ctrl.tfTenKM = tfTenKM;
            ctrl.cbLoaiKM = cbLoaiKM;
            ctrl.tfGiaTri = tfGiaTri;
            ctrl.dpTuNgay = dpTuNgay;
            ctrl.dpDenNgay = dpDenNgay;
            ctrl.tfMoTa = tfMoTa;
            ctrl.tabPaneProducts = tabPaneProducts;

            ctrl.tfTimThuoc = tfTimThuoc;
            ctrl.tbDSThuoc = (TableView) tbDSThuoc;
            ctrl.colMaThuoc = (TableColumn) colMaThuoc;
            ctrl.colTenThuoc = (TableColumn) colTenThuoc;
            ctrl.colSLAP = (TableColumn) colSLAP;
            ctrl.colSLTD = (TableColumn) colSLTD;
            ctrl.colDonVi = (TableColumn) colDonVi;
            ctrl.colXoaCT = (TableColumn) colXoaCT;
            ctrl.listViewThuoc = listViewThuoc;

            ctrl.tabTangKem = tabTangKem;
            ctrl.tfTimQua = tfTimQua;
            ctrl.tbTangKem = (TableView) tbTangKem;
            ctrl.colMaQua = (TableColumn) colMaQua;
            ctrl.colTenQua = (TableColumn) colTenQua;
            ctrl.colSLTang = (TableColumn) colSLTang;
            ctrl.colDonViQua = (TableColumn) colDonViQua;
            ctrl.colXoaQua = (TableColumn) colXoaQua;
            ctrl.listViewQua = listViewQua;

            ctrl.tabHoaDon = tabHoaDon;
            ctrl.tfGiaTriHoaDon = tfGiaTriHoaDon;

            ctrl.btnThem = btnThem;
            ctrl.btnHuy = btnHuy;
        } catch (Exception ignored) {
            // best-effort injection, controller field types may vary; keep silent as pattern in other GUI builders
        }

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Thêm khuyến mãi");
        stage.setScene(scene);
        stage.show();
    }
}
