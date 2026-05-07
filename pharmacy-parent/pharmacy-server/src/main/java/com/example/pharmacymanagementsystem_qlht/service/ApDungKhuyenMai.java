package com.example.pharmacymanagementsystem_qlht.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApDungKhuyenMai {
    private BigDecimal discount = BigDecimal.ZERO;
    private final Map<String, Integer> freeItems = new HashMap<>(); // MaThuoc -> SoLuong
    private final List<String> appliedMaKM = new ArrayList<>();

    public BigDecimal getDiscount() { return discount; }
    public void addDiscount(BigDecimal add) { this.discount = this.discount.add(add); }
    public Map<String, Integer> getFreeItems() { return freeItems; }
    public void addFreeItem(String maThuoc, int sl) { freeItems.merge(maThuoc, sl, Integer::sum); }
    public List<String> getAppliedMaKM() { return appliedMaKM; }
    public void addApplied(String maKM) { appliedMaKM.add(maKM); }
}
