package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class AuditLogSearchRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String keyword;
    private LocalDate fromDate;
    private LocalDate toDate;

    public AuditLogSearchRequest() {
    }

    public AuditLogSearchRequest(String keyword, LocalDate fromDate, LocalDate toDate) {
        this.keyword = keyword;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
