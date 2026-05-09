package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.DoiTraClientService;
import com.example.pharmacy.client.service.RmiDoiTraClientService;
import com.example.pharmacy.common.request.CreatePhieuDoiItemRequest;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraItemRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;

import java.math.BigDecimal;
import java.util.List;

public class DoiTraService {
    private final DoiTraClientService doiTraClientService =
            new RmiDoiTraClientService(new RmiClientProvider());

    public HoaDonDto findHoaDonGocForDoiTra(String maHoaDon) {
        return doiTraClientService.findHoaDonGocForDoiTra(maHoaDon);
    }

    public List<ChiTietHoaDonDto> findHoaDonDetailsForDoiTra(String maHoaDon) {
        return doiTraClientService.findHoaDonDetailsForDoiTra(maHoaDon);
    }

    public int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) {
        return doiTraClientService.getSoLuongDaDoi(maHoaDon, maLoHang, maDonViTinh);
    }

    public int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) {
        return doiTraClientService.getSoLuongDaTra(maHoaDon, maLoHang, maDonViTinh);
    }

    public String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) {
        return doiTraClientService.createPhieuDoi(request, UserContextMapper.toRemoteUserContext(actor));
    }

    public String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) {
        return doiTraClientService.createPhieuTra(request, UserContextMapper.toRemoteUserContext(actor));
    }

    public void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) {
        doiTraClientService.attachKhachHangToHoaDon(
                maHoaDon,
                maKhachHang,
                UserContextMapper.toRemoteUserContext(actor)
        );
    }

    public List<PhieuDoiHangDto> findAllPhieuDoi() {
        return doiTraClientService.findAllPhieuDoi();
    }

    public PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi) {
        return doiTraClientService.findPhieuDoiById(maPhieuDoi);
    }

    public List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi) {
        return doiTraClientService.findChiTietPhieuDoiByMaPD(maPhieuDoi);
    }

    public List<PhieuTraHangDto> findAllPhieuTra() {
        return doiTraClientService.findAllPhieuTra();
    }

    public PhieuTraHangDto findPhieuTraById(String maPhieuTra) {
        return doiTraClientService.findPhieuTraById(maPhieuTra);
    }

    public List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra) {
        return doiTraClientService.findChiTietPhieuTraByMaPT(maPhieuTra);
    }

    public CreatePhieuDoiRequest buildCreatePhieuDoiRequest(
            HoaDonDto hoaDonGoc,
            List<DoiHangItem> items,
            String ghiChu
    ) {
        CreatePhieuDoiRequest request = new CreatePhieuDoiRequest();
        request.setMaHoaDonGoc(hoaDonGoc.getMaHD());
        request.setMaKhachHang(hoaDonGoc.getMaKH().getMaKH());
        request.setNgayLap(java.time.LocalDate.now());
        request.setGhiChu(ghiChu == null ? "" : ghiChu.trim());
        for (DoiHangItem item : items) {
            if (item == null || item.getGoc() == null || item.getGoc().getLoHang() == null
                    || item.getGoc().getLoHang().getThuoc() == null || item.getGoc().getDvt() == null) {
                continue;
            }
            CreatePhieuDoiItemRequest line = new CreatePhieuDoiItemRequest();
            line.setMaLoHang(item.getGoc().getLoHang().getMaLH());
            line.setMaThuoc(item.getGoc().getLoHang().getThuoc().getMaThuoc());
            line.setMaDonViTinh(item.getGoc().getDvt().getMaDVT());
            line.setSoLuong(item.getSoLuongDoi());
            line.setLyDoDoi(item.getLyDo());
            request.getItems().add(line);
        }
        return request;
    }

    public CreatePhieuTraRequest buildCreatePhieuTraRequest(
            HoaDonDto hoaDonGoc,
            List<TraHangItem> items,
            String ghiChu
    ) {
        CreatePhieuTraRequest request = new CreatePhieuTraRequest();
        request.setMaHoaDonGoc(hoaDonGoc.getMaHD());
        request.setMaKhachHang(hoaDonGoc.getMaKH().getMaKH());
        request.setNgayLap(java.time.LocalDate.now());
        request.setGhiChu(ghiChu == null ? "" : ghiChu.trim());
        for (TraHangItem item : items) {
            if (item == null || item.getGoc() == null || item.getGoc().getLoHang() == null
                    || item.getGoc().getLoHang().getThuoc() == null || item.getGoc().getDvt() == null) {
                continue;
            }
            CreatePhieuTraItemRequest line = new CreatePhieuTraItemRequest();
            line.setMaLoHang(item.getGoc().getLoHang().getMaLH());
            line.setMaThuoc(item.getGoc().getLoHang().getThuoc().getMaThuoc());
            line.setMaDonViTinh(item.getGoc().getDvt().getMaDVT());
            line.setSoLuong(item.getSoLuongTra());
            line.setDonGia(BigDecimal.valueOf(item.getGoc().getDonGia()));
            line.setGiamGia(BigDecimal.valueOf(item.getGoc().getGiamGia()));
            line.setLyDoTra(item.getLyDo());
            request.getItems().add(line);
        }
        return request;
    }
}
