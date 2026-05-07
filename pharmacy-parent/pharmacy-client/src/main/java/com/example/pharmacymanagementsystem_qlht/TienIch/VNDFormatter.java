package com.example.pharmacymanagementsystem_qlht.TienIch;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class VNDFormatter {
    public static String format(double amount) {
        return String.format("%,.0f VND", amount);
    }
    public void applyNumberFormatter(TextField textField) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (!change.isContentChange()) return change; // bỏ qua di chuyển chuột, caret

            String newText = change.getControlNewText().replaceAll("[^\\d]", ""); // chỉ giữ số

            if (newText.isEmpty()) {
                change.setText("");
                return change;
            }

            try {
                long value = Long.parseLong(newText);
                String formatted = numberFormat.format(value);

                // Cập nhật textFormatter result, không gọi setText() để tránh loop
                change.setRange(0, change.getControlText().length());
                change.setText(formatted);
                change.setCaretPosition(formatted.length());
                change.setAnchor(formatted.length());
                return change;
            } catch (NumberFormatException e) {
                return null;
            }
        };

        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    public double parseFormattedNumber(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }

        try {
            // Chuẩn bị định dạng theo locale Việt Nam
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat nf = NumberFormat.getInstance(localeVN);

            // Loại bỏ ký tự không phải số, phẩy, chấm
            text = text.replaceAll("[^\\d,\\.]", "");

            // Parse theo format Việt Nam
            return nf.parse(text).doubleValue();

        } catch (Exception e) {
            // Nếu lỗi format, trả về 0
            System.err.println("Không thể chuyển đổi số: " + text);
            return 0.0;
        }
    }
}
