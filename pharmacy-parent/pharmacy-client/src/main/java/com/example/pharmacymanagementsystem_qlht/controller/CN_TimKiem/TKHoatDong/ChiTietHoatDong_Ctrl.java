package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong;

import com.example.pharmacymanagementsystem_qlht.model.HoatDong;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ChiTietHoatDong_Ctrl extends Application {

    public TextField tfMaHD;
    public TextField tfLoaiHD;
    public TextField tfThoiGian;
    public TextField tfMaNV;
    public TextField tfTenNV;
    public TextField tfBang;
    public TextArea tfNoiDung;
    public Button btnHuy;

    private final SimpleDateFormat tsFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    private HoatDong currentHd;

    public void initialize() {
        if (btnHuy != null) {
            btnHuy.setOnAction(e -> {
                Window w = btnHuy.getScene() == null ? null : btnHuy.getScene().getWindow();
                if (w != null) w.hide();
            });
        }
        Platform.runLater(()->{
            Stage dialog = (Stage) tfMaHD.getScene().getWindow();
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });
    }

    // Allows external code to pass HoatDong; stores it and applies when UI is ready
    public void loadData(HoatDong hd) {
        this.currentHd = hd;
        if (tfMaHD == null) {
            // UI not initialized yet; data will be applied in start()
            return;
        }
        if (hd == null) return;

        tfMaHD.setText(nullSafe(hd.getMaHD()));
        tfLoaiHD.setText(nullSafe(hd.getLoaiHD()));

        Timestamp t = hd.getThoiGian();
        tfThoiGian.setText(t == null ? "" : tsFormat.format(t));

        tfBang.setText(nullSafe(hd.getBang()));
        tfNoiDung.setText(nullSafe(hd.getNoiDung()));

        Object nv = hd.getNhanVien();
        if (nv == null) {
            tfMaNV.setText("");
            tfTenNV.setText("");
            return;
        }


        String ma = tryInvokeStringMethod(nv, "getMaNV", "getMaNhanVien", "getId", "getMa");
        String ten = tryInvokeStringMethod(nv,  "getTenNV","toString");
        if (ten == null || ten.isEmpty()) ten = nv.toString();

        tfMaNV.setText(nullSafe(ma));
        tfTenNV.setText(nullSafe(ten));
    }

    private String tryInvokeStringMethod(Object obj, String... methodCandidates) {
        for (String mName : methodCandidates) {
            try {
                Method m = obj.getClass().getMethod(mName);
                Object res = m.invoke(obj);
                if (res != null) return res.toString();
            } catch (NoSuchMethodException ignored) {
            } catch (Exception ignored) {
            }
        }
        return "";
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoatDong.ChiTietHoatDong_GUI()
                .showWithController(stage, this);
    }
}