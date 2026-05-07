package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import com.example.pharmacymanagementsystem_qlht.service.NhanVienService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class SuaXoaNhanVien_Ctrl {
    public TextField txtMaNV;
    public TextField txtTenNV;
    public TextField txtSDT;
    public TextField txtEmail;
    public ComboBox<String> cbxGioiTinh;
    public TextField txtDiaChi;
    public TextField txtNgayBD;
    public TextField txtNgayKT;
    public ComboBox<String> cbxTrangThai;
    public DatePicker txtNgaySinh;
    public TableView<LuongNhanVien> tblLuongNV;
    public TableColumn<LuongNhanVien, String> colMaLuong;
    public TableColumn<LuongNhanVien, String> colTuNgay;
    public TableColumn<LuongNhanVien, String> colDenNgay;
    public TableColumn<LuongNhanVien, Double> colLuongCoBan;
    public TableColumn<LuongNhanVien, Double> colPhuCap;
    public TableColumn<LuongNhanVien, String> colGhiChu;

    private final NhanVienService nhanVienService = new NhanVienService();
    private List<LuongNhanVien> listLuong;
    private NhanVien nhanVien;
    public DanhMucNhanVien_Ctrl danhMucNhanVien_Ctrl;

    public void initialize() {
        cbxGioiTinh.getItems().setAll("Nam", "Nu");
        cbxTrangThai.getItems().setAll("Dang lam viec", "Da nghi viec");
        configureLuongTable();
    }

    public void load(NhanVien nhanVien) {
        loadDataNhanVien(nhanVien);
        this.nhanVien = nhanVien;
    }

    public void loadDataNhanVien(NhanVien nhanVien) {
        txtMaNV.setText(nhanVien.getMaNV());
        txtTenNV.setText(nhanVien.getTenNV());
        txtSDT.setText(nhanVien.getSdt());
        txtEmail.setText(nhanVien.getEmail());
        txtNgaySinh.setValue(nhanVien.getNgaySinh() == null ? null : nhanVien.getNgaySinh().toLocalDate());
        cbxGioiTinh.setValue(nhanVien.isGioiTinh() ? "Nu" : "Nam");
        txtDiaChi.setText(nhanVien.getDiaChi());
        cbxTrangThai.setValue(nhanVien.isTrangThai() ? "Dang lam viec" : "Da nghi viec");
        txtNgayBD.setText(nhanVien.getNgayVaoLam() == null ? "" : nhanVien.getNgayVaoLam().toLocalDate().toString());
        txtNgayKT.setText(nhanVien.getNgayNghiLam() == null ? "Dang lam viec" : nhanVien.getNgayNghiLam().toLocalDate().toString());

        listLuong = nhanVienService.findLuongByMaNhanVien(nhanVien.getMaNV());
        tblLuongNV.setItems(FXCollections.observableArrayList(listLuong));
        tblLuongNV.refresh();
    }

    public void btnSuaTaiKhoan(javafx.event.ActionEvent actionEvent) {
        try {
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.SuaTaiKhoan_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.SuaTaiKhoan_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(txtDiaChi.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Sua tai khoan nhan vien");

            gui.showWithController(dialog, ctrl, nhanVien);

            if (ctrl.isSaved) {
                NhanVien updated = ctrl.getUpdatedNhanVien();
                if (updated != null) {
                    nhanVien.setTaiKhoan(updated.getTaiKhoan());
                    nhanVien.setMatKhau(updated.getMatKhau());
                    nhanVien.setVaiTro(updated.getVaiTro());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void btnThayDoiLuong(javafx.event.ActionEvent actionEvent) {
        try {
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThietLapLuongNV_Ctrl();
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.ThietLapLuongNV_GUI();

            LuongNhanVien luongHienHanh = listLuong == null ? null : listLuong.stream()
                    .filter(lnv -> lnv.getDenNgay() == null)
                    .findFirst()
                    .map(lnv -> new LuongNhanVien(
                            lnv.getMaLNV(),
                            lnv.getTuNgay(),
                            lnv.getDenNgay(),
                            lnv.getLuongCoBan(),
                            lnv.getPhuCap(),
                            lnv.getGhiChu(),
                            lnv.getNhanVien()
                    ))
                    .orElse(null);

            if (luongHienHanh == null) {
                luongHienHanh = new LuongNhanVien(null, null, null, 0.0, 0.0, "", nhanVien);
            }

            Stage dialog = new Stage();
            dialog.initOwner(txtDiaChi.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Thay doi luong nhan vien");

            gui.showWithController(dialog, ctrl, nhanVien, luongHienHanh);

            if (!ctrl.isSaved || ctrl.luongNhanVien == null) {
                return;
            }

            if (listLuong != null) {
                for (LuongNhanVien lnv : listLuong) {
                    if (lnv.getDenNgay() == null) {
                        lnv.setDenNgay(Date.valueOf(LocalDate.now()));
                        nhanVienService.saveLuongNhanVien(lnv);
                        break;
                    }
                }
            }

            LuongNhanVien updated = ctrl.luongNhanVien;
            LuongNhanVien moi = new LuongNhanVien();
            moi.setMaLNV(nhanVienService.generateNewMaLuongNhanVien());
            moi.setTuNgay(Date.valueOf(LocalDate.now().plusDays(1)));
            moi.setDenNgay(null);
            moi.setLuongCoBan(updated.getLuongCoBan());
            moi.setPhuCap(updated.getPhuCap());
            moi.setGhiChu(updated.getGhiChu());
            moi.setNhanVien(nhanVien);

            if (!nhanVienService.saveLuongNhanVien(moi)) {
                showError("Khong the luu bang luong moi cho nhan vien.");
                return;
            }

            loadDataNhanVien(nhanVien);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void btnLuu(javafx.event.ActionEvent actionEvent) {
        if (!kiemTraHopLe()) {
            return;
        }

        NhanVien updated = new NhanVien();
        updated.setMaNV(txtMaNV.getText().trim());
        updated.setTenNV(txtTenNV.getText().trim());
        updated.setSdt(txtSDT.getText().trim());
        updated.setEmail(txtEmail.getText().trim());
        updated.setNgaySinh(Date.valueOf(txtNgaySinh.getValue()));
        updated.setGioiTinh("Nu".equals(cbxGioiTinh.getValue()));
        updated.setDiaChi(txtDiaChi.getText().trim());
        updated.setTaiKhoan(nhanVien.getTaiKhoan());
        updated.setMatKhau(nhanVien.getMatKhau());
        updated.setVaiTro(nhanVien.getVaiTro());
        updated.setTrangThaiXoa(nhanVien.isTrangThaiXoa());

        boolean isDangLam = "Dang lam viec".equals(cbxTrangThai.getValue());
        updated.setTrangThai(isDangLam);
        updated.setNgayVaoLam(nhanVien.getNgayVaoLam());
        updated.setNgayNghiLam(nhanVien.getNgayNghiLam());

        if (!nhanVien.isTrangThai() && isDangLam) {
            updated.setNgayVaoLam(Date.valueOf(LocalDate.now()));
            updated.setNgayNghiLam(null);
        } else if (nhanVien.isTrangThai() && !isDangLam) {
            updated.setNgayNghiLam(Date.valueOf(LocalDate.now()));
        } else if (isDangLam) {
            updated.setNgayNghiLam(null);
        }

        if (!nhanVienService.update(updated)) {
            showError("Khong the cap nhat nhan vien. Vui long kiem tra tai khoan trung hoac ket noi server.");
            return;
        }

        nhanVien = updated;
        if (danhMucNhanVien_Ctrl != null) {
            danhMucNhanVien_Ctrl.loadData();
        }
        dong();
    }

    public void btnXoa(javafx.event.ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xac nhan");
        confirm.setHeaderText(null);
        confirm.setContentText("Ban co chac chan muon xoa nhan vien nay khong?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        if (!nhanVienService.softDelete(nhanVien.getMaNV())) {
            showError("Xoa nhan vien that bai!");
            return;
        }

        if (danhMucNhanVien_Ctrl != null) {
            danhMucNhanVien_Ctrl.loadData();
        }
        dong();
    }

    public void btnHuy(javafx.event.ActionEvent actionEvent) {
        dong();
    }

    public void setParent(DanhMucNhanVien_Ctrl parent) {
        this.danhMucNhanVien_Ctrl = parent;
    }

    private void configureLuongTable() {
        colMaLuong.setCellValueFactory(new PropertyValueFactory<>("maLNV"));
        colTuNgay.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTuNgay() == null ? "" : cellData.getValue().getTuNgay().toString()));
        colDenNgay.setCellValueFactory(cellData -> {
            Date denNgay = cellData.getValue().getDenNgay();
            return new SimpleStringProperty(denNgay == null ? "Dang ap dung" : denNgay.toString());
        });

        NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        colLuongCoBan.setCellValueFactory(new PropertyValueFactory<>("luongCoBan"));
        colLuongCoBan.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : currencyFormat.format(item) + " VND");
            }
        });

        colPhuCap.setCellValueFactory(new PropertyValueFactory<>("phuCap"));
        colPhuCap.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : currencyFormat.format(item) + " VND");
            }
        });
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
    }

    private void dong() {
        Stage stage = (Stage) txtMaNV.getScene().getWindow();
        stage.close();
    }

    private boolean kiemTraHopLe() {
        if (txtTenNV.getText().trim().isEmpty()) {
            showError("Ten nhan vien khong duoc de trong!");
            return false;
        }
        if (txtSDT.getText().trim().isEmpty() || !txtSDT.getText().trim().matches("0\\d{9}")) {
            showError("So dien thoai khong hop le!");
            return false;
        }
        if (txtNgaySinh.getValue() == null) {
            showError("Ngay sinh khong duoc de trong!");
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty()
                && !txtEmail.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            showError("Email khong hop le!");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
