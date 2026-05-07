package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.SuaKhuyenMai_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaKhuyenMai_GUI {

    public void showWithController(Stage stage, SuaKhuyenMai_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(954, 444);
        root.setStyle("-fx-font-size: 13;");

        // Title
        Label lbTitle = new Label("Cập nhật khuyến mãi");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(935, 28);
        lbTitle.setFont(Font.font(18));

        // Main VBox
        VBox mainVBox = new VBox();
        mainVBox.setLayoutX(14.857142857142858);
        mainVBox.setLayoutY(36);
        mainVBox.setPrefSize(932, 364);
        mainVBox.setSpacing(8);

        // GridPane for general info
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPrefSize(925, 135);

        ColumnConstraints c0 = new ColumnConstraints();
        c0.setPercentWidth(9);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(41);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(8);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(42);
        grid.getColumnConstraints().addAll(c0, c1, c2, c3);

        // Row placeholders (GridPane will size rows automatically)
        for (int i = 0; i < 5; i++) grid.getRowConstraints().add(new RowConstraints());

        // Fields
        Label lblTenKM = new Label("Tên KM:");
        TextField tfTenKM = new TextField();
        tfTenKM.setId("tfTenKM");
        tfTenKM.setPrefWidth(600);

        Label lblLoaiKM = new Label("Loại KM:");
        ComboBox<String> cbLoaiKM = new ComboBox<>();
        cbLoaiKM.setId("cbLoaiKM");
        cbLoaiKM.setPrefSize(380, 25);

        Label lblGiaTri = new Label("Giá trị KM:");
        TextField tfGiaTri = new TextField();
        tfGiaTri.setId("tfGiaTri");

        Label lblTu = new Label("Từ:");
        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setId("dpTuNgay");
        dpTuNgay.setPrefSize(387, 25);

        Label lblDen = new Label("Đến:");
        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setId("dpDenNgay");
        dpDenNgay.setPrefSize(407, 25);

        Label lblMoTa = new Label("Mô tả:");
        TextField tfMoTa = new TextField();
        tfMoTa.setId("tfMoTa");

        Label lblMaKM = new Label("Mã KM:");
        TextField tfMaKM = new TextField();
        tfMaKM.setId("tfMaKM");
        tfMaKM.setEditable(false);
        tfMaKM.setPrefWidth(365);

        // Place nodes in grid (columnIndex, rowIndex)
        grid.add(lblTenKM, 0, 0);
        GridPane.setColumnSpan(tfTenKM, 3);
        grid.add(tfTenKM, 1, 0);

        grid.add(lblLoaiKM, 0, 1);
        grid.add(cbLoaiKM, 1, 1);

        grid.add(lblGiaTri, 2, 1);
        grid.add(tfGiaTri, 3, 1);

        grid.add(lblTu, 0, 2);
        grid.add(dpTuNgay, 1, 2);

        grid.add(lblDen, 2, 2);
        grid.add(dpDenNgay, 3, 2);

        grid.add(lblMoTa, 0, 3);
        grid.add(tfMoTa, 1, 3);

        grid.add(lblMaKM, 2, 3);
        grid.add(tfMaKM, 3, 3);
        VBox.setMargin(grid, new javafx.geometry.Insets(8, 0, 0, 0));

        // TabPane for products
        TabPane tabPaneProducts = new TabPane();
        tabPaneProducts.setId("tabPaneProducts");
        tabPaneProducts.setPrefSize(969, 216);

        // Tab: Danh sách thuốc
        Tab tabThuoc = new Tab("Danh sách thuốc");
        tabThuoc.setClosable(false);
        AnchorPane paneThuocArea = new AnchorPane();
        // Search HBox inside tab
        HBox hbSearchThuoc = new HBox();
        hbSearchThuoc.setLayoutX(8);
        hbSearchThuoc.setLayoutY(8);
        hbSearchThuoc.setSpacing(8);
        Label lblTim = new Label("Tìm thuốc:");
        TextField tfTimThuoc = new TextField();
        tfTimThuoc.setId("tfTimThuoc");
        tfTimThuoc.setPrefWidth(860);
        tfTimThuoc.setPromptText("Nhập mã, tên sản phẩm");
        HBox.setHgrow(tfTimThuoc, Priority.ALWAYS);
        hbSearchThuoc.getChildren().addAll(lblTim, tfTimThuoc);

        TableView<ChiTietKhuyenMai> tbDSThuoc = new TableView<>();
        tbDSThuoc.setId("tbDSThuoc");
        tbDSThuoc.setLayoutX(8);
        tbDSThuoc.setLayoutY(43);
        tbDSThuoc.setPrefSize(922, 133);

        TableColumn<ChiTietKhuyenMai, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setPrefWidth(105);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietKhuyenMai, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        colTenThuoc.setPrefWidth(288);

        TableColumn<ChiTietKhuyenMai, Integer> colSLAP = new TableColumn<>("Số lượng áp dụng");
        colSLAP.setPrefWidth(152);
        colSLAP.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietKhuyenMai, Integer> colSLTD = new TableColumn<>("Số hóa đơn áp dụng");
        colSLTD.setPrefWidth(166);
        colSLTD.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietKhuyenMai, String> colDonVi = new TableColumn<>("Đơn vị (cơ bản)");
        colDonVi.setPrefWidth(146);
        colDonVi.setStyle("-fx-alignment: CENTER;");

        TableColumn<ChiTietKhuyenMai, Void> colXoaCT = new TableColumn<>("");
        colXoaCT.setPrefWidth(60);
        colXoaCT.setStyle("-fx-alignment: CENTER;");

        tbDSThuoc.getColumns().addAll(colMaThuoc, colTenThuoc, colSLAP, colSLTD, colDonVi, colXoaCT);

        ListView<Thuoc_SanPham> listViewThuoc = new ListView<>();
        listViewThuoc.setId("listViewThuoc");
        listViewThuoc.setLayoutX(72);
        listViewThuoc.setLayoutY(32);
        listViewThuoc.setPrefSize(859, 133);

        paneThuocArea.getChildren().addAll(hbSearchThuoc, tbDSThuoc, listViewThuoc);
        tabThuoc.setContent(paneThuocArea);

        // Tab: Sản phẩm tặng kèm
        Tab tabTangKem = new Tab("Sản phẩm tặng kèm");
        tabTangKem.setClosable(false);
        AnchorPane paneTangKem = new AnchorPane();

        HBox hbSearchQua = new HBox();
        hbSearchQua.setLayoutX(8);
        hbSearchQua.setLayoutY(8);
        hbSearchQua.setSpacing(8);
        Label lblTimQua = new Label("Tìm quà tặng:");
        TextField tfTimQua = new TextField();
        tfTimQua.setId("tfTimQua");
        tfTimQua.setPrefWidth(844);
        tfTimQua.setPromptText("Nhập mã, tên sản phẩm");
        HBox.setHgrow(tfTimQua, Priority.ALWAYS);
        hbSearchQua.getChildren().addAll(lblTimQua, tfTimQua);

        TableView<Thuoc_SP_TangKem> tbTangKem = new TableView<>();
        tbTangKem.setId("tbTangKem");
        tbTangKem.setLayoutX(8);
        tbTangKem.setLayoutY(42);
        tbTangKem.setPrefSize(922, 133);

        TableColumn<Thuoc_SP_TangKem, String> colMaQua = new TableColumn<>("Mã thuốc");
        colMaQua.setPrefWidth(120);
        colMaQua.setStyle("-fx-alignment: CENTER;");

        TableColumn<Thuoc_SP_TangKem, String> colTenQua = new TableColumn<>("Tên thuốc");
        colTenQua.setPrefWidth(400);

        TableColumn<Thuoc_SP_TangKem, Integer> colSLTang = new TableColumn<>("Số lượng tặng");
        colSLTang.setPrefWidth(149);
        colSLTang.setStyle("-fx-alignment: CENTER;");

        TableColumn<Thuoc_SP_TangKem, String> colDonViQua = new TableColumn<>("Đơn vị (cơ bản)");
        colDonViQua.setPrefWidth(181);
        colDonViQua.setStyle("-fx-alignment: CENTER;");

        TableColumn<Thuoc_SP_TangKem, Void> colXoaQua = new TableColumn<>("");
        colXoaQua.setPrefWidth(71);
        colXoaQua.setStyle("-fx-alignment: CENTER;");

        tbTangKem.getColumns().addAll(colMaQua, colTenQua, colSLTang, colDonViQua, colXoaQua);

        ListView<Thuoc_SanPham> listViewQua = new ListView<>();
        listViewQua.setId("listViewQua");
        listViewQua.setLayoutX(88);
        listViewQua.setLayoutY(33);
        listViewQua.setPrefSize(844, 140);

        paneTangKem.getChildren().addAll(hbSearchQua, tbTangKem, listViewQua);
        tabTangKem.setContent(paneTangKem);

        // Tab: Giảm theo giá trị hóa đơn
        Tab tabHoaDon = new Tab("Giảm theo giá trị hóa đơn");
        tabHoaDon.setClosable(false);
        AnchorPane paneHoaDon = new AnchorPane();

        Label lblGiaTriHoaDon = new Label("Giá trị hóa đơn áp dụng (VNĐ):");
        lblGiaTriHoaDon.setLayoutX(7);
        lblGiaTriHoaDon.setLayoutY(60);

        TextField tfGiaTriHoaDon = new TextField();
        tfGiaTriHoaDon.setId("tfGiaTriHoaDon");
        tfGiaTriHoaDon.setLayoutX(200);
        tfGiaTriHoaDon.setLayoutY(56);
        tfGiaTriHoaDon.setPrefSize(722, 42);

        paneHoaDon.getChildren().addAll(lblGiaTriHoaDon, tfGiaTriHoaDon);
        tabHoaDon.setContent(paneHoaDon);

        tabPaneProducts.getTabs().addAll(tabThuoc, tabTangKem, tabHoaDon);

        mainVBox.getChildren().addAll(grid, tabPaneProducts);

        // Buttons
        Button btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setLayoutX(859);
        btnLuu.setLayoutY(401);
        btnLuu.setPrefSize(80, 34);


        Button btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(768);
        btnHuy.setLayoutY(401);
        btnHuy.setPrefSize(80, 34);
        btnLuu.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnHuy.setStyle(" -fx-background-color: #f0ad4e; -fx-text-fill: white;");

        // Assemble root
        root.getChildren().addAll(lbTitle, mainVBox, btnLuu, btnHuy);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());

        // Inject controls into controller
        ctrl.tfTenKM = tfTenKM;
        ctrl.cbLoaiKM = cbLoaiKM;
        ctrl.tfGiaTri = tfGiaTri;
        ctrl.dpTuNgay = dpTuNgay;
        ctrl.dpDenNgay = dpDenNgay;
        ctrl.tfMoTa = tfMoTa;
        ctrl.tfMaKM = tfMaKM;

        ctrl.tabPaneProducts = tabPaneProducts;
        ctrl.tabThuoc = tabThuoc;
        ctrl.tabTangKem = tabTangKem;
        ctrl.tabHoaDon = tabHoaDon;

        ctrl.tfTimThuoc = tfTimThuoc;
        ctrl.tbDSThuoc = tbDSThuoc;
        ctrl.colMaThuoc = colMaThuoc;
        ctrl.colTenThuoc = colTenThuoc;
        ctrl.colSLAP = colSLAP;
        ctrl.colSLTD = colSLTD;
        ctrl.colDonVi = colDonVi;
        ctrl.colXoaCT = colXoaCT;
        ctrl.listViewThuoc = listViewThuoc;

        ctrl.tfTimQua = tfTimQua;
        ctrl.tbTangKem = tbTangKem;
        ctrl.colMaQua = colMaQua;
        ctrl.colTenQua = colTenQua;
        ctrl.colSLTang = colSLTang;
        ctrl.colDonViQua = colDonViQua;
        ctrl.colXoaQua = colXoaQua;
        ctrl.listViewQua = listViewQua;

        ctrl.tfGiaTriHoaDon = tfGiaTriHoaDon;

        ctrl.btnLuu = btnLuu;
        ctrl.btnHuy = btnHuy;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Cập nhật khuyến mãi");
        stage.setScene(scene);
        stage.show();
    }
}
