package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.AuditLogClientService;
import com.example.pharmacy.client.service.RmiAuditLogClientService;
import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;
import com.example.pharmacy.common.model.HoatDongDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class HoatDongService {
    private final AuditLogClientService auditLogClientService =
            new RmiAuditLogClientService(new RmiClientProvider());

    public List<HoatDongDto> findAll() {
        return mapLogs(auditLogClientService.findAuditLogs(new AuditLogSearchRequest(null, null, null)));
    }

    public List<HoatDongDto> search(String keyword, LocalDate fromDate, LocalDate toDate) {
        return mapLogs(auditLogClientService.findAuditLogs(new AuditLogSearchRequest(keyword, fromDate, toDate)));
    }

    public HoatDongDto findByCode(String auditCode) {
        return mapLog(auditLogClientService.findAuditLogByCode(auditCode));
    }

    private List<HoatDongDto> mapLogs(List<AuditLogDTO> logs) {
        return logs.stream().map(this::mapLog).toList();
    }

    private HoatDongDto mapLog(AuditLogDTO log) {
        if (log == null) {
            return null;
        }
        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(log.getEmployeeId());
        nhanVien.setTenNV(log.getFullName());
        HoatDongDto hoatDong = new HoatDongDto();
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
