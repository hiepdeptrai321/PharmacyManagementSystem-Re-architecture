package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.AuditLogClientService;
import com.example.pharmacy.client.service.RmiAuditLogClientService;
import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;
import com.example.pharmacymanagementsystem_qlht.model.HoatDong;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class HoatDongService {
    private final AuditLogClientService auditLogClientService =
            new RmiAuditLogClientService(new RmiClientProvider());

    public List<HoatDong> findAll() {
        return mapLogs(auditLogClientService.findAuditLogs(new AuditLogSearchRequest(null, null, null)));
    }

    public List<HoatDong> search(String keyword, LocalDate fromDate, LocalDate toDate) {
        return mapLogs(auditLogClientService.findAuditLogs(new AuditLogSearchRequest(keyword, fromDate, toDate)));
    }

    public HoatDong findByCode(String auditCode) {
        return mapLog(auditLogClientService.findAuditLogByCode(auditCode));
    }

    private List<HoatDong> mapLogs(List<AuditLogDTO> logs) {
        return logs.stream().map(this::mapLog).toList();
    }

    private HoatDong mapLog(AuditLogDTO log) {
        if (log == null) {
            return null;
        }
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(log.getEmployeeId());
        nhanVien.setTenNV(log.getFullName());
        HoatDong hoatDong = new HoatDong();
        hoatDong.setMaHD(log.getAuditCode());
        hoatDong.setLoaiHD(log.getAction() == null ? "" : log.getAction().name());
        hoatDong.setBang(log.getEntityName());
        hoatDong.setNoiDung(log.getDescription());
        hoatDong.setNhanVien(nhanVien);
        if (log.getCreatedAt() != null) {
            hoatDong.setThoiGian(Timestamp.valueOf(log.getCreatedAt()));
        }
        return hoatDong;
    }
}
