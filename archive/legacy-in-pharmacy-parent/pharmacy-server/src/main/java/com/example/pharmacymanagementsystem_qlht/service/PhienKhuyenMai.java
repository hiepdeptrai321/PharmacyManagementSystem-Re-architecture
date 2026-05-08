package com.example.pharmacymanagementsystem_qlht.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PhienKhuyenMai {
    private static final Map<String, Integer> soHDDaDung = new HashMap<>();
    private static final ConcurrentMap<String, AtomicInteger> counter = new ConcurrentHashMap<>();

    private static String key(String maKM, String maThuoc) {
        String today = java.time.LocalDate.now().toString();
        return maKM + "|" + maThuoc + "|" + today;
    }

    // Check remaining slots
    public static boolean conLuot(String maKM, String maThuoc, int soHDToiDa) {
        if (soHDToiDa <= 0) return true;

        var k = key(maKM, maThuoc);
        var cur = counter.computeIfAbsent(k, kk -> new AtomicInteger(0));
        return cur.get() < soHDToiDa;
    }

    // Reserve one slot atomically when discount is applied
    public static boolean tangLuotIfAvailable(String maKM, String maThuoc, int soHDToiDa) {
        var k = key(maKM, maThuoc);
        var cur = counter.computeIfAbsent(k, kk -> new AtomicInteger(0));
        while (true) {
            int val = cur.get();
            if (val >= soHDToiDa) return false;
            if (cur.compareAndSet(val, val + 1)) return true;
        }
    }
    public static int soLuotConLai(String maKM, String maThuoc, int soHDToiDa) {
        if (soHDToiDa <= 0) return Integer.MAX_VALUE;
        var k = key(maKM, maThuoc);
        var cur = counter.getOrDefault(k, new AtomicInteger(0)).get();
        return Math.max(0, soHDToiDa - cur);
    }

    public static void clearToday() {
        String today = java.time.LocalDate.now().toString();
        counter.keySet().removeIf(k -> k.endsWith("|" + today));
    }
}
