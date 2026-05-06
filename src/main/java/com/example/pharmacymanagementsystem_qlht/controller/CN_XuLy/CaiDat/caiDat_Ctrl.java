package com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat;

import com.example.pharmacymanagementsystem_qlht.controller.CuaSoChinh_QuanLy_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;
import com.example.pharmacymanagementsystem_qlht.service.CaiDatService;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class caiDat_Ctrl {
    private final caiDat_GUI view;
    private final CaiDatService caiDatService;
    private List<CaiDat> caiDat_List;

    public caiDat_Ctrl(caiDat_GUI view) {
        this.view = view;
        caiDatService = new CaiDatService();
        caiDat_List = new ArrayList<>();
        caiDat_List = caiDatService.findAll();
        loadThongSo();
        initEvent();
    }

    public void loadThongSo() {
        for (CaiDat caiDat : caiDat_List) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue": {
                    Float giaTriThue = Float.parseFloat(caiDat.getGiaTri()) * 100;
                    String[] giaTriThueSplit = giaTriThue.toString().split("\\.");

                    view.txtThue.setText(giaTriThueSplit[0]);
                }
                break;
                case "NgayHetHan": {
                    int soNgay = Integer.parseInt(caiDat.getGiaTri());
                    view.txtNgay.setText(soNgay + "");
                    view.cbxdonViNgay.getSelectionModel().select(0);

                }
                break;
                default:
                    break;
            }
        }
    }

    private void initEvent() {
        view.btnLuu.setOnAction(e -> luu());
        view.btnHuy.setOnAction(e -> huy());
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
            return;
        }

        for (CaiDat caiDat : caiDat_List) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue":
                    if (cachLuu == 1 || cachLuu == 3) {
                        Float thueTemp = Float.parseFloat(view.txtThue.getText());
                        String thueDaChuyen = String.valueOf(thueTemp / 100);
                        caiDat.setGiaTri(thueDaChuyen);
                        caiDatService.update(caiDat);
                    }
                    break;

                case "NgayHetHan":
                    if (cachLuu == 2 || cachLuu == 3) {
                        String donVi = view.cbxdonViNgay.getValue();
                        int soNgay = Integer.parseInt(view.txtNgay.getText());
                        switch (donVi) {
                            case "ThÃ¡ng":
                                soNgay *= 30;
                                break;
                            case "NÄƒm":
                                soNgay *= 365;
                                break;
                            default:
                                break;
                        }
                        caiDat.setGiaTri(String.valueOf(soNgay));
                        caiDatService.update(caiDat);
                    }
                    break;
                default:
                    break;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("HoÃ n táº¥t");
        alert.setHeaderText(null);
        alert.setContentText("LÆ°u cÃ i Ä‘áº·t thÃ nh cÃ´ng.\nBáº¡n cÃ³ muá»‘n khá»Ÿi Ä‘á»™ng láº¡i á»©ng dá»¥ng Ä‘á»ƒ Ã¡p dá»¥ng thay Ä‘á»•i khÃ´ng?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            restartApp();
        }
        dong();
    }

    private void restartApp() {
        Stage currentStage = (Stage) view.btnLuu.getScene().getWindow();
        currentStage.close();
        CuaSoChinh_QuanLy_Ctrl.instance.dong();

        javafx.application.Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                new com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl()
                        .start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private int cachLuu() {
        int cachLuu = 0;
        for (CaiDat caiDat : caiDat_List) {
            switch (caiDat.getTenThongSo()) {
                case "GiaTriThue": {
                    Float thueTemp = Float.parseFloat(view.txtThue.getText());
                    String thueDaChuyen = String.valueOf(thueTemp / 100);
                    if (!thueDaChuyen.equals(caiDat.getGiaTri())) {
                        cachLuu += 1;
                    }
                }
                break;
                case "NgayHetHan": {
                    if (!view.txtNgay.getText().equals(caiDat.getGiaTri()) || view.cbxdonViNgay.getSelectionModel().getSelectedIndex() != 0) {
                        cachLuu += 2;
                    }
                }
                break;
                default:
                    break;
            }
        }
        return cachLuu;
    }

    private boolean xacNhanLuu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÃ¡c nháº­n lÆ°u");
        alert.setHeaderText(null);
        alert.setContentText("Báº¡n cÃ³ cháº¯c cháº¯n muá»‘n lÆ°u cÃ¡c thay Ä‘á»•i khÃ´ng?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean validateInput() {
        String thueText = view.txtThue.getText().trim();

        if (thueText.isEmpty()) {
            showError("Thuáº¿ khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng");
            return false;
        }

        try {
            float thue = Float.parseFloat(thueText);

            if (thue < 0) {
                showError("Thuáº¿ khÃ´ng Ä‘Æ°á»£c lÃ  sá»‘ Ã¢m");
                return false;
            }

            if (thue > 100) {
                showError("Thuáº¿ khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 100%");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Thuáº¿ pháº£i lÃ  sá»‘");
            return false;
        }

        String ngayText = view.txtNgay.getText().trim();

        if (ngayText.isEmpty()) {
            showError("Thá»i gian khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng");
            return false;
        }

        try {
            int ngay = Integer.parseInt(ngayText);

            if (ngay < 0) {
                showError("Thá»i gian khÃ´ng Ä‘Æ°á»£c lÃ  sá»‘ Ã¢m");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Thá»i gian pháº£i lÃ  sá»‘ nguyÃªn");
            return false;
        }

        return true;
    }

    private boolean xacNhanHuy() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÃ¡c nháº­n");
        alert.setHeaderText(null);
        alert.setContentText("Báº¡n cÃ³ cháº¯c muá»‘n há»§y khÃ´ng?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lá»—i nháº­p liá»‡u");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private void huy() {
        if (!xacNhanHuy()) {
            return;
        }
        dong();
    }

    private void dong() {
        Stage stage = (Stage) view.btnLuu.getScene().getWindow();
        stage.close();
    }
}
