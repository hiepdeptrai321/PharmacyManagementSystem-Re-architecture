package com.example.pharmacy.client.controller.CN_CapNhat.CapNhatGia;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.ChiTietDonViTinhDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;
import com.example.pharmacy.client.service.DonViTinhService;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ThietLapDonViTinh_Them_Ctrl {

    // 1. KHAI BÁO THÀNH PHẦN GIAO DIỆN

    public CheckBox checkDVCB;
    public TextField tfHeSo;
    public TextField tfGiaBan;
    public TextField tfGiaNhap;
    public Button btnThemDVT;
    public ComboBox cbDVT;
    public Button btnThem, btnHuy;
    private String maThuoc;
    private Consumer<ChiTietDonViTinhDto> onAdded;
    ChiTietDonViTinhDto ctdvt = new ChiTietDonViTinhDto();
    private final DonViTinhService donViTinhService = new DonViTinhService();

    // 2. KHỞI TẠO (INITIALIZE)

    public void initialize(){
        loadCbDVT();
        cbDVCBCheck();
        btnThemDVT.setOnAction(e-> btnThemDVTClick());
        btnHuy.setOnAction(e -> btnHuyClick(null));
        btnThem.setOnAction(e -> btnThemClick(null));
        checkDVCB.setOnAction(e-> cbDVCBCheck());
        setupVNDFormat(tfGiaBan);
        setupVNDFormat(tfGiaNhap);
    }

    // 3. XỬ LÝ SỰ KIỆN GIAO DIỆN

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


    public void loadCbDVT(){
        cbDVT.getItems().clear();
        List<DonViTinhDto> list = donViTinhService.findAll();
        for(DonViTinhDto donViTinh : list){
            cbDVT.getItems().add(donViTinh.getTenDonViTinh());
        }
    }

    public void btnThemDVTClick() {
        try {
            ThemDVT_Ctrl ctrl = new ThemDVT_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Thêm đơn vị tính mới");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacy.client.view.CN_CapNhat.CapNhatGia.ThemDVT_GUI()
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

        DonViTinhDto dvt = donViTinhService.selectByTenDVT(cbDVT.getSelectionModel().getSelectedItem().toString());

        Float heSo = parseNumberVN(tfHeSo.getText().trim());
        Double giaNhap = parseVNDFormat(tfGiaNhap.getText().trim());
        Double giaBan = parseVNDFormat(tfGiaBan.getText().trim());

        ctdvt.setDvt(dvt);
        ctdvt.setHeSoQuyDoi(heSo);
        ctdvt.setGiaNhap(giaNhap);
        ctdvt.setGiaBan(giaBan);
        ctdvt.setDonViCoBan(checkDVCB.isSelected());
        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(maThuoc);
        ctdvt.setThuoc(thuoc);

        //Thông báo cho SuaGiaThuoc để thêm vào bảng
        if (onAdded != null) onAdded.accept(ctdvt);

        Stage stage = (Stage) btnThem.getScene().getWindow();
        stage.close();

    }


    //2 tham số cho luồng thêm mới
    public void setContext(String maThuoc, Consumer<ChiTietDonViTinhDto> onAdded) {
        this.maThuoc = maThuoc;
        this.onAdded = onAdded;
    }

    // --- Helpers ---

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

    // Use Float for validation to keep everything in float domain
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
            } else if (giaNhap.floatValue() < 0f) {
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
            } else if (giaBan.floatValue() < 0f) {
                sb.append("Giá bán phải >= 0.\n");
                addErrorStyle(tfGiaBan);
            }
        }

        // So sánh giá bán với giá nhập
        if (giaNhap != null && giaBan != null && giaBan.floatValue() < giaNhap.floatValue()) {
            sb.append("Giá bán phải lớn hơn hoặc bằng giá nhập.\n");
            addErrorStyle(tfGiaBan);
        }

        // Hệ số quy đổi
        String heSoText = tfHeSo.getText() == null ? "" : tfHeSo.getText().trim();
        Float heSo = null;
        if (heSoText.isEmpty()) {
            sb.append("Hệ số quy đổi không được để trống.\n");
            addErrorStyle(tfHeSo);
        } else {
            heSo = parseNumberVN(heSoText);
            if (heSo == null) {
                sb.append("Hệ số quy đổi phải là số hợp lệ.\n");
                addErrorStyle(tfHeSo);
            } else if (heSo.floatValue() <= 0f) {
                sb.append("Hệ số quy đổi phải > 0.\n");
                addErrorStyle(tfHeSo);
            }
        }

        // Nếu là đơn vị cơ bản thì hệ số = 1
        if (checkDVCB.isSelected() && heSo != null && Math.abs(heSo.floatValue() - 1f) > 1e-6f) {
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

    // Parse to Float instead of Double
    private Float parseNumberVN(String raw) {
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
                return (float) (num / den);
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
            return Float.parseFloat(s);
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
