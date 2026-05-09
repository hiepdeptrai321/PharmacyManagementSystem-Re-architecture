package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.server.repository.TonKhoRepository;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;

import java.util.List;
import java.util.Objects;

public class TonKhoServiceImpl implements TonKhoService {
    private final TonKhoRepository tonKhoRepository;
    private final AuditService auditService;

    public TonKhoServiceImpl(TonKhoRepository tonKhoRepository, AuditService auditService) {
        this.tonKhoRepository = Objects.requireNonNull(tonKhoRepository, "tonKhoRepository must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public List<Thuoc_SP_TheoLo> findAllLots() {
        return tonKhoRepository.findAllLots();
    }

    @Override
    public boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo, UserContext actor) {
        if (thuocTheoLo == null || thuocTheoLo.getMaLH() == null || thuocTheoLo.getMaLH().isBlank()) {
            throw new BusinessException("Thong tin lo thuoc khong hop le.");
        }
        if (thuocTheoLo.getSoLuongTon() < 0) {
            throw new BusinessException("So luong ton khong duoc am.");
        }
        if (actor == null || actor.getEmployeeId() == null || actor.getEmployeeId().isBlank()) {
            throw new BusinessException("UserContext khong hop le de cap nhat ton kho.");
        }

        boolean updated = tonKhoRepository.updateLotQuantity(thuocTheoLo);
        if (updated) {
            String maThuoc = thuocTheoLo.getThuoc() == null ? "" : thuocTheoLo.getThuoc().getMaThuoc();
            auditService.logAction(actor, AuditAction.UPDATE, "Thuoc_SP_TheoLo", thuocTheoLo.getMaLH(),
                    "Cap nhat so luong ton cho lo " + thuocTheoLo.getMaLH() + " cua thuoc " + maThuoc + ".");
        }
        return updated;
    }
}
