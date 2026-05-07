package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietDonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.service.DonViTinhService;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ThietLapDonViTinh_SuaXoa_Ctrl {

    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN

    public CheckBox checkDVCB;
    public TextField tfHeSo;
    public TextField tfGiaBan;
    public TextField tfGiaNhap;
    public Button btnThemDVT;
    public ComboBox cbDVT;
    public Button btnThem, btnHuy;
    public Button btnXoa;
    private String maThuoc;
    private Consumer<ChiTietDonViTinh> onAdded;
    private Consumer<ChiTietDonViTinh> onDeleted;
    ChiTietDonViTinh ctdvt = new ChiTietDonViTinh();
    private final DonViTinhService donViTinhService = new DonViTinhService();

    // 2. KHỞI TẠO (INITIALIZE)

    public void initialize(){
        loadCbDVT();
        cbDVCBCheck();
        btnHuy.setOnAction(actionEvent -> btnHuyClick(null));
        btnThem.setOnAction(actionEvent -> btnThemClick(null));
        btnXoa.setOnAction(actionEvent -> btnXoaClick(null));
        btnThemDVT.setOnAction(actionEvent -> btnThemDVTClick(null));
        checkDVCB.setOnAction(e-> cbDVCBCheck());
        setupVNDFormat(tfGiaBan);
        setupVNDFormat(tfGiaNhap);

    }

    // Phương thức setup format VND
    private void setupVNDFormat(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }

            // Loại bỏ tất cả ký tự không phải số
            String digitsOnly = newValue.replaceAll("[^0-9]", "");

            if (digitsOnly.isEmpty()) {
                textField.setText("");
                return;
            }

            // Format với dấu chấm phân cách hàng nghìn
            String formatted = formatVND(digitsOnly);
            if(formatted.equals("00")) {
                formatted = "0";
            };

            // Chỉ cập nhật nếu giá trị thay đổi để tránh vòng lặp
            if (!newValue.equals(formatted)) {
                textField.setText(formatted);
                // Di chuyển con trỏ về cuối
                Platform.runLater(() ->
                        textField.positionCaret(textField.getText().length())
                );
            }
        });
    }

    // Format số theo kiểu VND (ngăn cách hàng nghìn bằng dấu chấm)
    private String formatVND(String digitsOnly) {
        if (digitsOnly == null || digitsOnly.isEmpty()) {
            return "";
        }

        // Loại bỏ các số 0 ở đầu (trừ trường hợp chỉ có 1 số 0)
        digitsOnly = digitsOnly.replaceFirst("^0+(?!$)", "");

        // Nếu sau khi loại bỏ mà rỗng hoặc chỉ còn "0"
        if (digitsOnly.isEmpty() || digitsOnly.equals("0")) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        int count = 0;

        // Duyệt từ phải sang trái
        for (int i = digitsOnly.length() - 1; i >= 0; i--) {
            if (count == 3) {
                result.insert(0, ",");
                count = 0;
            }
            result.insert(0, digitsOnly.charAt(i));
            count++;
        }

        return result.toString();
    }


    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN

    public void setCtdvt(ChiTietDonViTinh ctdvt) {
        this.ctdvt = ctdvt;
        fillFormFromModel();
    }

    public void loadCbDVT(){
        cbDVT.getItems().clear();
        List<DonViTinh> list = donViTinhService.findAll();
        for(DonViTinh donViTinh : list){
            cbDVT.getItems().add(donViTinh.getTenDonViTinh());
        }
    }

    public void btnThemDVTClick(MouseEvent mouseEvent) {
        try {
            ThemDVT_Ctrl ctrl = new ThemDVT_Ctrl();
            Stage stage = new Stage();
            stage.setTitle("Thêm đơn vị tính mới");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.ThemDVT_GUI()
                    .showWithController(stage, ctrl);

            stage.setOnHidden(e->loadCbDVT());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnHuyClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }

    public void btnThemClick(MouseEvent mouseEvent) {
        if (!kiemTraData()) return;

        DonViTinh dvt = donViTinhService.selectByTenDVT(cbDVT.getSelectionModel().getSelectedItem().toString());

        double heSo = parseNumberVN(tfHeSo.getText().trim());
        heSo= Math.round(heSo * 100000.0) / 100000.0;
        double giaNhap = parseVNDFormat(tfGiaNhap.getText().trim());
        double giaBan = parseVNDFormat(tfGiaBan.getText().trim());

        ctdvt.setDvt(dvt);
        ctdvt.setHeSoQuyDoi(heSo);
        ctdvt.setGiaNhap(giaNhap);
        ctdvt.setGiaBan(giaBan);
        ctdvt.setDonViCoBan(checkDVCB.isSelected());
        Thuoc_SanPham thuoc = new Thuoc_SanPham();
        thuoc.setMaThuoc(maThuoc);
        ctdvt.setThuoc(thuoc);
        System.out.println("Đơn vị tính thêm/sửa: " + ctdvt.getHeSoQuyDoi());

        //Thông báo cho SuaGiaThuoc để thêm vào bảng
        if (onAdded != null) onAdded.accept(ctdvt);

        Stage stage = (Stage) btnThem.getScene().getWindow();
        stage.close();

    }
    public void btnXoaClick(MouseEvent mouseEvent) {
        if (ctdvt == null) {
            Stage stage = (Stage) btnThem.getScene().getWindow();
            stage.close();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xoá đơn vị tính này?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                if (onDeleted != null) onDeleted.accept(ctdvt); // notify parent to remove from table
                Stage stage = (Stage) btnThem.getScene().getWindow();
                stage.close();
            }
        });
    }


    // 3 tham số cho luồng sửa/xoá
    public void setContext(String maThuoc, Consumer<ChiTietDonViTinh> onAdded, Consumer<ChiTietDonViTinh> onDeleted) {
        this.maThuoc = maThuoc;
        this.onAdded = onAdded;
        this.onDeleted = onDeleted;
    }

    private void fillFormFromModel() {
        if (ctdvt == null) return;
        if (cbDVT != null && ctdvt.getDvt() != null) {
            cbDVT.getSelectionModel().select(ctdvt.getDvt().getTenDonViTinh());
        }
        if (tfHeSo != null) tfHeSo.setText(String.valueOf(ctdvt.getHeSoQuyDoi()));
        if (tfGiaNhap != null) {
            double giaNhap = ctdvt.getGiaNhap();
            tfGiaNhap.setText(formatPriceForDisplay(giaNhap));
        }
        if (tfGiaBan != null) {
            double giaBan = ctdvt.getGiaBan();
            tfGiaBan.setText(formatPriceForDisplay(giaBan));
        }
        if (checkDVCB != null) checkDVCB.setSelected(ctdvt.isDonViCoBan());
        cbDVCBCheck();
    }

    // --- Helpers ---

    private String formatPriceForDisplay(double price) {
        // Chuyển sang số nguyên nếu không có phần thập phân
        if (price == (long) price) {
            return formatVND(String.valueOf((long) price));
        }
        return formatVND(String.valueOf(price).replace(".", ""));
    }

    private void clearErrorStyles() {
        resetStyle(cbDVT);
        resetStyle(tfGiaNhap);
        resetStyle(tfGiaBan);
        resetStyle(tfHeSo);
    }

    private void addErrorStyle(Control control) {
        // Giữ style hiện tại và thêm viền đỏ
        String base = control.getStyle() == null ? "" : control.getStyle();
        control.setStyle(base + "; -fx-border-color: #d32f2f; -fx-border-width: 1; -fx-border-radius: 4;");
    }

    private void resetStyle(Control control) {
        control.setStyle("");
    }



    public boolean kiemTraData() {
        clearErrorStyles();
        StringBuilder sb = new StringBuilder();

        if (cbDVT.getSelectionModel().getSelectedIndex() < 0) {
            sb.append("Vui lòng chọn đơn vị tính.\n");
            addErrorStyle(cbDVT);
        }

        // Giá nhập
        String giaNhapText = tfGiaNhap.getText() == null ? "" : tfGiaNhap.getText().trim();
        Double giaNhap = null;
        if (giaNhapText.isEmpty()) {
            sb.append("Giá nhập không được để trống.\n");
            addErrorStyle(tfGiaNhap);
        } else {
            giaNhap = parseVNDFormat(giaNhapText);
            if (giaNhap == null) {
                sb.append("Giá nhập phải là số hợp lệ.\n");
                addErrorStyle(tfGiaNhap);
            } else if (giaNhap < 0.0) {
                sb.append("Giá nhập phải >= 0.\n");
                addErrorStyle(tfGiaNhap);
            }
        }

        // Giá bán
        String giaBanText = tfGiaBan.getText() == null ? "" : tfGiaBan.getText().trim();
        Double giaBan = null;
        if (giaBanText.isEmpty()) {
            sb.append("Giá bán không được để trống.\n");
            addErrorStyle(tfGiaBan);
        } else {
            giaBan = parseVNDFormat(giaBanText);
            if (giaBan == null) {
                sb.append("Giá bán phải là số hợp lệ.\n");
                addErrorStyle(tfGiaBan);
            } else if (giaBan < 0.0) {
                sb.append("Giá bán phải >= 0.\n");
                addErrorStyle(tfGiaBan);
            }
        }
        System.out.println("Giá nhập: " + giaNhap + ", Giá bán: " + giaBan);

        // So sánh giá bán với giá nhập
        if (giaNhap != null && giaBan != null && giaBan < giaNhap) {
            sb.append("Giá bán phải lớn hơn hoặc bằng giá nhập.\n");
            addErrorStyle(tfGiaBan);
        }

        // Hệ số quy đổi
        String heSoText = tfHeSo.getText() == null ? "" : tfHeSo.getText().trim();
        Double heSo = null;
        if (heSoText.isEmpty()) {
            sb.append("Hệ số quy đổi không được để trống.\n");
            addErrorStyle(tfHeSo);
        } else {
            heSo = parseNumberVN(heSoText);
            if (heSo == null) {
                sb.append("Hệ số quy đổi phải là số hợp lệ.\n");
                addErrorStyle(tfHeSo);
            } else if (heSo <= 0.0) {
                sb.append("Hệ số quy đổi phải > 0.\n");
                addErrorStyle(tfHeSo);
            }
        }

        // Nếu là đơn vị cơ bản thì hệ số = 1
        if (checkDVCB.isSelected() && heSo != null && Math.abs(heSo - 1.0) > 1e-9) {
            sb.append("Đơn vị cơ bản phải có hệ số quy đổi = 1.\n");
            addErrorStyle(tfHeSo);
        }

        if (sb.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dữ liệu không hợp lệ");
            alert.setHeaderText("Vui lòng kiểm tra lại các trường sau:");
            alert.setContentText(sb.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }
    private Double parseVNDFormat(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        // Loại bỏ khoảng trắng
        s = s.replaceAll("\\s+", "");

        // Thay dấu phẩy thập phân thành dấu chấm (nếu có)
        s = s.replace(",", "");

        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    // Parse to Double
    private Double parseNumberVN(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        // remove internal spaces
        s = s.replaceAll("\\s+", "");

        // handle fraction forms "num/den" or "num:den"
        if (s.contains("/") || s.contains(":")) {
            String delim = s.contains("/") ? "/" : ":";
            String[] parts = s.split(java.util.regex.Pattern.quote(delim), 2);
            if (parts.length != 2) return null;
            String a = parts[0].replace(",", ".").trim();
            String b = parts[1].replace(",", ".").trim();
            try {
                double num = Double.parseDouble(a);
                double den = Double.parseDouble(b);
                if (den == 0.0) return null;
                return num / den;
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        // fallback: handle thousand separators and decimal comma
        if (s.contains(",") && s.contains(".")) {
            s = s.replace(".", "").replace(",", ".");
        } else {
            s = s.replace(",", ".");
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public void cbDVCBCheck(){
        if(checkDVCB.isSelected()){
            tfHeSo.setText("1");
            tfHeSo.setDisable(true);
        } else {
            tfHeSo.setDisable(false);
        }
    }
}
