package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat;

import com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;
import com.example.pharmacymanagementsystem_qlht.service.CaiDatService;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import com.example.pharmacymanagementsystem_qlht.view.DangNhap_GUI;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class caiDat_Ctrl {
    private final caiDat_GUI view;
    private final CaiDatService caiDatService;
    private final List<CaiDat> caiDatList;

    public caiDat_Ctrl(caiDat_GUI view) {
        this.view = view;
        this.caiDatService = new CaiDatService();
        this.caiDatList = new ArrayList<>(caiDatService.findAll());
        loadThongSo();
        initEvent();
    }

    public void loadThongSo() {
        for (CaiDat caiDat : caiDatList) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue" -> {
                    float giaTriThue = Float.parseFloat(caiDat.getGiaTri()) * 100;
                    String[] split = Float.toString(giaTriThue).split("\\.");
                    view.txtThue.setText(split[0]);
                }
                case "NgayHetHan" -> {
                    int soNgay = Integer.parseInt(caiDat.getGiaTri());
                    view.txtNgay.setText(Integer.toString(soNgay));
                    view.cbxdonViNgay.getSelectionModel().select(0);
                }
                default -> {
                }
            }
        }
    }

    private void initEvent() {
        view.btnLuu.setOnAction(event -> luu());
        view.btnHuy.setOnAction(event -> huy());
    }

    private void luu() {
        if (!validateInput()) {
            return;
        }
        if (!xacNhanLuu()) {
            return;
        }
        int cachLuu = cachLuu();
        if (cachLuu == 0) {
            dong();
            return;
        }

        boolean updated = true;
        for (CaiDat caiDat : caiDatList) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue" -> {
                    if (cachLuu == 1 || cachLuu == 3) {
                        float thueTemp = Float.parseFloat(view.txtThue.getText());
                        caiDat.setGiaTri(String.valueOf(thueTemp / 100));
                        updated &= caiDatService.update(caiDat);
                    }
                }
                case "NgayHetHan" -> {
                    if (cachLuu == 2 || cachLuu == 3) {
                        int soNgay = Integer.parseInt(view.txtNgay.getText());
                        String donVi = view.cbxdonViNgay.getValue();
                        if ("Thang".equals(donVi)) {
                            soNgay *= 30;
                        } else if ("Nam".equals(donVi)) {
                            soNgay *= 365;
                        }
                        caiDat.setGiaTri(String.valueOf(soNgay));
                        updated &= caiDatService.update(caiDat);
                    }
                }
                default -> {
                }
            }
        }

        if (!updated) {
            showError("Khong the luu cai dat. Vui long kiem tra ket noi server.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hoan tat");
        alert.setHeaderText(null);
        alert.setContentText("Luu cai dat thanh cong.\nBan co muon khoi dong lai ung dung de ap dung thay doi khong?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            restartApp();
            return;
        }
        dong();
    }

    private void restartApp() {
        Stage currentStage = (Stage) view.btnLuu.getScene().getWindow();
        Stage ownerStage = currentStage.getOwner() instanceof Stage stage ? stage : null;
        currentStage.close();
        if (ownerStage != null) {
            ownerStage.close();
        }
        SessionContext.clear();

        Platform.runLater(() -> {
            try {
                Stage loginStage = new Stage();
                loginStage.setTitle("Dang nhap he thong quan ly nha thuoc");
                loginStage.getIcons().add(new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")
                )));
                new DangNhap_GUI().showWithController(loginStage, new DangNhap_Ctrl());
                loginStage.show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private int cachLuu() {
        int cachLuu = 0;
        for (CaiDat caiDat : caiDatList) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue" -> {
                    float thueTemp = Float.parseFloat(view.txtThue.getText());
                    String thueDaChuyen = String.valueOf(thueTemp / 100);
                    if (!thueDaChuyen.equals(caiDat.getGiaTri())) {
                        cachLuu += 1;
                    }
                }
                case "NgayHetHan" -> {
                    if (!view.txtNgay.getText().equals(caiDat.getGiaTri())
                            || view.cbxdonViNgay.getSelectionModel().getSelectedIndex() != 0) {
                        cachLuu += 2;
                    }
                }
                default -> {
                }
            }
        }
        return cachLuu;
    }

    private boolean xacNhanLuu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xac nhan luu");
        alert.setHeaderText(null);
        alert.setContentText("Ban co chac chan muon luu cac thay doi khong?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean validateInput() {
        String thueText = view.txtThue.getText().trim();
        if (thueText.isEmpty()) {
            showError("Thue khong duoc de trong");
            return false;
        }
        try {
            float thue = Float.parseFloat(thueText);
            if (thue < 0) {
                showError("Thue khong duoc la so am");
                return false;
            }
            if (thue > 100) {
                showError("Thue khong duoc vuot qua 100%");
                return false;
            }
        } catch (NumberFormatException exception) {
            showError("Thue phai la so");
            return false;
        }

        String ngayText = view.txtNgay.getText().trim();
        if (ngayText.isEmpty()) {
            showError("Thoi gian khong duoc de trong");
            return false;
        }
        try {
            int ngay = Integer.parseInt(ngayText);
            if (ngay < 0) {
                showError("Thoi gian khong duoc la so am");
                return false;
            }
        } catch (NumberFormatException exception) {
            showError("Thoi gian phai la so nguyen");
            return false;
        }
        return true;
    }

    private boolean xacNhanHuy() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xac nhan");
        alert.setHeaderText(null);
        alert.setContentText("Ban co chac muon huy khong?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void huy() {
        if (xacNhanHuy()) {
            dong();
        }
    }

    private void dong() {
        Stage stage = (Stage) view.btnLuu.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi nhap lieu");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
