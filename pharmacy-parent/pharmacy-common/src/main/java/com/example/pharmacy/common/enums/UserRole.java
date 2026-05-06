package com.example.pharmacy.common.enums;

import java.io.Serial;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.Locale;

public enum UserRole implements Serializable {
    MANAGER,
    STAFF,
    UNKNOWN;

    @Serial
    private static final long serialVersionUID = 1L;

    public static UserRole fromLegacyValue(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT)
                .trim();
        if ("quan ly".equals(normalized)) {
            return MANAGER;
        }
        if ("nhan vien".equals(normalized)) {
            return STAFF;
        }
        return UNKNOWN;
    }

    public String toLegacyValue() {
        return switch (this) {
            case MANAGER -> "Quan ly";
            case STAFF -> "Nhan vien";
            case UNKNOWN -> "Khong xac dinh";
        };
    }
}
