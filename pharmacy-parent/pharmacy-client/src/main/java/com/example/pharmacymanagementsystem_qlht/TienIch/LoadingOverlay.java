package com.example.pharmacymanagementsystem_qlht.TienIch;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoadingOverlay extends StackPane {

    private final VBox box;

    public LoadingOverlay() {
        setPickOnBounds(true); // chặn click xuống dưới
        setVisible(false);
        setManaged(false);     // không ảnh hưởng layout khi ẩn

        // nền mờ
        Region background = new Region();
        background.setStyle("-fx-background-color: rgba(0,0,0,0.25);");
        background.prefWidthProperty().bind(widthProperty());
        background.prefHeightProperty().bind(heightProperty());

        ProgressIndicator indicator = new ProgressIndicator();
        Label label = new Label("Đang tải dữ liệu...");
        label.setTextFill(Color.WHITE);

        box = new VBox(10, indicator, label);
        box.setAlignment(Pos.CENTER);

        getChildren().addAll(background, box);
        setAlignment(Pos.CENTER);
    }

    public void show() {
        setVisible(true);
        setManaged(true);
        toFront();
    }

    public void hide() {
        setVisible(false);
        setManaged(false);
    }
}