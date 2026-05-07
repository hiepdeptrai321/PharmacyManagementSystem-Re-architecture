package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietKhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.KhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.LoaiKhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TangKem_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    private final KhuyenMai_Dao khuyenMaiDao = new KhuyenMai_Dao();
    private final LoaiKhuyenMai_Dao loaiKhuyenMaiDao = new LoaiKhuyenMai_Dao();
    private final ChiTietKhuyenMai_Dao chiTietKhuyenMaiDao = new ChiTietKhuyenMai_Dao();
    private final Thuoc_SP_TangKem_Dao thuocSpTangKemDao = new Thuoc_SP_TangKem_Dao();
    private final CodeGenerationService codeGenerationService;

    public KhuyenMaiServiceImpl(CodeGenerationService codeGenerationService) {
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<KhuyenMai> findAll() {
        return khuyenMaiDao.selectAll();
    }

    @Override
    public KhuyenMai findById(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return null;
        }
        try {
            return khuyenMaiDao.selectById(maKhuyenMai);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public List<KhuyenMai> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }
        return khuyenMaiDao.selectByTuKhoa(tuKhoa);
    }

    @Override
    public String generateNewMaKM() {
        return codeGenerationService.nextCode(BusinessCodeType.KHUYEN_MAI);
    }

    @Override
    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() {
        return loaiKhuyenMaiDao.selectAll();
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        if (maLoaiKhuyenMai == null || maLoaiKhuyenMai.isBlank()) {
            return null;
        }
        try {
            return loaiKhuyenMaiDao.selectById(maLoaiKhuyenMai);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        if (tenLoaiKhuyenMai == null || tenLoaiKhuyenMai.isBlank()) {
            return null;
        }
        try {
            return loaiKhuyenMaiDao.selectByTen(tenLoaiKhuyenMai);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return chiTietKhuyenMaiDao.selectByMaKM(maKhuyenMai);
    }

    @Override
    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return thuocSpTangKemDao.selectByMaKM(maKhuyenMai);
    }

    @Override
    public boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (khuyenMai == null) {
            return false;
        }
        if (khuyenMai.getMaKM() == null || khuyenMai.getMaKM().isBlank()) {
            khuyenMai.setMaKM(generateNewMaKM());
        }
        try {
            if (!khuyenMaiDao.insert(khuyenMai)) {
                return false;
            }
            saveChiTiet(khuyenMai, chiTietKhuyenMais);
            saveQuaTang(khuyenMai, quaTangKhuyenMais);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (khuyenMai == null || khuyenMai.getMaKM() == null || khuyenMai.getMaKM().isBlank()) {
            return false;
        }
        try {
            if (!khuyenMaiDao.update(khuyenMai)) {
                return false;
            }
            chiTietKhuyenMaiDao.deleteByMaKM(khuyenMai.getMaKM());
            thuocSpTangKemDao.deleteByMaKM(khuyenMai.getMaKM());
            saveChiTiet(khuyenMai, chiTietKhuyenMais);
            saveQuaTang(khuyenMai, quaTangKhuyenMais);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return false;
        }
        try {
            thuocSpTangKemDao.deleteByMaKM(maKhuyenMai);
            chiTietKhuyenMaiDao.deleteByMaKM(maKhuyenMai);
            return khuyenMaiDao.deleteByMaKM(maKhuyenMai);
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<KhuyenMai> findActiveOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return khuyenMaiDao.selectActiveOn(ngay);
    }

    @Override
    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return khuyenMaiDao.selectActiveInvoiceOn(ngay);
    }

    private void saveChiTiet(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais) {
        if (chiTietKhuyenMais == null) {
            return;
        }
        for (ChiTietKhuyenMai chiTietKhuyenMai : chiTietKhuyenMais) {
            if (chiTietKhuyenMai == null || chiTietKhuyenMai.getThuoc() == null) {
                continue;
            }
            chiTietKhuyenMai.setKhuyenMai(khuyenMai);
            chiTietKhuyenMaiDao.insert(chiTietKhuyenMai);
        }
    }

    private void saveQuaTang(KhuyenMai khuyenMai, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (quaTangKhuyenMais == null) {
            return;
        }
        for (Thuoc_SP_TangKem quaTangKhuyenMai : quaTangKhuyenMais) {
            if (quaTangKhuyenMai == null || quaTangKhuyenMai.getThuocTangKem() == null) {
                continue;
            }
            quaTangKhuyenMai.setKhuyenmai(khuyenMai);
            thuocSpTangKemDao.insert(quaTangKhuyenMai);
        }
    }
}
