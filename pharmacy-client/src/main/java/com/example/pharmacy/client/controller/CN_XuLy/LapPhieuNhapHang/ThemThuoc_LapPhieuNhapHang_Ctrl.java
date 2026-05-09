package com.example.pharmacy.client.controller.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc.ThemThuoc_Ctrl;
import com.example.pharmacy.common.model.ChiTietDonViTinhDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;
import com.example.pharmacy.client.service.ThuocService;
import com.example.pharmacy.client.view.CN_DanhMuc.DMThuoc.ThemThuoc_GUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ThemThuoc_LapPhieuNhapHang_Ctrl {
    public TextField tfMaThuoc;
    public TextField tfTenThuoc;
    private TextField tfLoaiHang;
    public TableView<ChiTietDonViTinhDto> tbDVT;
    public TableColumn<ChiTietDonViTinhDto, String> colDVT;
    public TableColumn<ChiTietDonViTinhDto, String> colKH;
    public TableColumn<ChiTietDonViTinhDto, Object> colHeSo;
    public TableColumn<ChiTietDonViTinhDto, Object> colGiaNhap;
    public TableColumn<ChiTietDonViTinhDto, Object> colGiaBan;
    public TableColumn<ChiTietDonViTinhDto, Object> colDVCB;
    public TableColumn<ChiTietDonViTinhDto, Void> colXoa;
    public Button btnLuu;
    public Button btnHuy;
    public Button btnThietLapGia;

    private final ObservableList<ChiTietDonViTinhDto> listGia = FXCollections.observableArrayList();
    private final ThuocService thuocService = new ThuocService();
    private Thuoc_SanPhamDto thuoc;
    private LapPhieuNhapHang_Ctrl parentCtrl;

    public void initialize() {
        colDVT.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDvt().getTenDonViTinh()));
        colKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDvt().getKiHieu()));
        colHeSo.setCellValueFactory(new PropertyValueFactory<>("heSoQuyDoi"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colDVCB.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isDonViCoBan() ? "X" : ""));
        colXoa.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("X");

            {
                btn.setOnAction(event -> {
                    if (getIndex() >= 0 && getIndex() < listGia.size()) {
                        listGia.remove(getIndex());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tbDVT.setItems(listGia);
    }

    public void setParentCtrl(LapPhieuNhapHang_Ctrl parentCtrl) {
        this.parentCtrl = parentCtrl;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
        this.thuoc = thuoc;
        tfMaThuoc.setText(thuoc.getMaThuoc());
        tfTenThuoc.setText(thuoc.getTenThuoc());
        loadListGia(thuoc.getMaThuoc());
    }

    public void loadListGia(String maThuoc) {
        List<ChiTietDonViTinhDto> loaded = thuocService.findAll().stream()
                .filter(item -> maThuoc.equals(item.getMaThuoc()))
                .findFirst()
                .map(Thuoc_SanPhamDto::getDsCTDVT)
                .orElse(List.of());
        listGia.setAll(loaded);
    }

    public void btnThietLapGiaClick() {
        new Alert(Alert.AlertType.INFORMATION,
                "Thiet lap don vi tinh bo sung trong popup nay se duoc noi lai sau. Hien tai hay quan ly trong man Thuoc chinh."
        ).showAndWait();
    }

    public void btnLuuClick() {
        if (parentCtrl != null) {
            parentCtrl.timKiemDonViTinh();
        }
        closeWindow();
    }

    public void btnHuyClick() {
        closeWindow();
    }

    public void btnThemThuocClick(ActionEvent e) {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            if (e.getSource() instanceof javafx.scene.Node node) {
                stage.initOwner(node.getScene().getWindow());
            }
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Them thuoc");

            ThemThuoc_GUI gui = new ThemThuoc_GUI();
            ThemThuoc_Ctrl ctrl = new ThemThuoc_Ctrl();
            gui.showWithController(stage, ctrl);
            stage.showAndWait();

            Thuoc_SanPhamDto thuocThem = ctrl.getThuocThem();
            if (thuocThem != null) {
                setThuoc(thuocThem);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) tfMaThuoc.getScene().getWindow();
        stage.close();
    }
}
