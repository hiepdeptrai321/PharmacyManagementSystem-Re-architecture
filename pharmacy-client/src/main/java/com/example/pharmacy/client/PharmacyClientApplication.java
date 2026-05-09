package com.example.pharmacy.client;

import com.example.pharmacy.client.controller.DangNhap_Ctrl;
import javafx.application.Application;
import javafx.stage.Stage;

public final class PharmacyClientApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new DangNhap_Ctrl().start(primaryStage);
        primaryStage.show();
    }
}
