package com.example.pharmacymanagementsystem_qlht.TienIch;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class TuyChinhAlert {
    public static void hien(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(
                TuyChinhAlert.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/ThongBaoAlert.css"
                ).toExternalForm()
        );

        Stage stage = (Stage) pane.getScene().getWindow();

        switch (type) {
            case WARNING:
                pane.getStyleClass().add("warning-alert");
                stage.getIcons().add(new Image(
                        TuyChinhAlert.class.getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/iconcanhbao.jpg")));
                break;

            case INFORMATION:
                pane.getStyleClass().add("info-alert");
                break;

            case ERROR:
                pane.getStyleClass().add("error-alert");
                break;
        }

        stage.setWidth(550);
        stage.setHeight(260);
        alert.showAndWait();
    }
    public static Optional<ButtonType> hienOpt(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(
                TuyChinhAlert.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/ThongBaoAlert.css"
                ).toExternalForm()
        );

        Stage stage = (Stage) pane.getScene().getWindow();

        switch (type) {
            case WARNING:
                pane.getStyleClass().add("warning-alert");
                stage.getIcons().add(new Image(
                        TuyChinhAlert.class.getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/iconcanhbao.jpg")));
                break;
            case INFORMATION:
                pane.getStyleClass().add("info-alert");
                break;
            case ERROR:
                pane.getStyleClass().add("error-alert");
                break;
        }

        stage.setWidth(550);
        stage.setHeight(260);

        // ✅ Trả về kết quả khi người dùng bấm nút
        return alert.showAndWait();
    }
    public static Optional<ButtonType> hoi(String title, String header, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        // custom button
        ButtonType btnYes = new ButtonType("Có", ButtonType.OK.getButtonData());
        ButtonType btnNo = new ButtonType("Không", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(btnYes, btnNo);

        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(
                TuyChinhAlert.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/ThongBaoAlert.css"
                ).toExternalForm()
        );

        pane.getStyleClass().add("confirm-alert");

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setWidth(550);
        stage.setHeight(260);

        return alert.showAndWait();
    }


}
