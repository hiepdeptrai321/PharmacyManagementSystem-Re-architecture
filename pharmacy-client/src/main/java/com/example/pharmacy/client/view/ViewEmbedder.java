package com.example.pharmacy.client.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class ViewEmbedder {

    private ViewEmbedder() {}

    public static Parent buildFromShowWithController(Object guiInstance, Object ctrlInstance) {
        try {
            Method m = Arrays.stream(guiInstance.getClass().getMethods())
                    .filter(me -> me.getName().equals("showWithController"))
                    .filter(me -> {
                        Class<?>[] p = me.getParameterTypes();
                        return p.length == 2
                                && Stage.class.isAssignableFrom(p[0])
                                && p[1].isAssignableFrom(ctrlInstance.getClass());
                    })
                    .findFirst()
                    .orElseThrow(() -> new NoSuchMethodException(
                            "Không tìm thấy showWithController(Stage, " + ctrlInstance.getClass().getSimpleName()
                                    + ") trong " + guiInstance.getClass().getSimpleName()));

            Stage phantom = new Stage();
            m.invoke(guiInstance, phantom, ctrlInstance);
            Scene sc = phantom.getScene();
            if (sc == null || sc.getRoot() == null) {
                throw new IllegalStateException("GUI chưa setScene(root) bên trong showWithController.");
            }
            Parent root = sc.getRoot();
            phantom.setScene(null);
            return root;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi embed view: " + guiInstance.getClass().getName(), ex);
        }
    }

    public static void mountInto(Pane host, Parent root) {
        host.getChildren().setAll(root);
        if (root instanceof Region r) {
            r.prefWidthProperty().bind(host.widthProperty());
            r.prefHeightProperty().bind(host.heightProperty());
        } else {
            host.widthProperty().addListener((o, ov, nv) ->
                    root.resizeRelocate(0, 0, nv.doubleValue(), host.getHeight()));
            host.heightProperty().addListener((o, ov, nv) ->
                    root.resizeRelocate(0, 0, host.getWidth(), nv.doubleValue()));
        }
    }
}
