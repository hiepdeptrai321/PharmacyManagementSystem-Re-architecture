package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat.caiDat_Ctrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.List;

public class caiDat_GUI {
    public TextField txtThue;
    public ComboBox<String> cbxdonViNgay;
    public TextField txtNgay;
    public Button btnLuu;
    public Button btnHuy;
    private Parent root;

    public Parent createUI() {
        BorderPane borderPane = new BorderPane();

        VBox pnlChinh = new VBox();
        pnlChinh.setSpacing(10);
        Line line = new Line(0, 0, 310, 0);
        line.setStroke(Color.gray(0.2));
        line.setStrokeWidth(1);
        VBox vbox = new VBox();

        HBox pnlThue = new HBox();
        pnlThue.setSpacing(10);
        Label lblThue = new Label("Thue (%):");
        txtThue = new TextField();
        txtThue.setPrefSize(35, 25);
        Region paddingTraiThue = new Region();
        paddingTraiThue.setPadding(new Insets(0, 88, 0, 0));
        pnlThue.getChildren().addAll(paddingTraiThue, lblThue, txtThue);
        pnlChinh.getChildren().add(pnlThue);

        HBox pnlNgayHetHan = new HBox();
        pnlNgayHetHan.setSpacing(10);
        Label lblCanhBaoNgayHetHan = new Label("Thoi gian canh bao het han:");
        txtNgay = new TextField();
        txtNgay.setPrefSize(35, 25);
        cbxdonViNgay = new ComboBox<>();
        List<String> items = Arrays.asList("Ngay", "Thang", "Nam");
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        cbxdonViNgay.setItems(observableItems);
        cbxdonViNgay.getSelectionModel().select(0);
        pnlNgayHetHan.getChildren().addAll(lblCanhBaoNgayHetHan, txtNgay, cbxdonViNgay);
        pnlChinh.getChildren().add(pnlNgayHetHan);

        Region paddingTrai = new Region();
        paddingTrai.setPadding(new Insets(0, 5, 0, 5));
        borderPane.setLeft(paddingTrai);
        Region paddingPhai = new Region();
        paddingPhai.setPadding(new Insets(0, 5, 0, 5));
        borderPane.setRight(paddingPhai);

        btnLuu = new Button("Luu");
        btnLuu.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #0c81ff; -fx-font-weight: bold");
        btnHuy = new Button("Huy");
        btnHuy.setStyle("-fx-text-fill: #fbfbfb; -fx-background-color: #F0AD4EFF; -fx-font-weight: bold");
        Region paddingTraiNut = new Region();
        paddingTraiNut.setPadding(new Insets(0, 165, 0, 20));
        HBox bottomPane = new HBox(paddingTraiNut, btnHuy, btnLuu);
        bottomPane.setSpacing(10);
        pnlChinh.getChildren().add(bottomPane);
        borderPane.setCenter(pnlChinh);

        Label lblTitle = new Label("Cai dat");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox topPane = new HBox(lblTitle);
        topPane.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(topPane, line);
        vbox.setPadding(new Insets(0, 0, 10, 0));
        borderPane.setTop(vbox);

        this.root = borderPane;
        new caiDat_Ctrl(this);
        return root;
    }

    public Parent getRoot() {
        return root;
    }
}
