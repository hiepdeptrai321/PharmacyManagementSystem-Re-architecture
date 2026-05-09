package com.example.pharmacy.common.enums;

import java.io.Serial;
import java.io.Serializable;

public enum AuditAction implements Serializable {
    CREATE,
    UPDATE,
    DELETE,
    LOGIN,
    LOGOUT,
    IMPORT,
    PAYMENT,
    RETURN,
    APPROVE,
    REPORT,
    OTHER;

    @Serial
    private static final long serialVersionUID = 1L;
}
