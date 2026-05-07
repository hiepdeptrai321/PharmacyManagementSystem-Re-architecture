package com.example.pharmacymanagementsystem_qlht.TienIch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DoiNgay {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
    private DoiNgay() {}

    //public static String dinhDangNgay(Date d) {
      //  if (d == null) return "";
     //   LocalDate ld = d.toLocalDate();
    //    return ld.format(DATE_FORMATTER);
   // }

    public static String dinhDangThoiGian(Timestamp ts) {
        if (ts == null) return "";
        Instant instant = ts.toInstant();
        LocalDate ld = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return ld.format(DATE_FORMATTER);
    }
    public static String dinhDangNgay(LocalDate ld) {
        if (ld == null) return "";
        return ld.format(DATE_FORMATTER); // Dùng luôn formatter bạn đã khai báo
    }

    public static String dinhDangNgay(Date d) {
        if (d == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public static String dinhDangGio(LocalDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }
}

