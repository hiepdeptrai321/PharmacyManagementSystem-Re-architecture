package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.repository.ReportRepository;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = Objects.requireNonNull(reportRepository, "reportRepository must not be null");
    }

    @Override
    public List<ThongKeBanHangDto> getThongKeBanHang(String preset) {
        DateRangeRequest range = resolvePresetDateRange(preset);
        return reportRepository.findThongKeBanHangByDateRange(range, resolveBucketForPreset(preset));
    }

    @Override
    public List<ThongKeBanHangDto> getThongKeBanHangByDateRange(DateRangeRequest request) {
        validateDateRangeRequest(request);
        return reportRepository.findThongKeBanHangByDateRange(request, ReportRepository.ReportBucket.DAY);
    }

    @Override
    public List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset) {
        return reportRepository.findHoaDonByDateRange(resolvePresetDateRange(preset));
    }

    @Override
    public List<HoaDonDisplayDto> getHoaDonTheoDateRange(DateRangeRequest request) {
        validateDateRangeRequest(request);
        return reportRepository.findHoaDonByDateRange(request);
    }

    @Override
    public List<ThongKeTopSanPhamDto> getTopBanChay(DateRangeRequest request, int limit) {
        validateDateRangeRequest(request);
        validateLimit(limit);
        return reportRepository.findTopBanChayByDateRange(request, limit);
    }

    @Override
    public List<ThongKeTopSanPhamDto> getTopDoanhThu(DateRangeRequest request, int limit) {
        validateDateRangeRequest(request);
        validateLimit(limit);
        return reportRepository.findTopDoanhThuByDateRange(request, limit);
    }

    @Override
    public List<ThongKeTonKhoDto> getThongKeXnt(DateRangeRequest request) {
        validateDateRangeRequest(request);
        return reportRepository.findThongKeXnt(request);
    }

    @Override
    public List<ThuocHetHanDto> getThuocHetHan() {
        return reportRepository.findThuocHetHan();
    }

    @Override
    public List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request) {
        validateDateRangeRequest(request);
        return reportRepository.findRevenueByDateRange(request);
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit) {
        validateDateRangeRequest(request);
        validateLimit(limit);
        return reportRepository.findTopSellingProducts(request, limit);
    }

    @Override
    public List<ExpiringLotDTO> getExpiredLots() {
        return reportRepository.findExpiredLots();
    }

    @Override
    public List<ExpiringLotDTO> getExpiringLots(int thresholdDays) {
        if (thresholdDays < 0) {
            throw new BusinessException("Nguong canh bao het han khong hop le.");
        }
        return reportRepository.findExpiringLots(thresholdDays);
    }

    private void validateDateRangeRequest(DateRangeRequest request) {
        if (request == null || request.getFromDate() == null || request.getToDate() == null) {
            throw new BusinessException("Khoang ngay bao cao khong hop le.");
        }
        if (request.getToDate().isBefore(request.getFromDate())) {
            throw new BusinessException("Ngay ket thuc khong duoc truoc ngay bat dau.");
        }
    }

    private void validateLimit(int limit) {
        if (limit <= 0) {
            throw new BusinessException("Limit phai lon hon 0.");
        }
    }

    private DateRangeRequest resolvePresetDateRange(String preset) {
        String normalized = normalizePreset(preset);
        LocalDate now = LocalDate.now();
        return switch (normalized) {
            case "TODAY" -> new DateRangeRequest(now, now);
            case "THIS_WEEK" -> new DateRangeRequest(now.with(DayOfWeek.MONDAY), now.with(DayOfWeek.SUNDAY));
            case "THIS_MONTH" -> new DateRangeRequest(now.withDayOfMonth(1), now.withDayOfMonth(now.lengthOfMonth()));
            case "THIS_YEAR" -> new DateRangeRequest(now.withDayOfYear(1), now.withDayOfYear(now.lengthOfYear()));
            default -> throw new BusinessException("Moc thoi gian thong ke khong hop le.");
        };
    }

    private ReportRepository.ReportBucket resolveBucketForPreset(String preset) {
        String normalized = normalizePreset(preset);
        return switch (normalized) {
            case "TODAY" -> ReportRepository.ReportBucket.HOUR;
            case "THIS_YEAR" -> ReportRepository.ReportBucket.MONTH;
            default -> ReportRepository.ReportBucket.DAY;
        };
    }

    private String normalizePreset(String preset) {
        if (preset == null) {
            return "";
        }
        String value = preset.trim();
        return switch (value) {
            case "Hôm nay", "HÃ´m nay" -> "TODAY";
            case "Tuần này", "Tuáº§n nÃ y" -> "THIS_WEEK";
            case "Tháng này", "ThÃ¡ng nÃ y" -> "THIS_MONTH";
            case "Năm nay", "NÄƒm nay", "Năm Nay", "NÄƒm Nay" -> "THIS_YEAR";
            default -> value;
        };
    }
}
