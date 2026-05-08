package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CaiDat_Dao implements DaoInterface<CaiDat>{
//    private final String INSERT_SQL = "INSERT INTO ThongSoUngDung (TenThongSo,GiaTri) VALUES (?,?)";
    private final String UPDATE_SQL = "UPDATE ThongSoUngDung SET GiaTri=? WHERE TenThongSo=?";
    private final String SELECT_SQL = "SELECT * FROM CaiDat WHERE TenThongSo=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM ThongSoUngDung";

    @Override
    public boolean insert(CaiDat e) {
        return false;
    }

    @Override
    public boolean update(CaiDat e) {
        return ConnectDB.update(UPDATE_SQL,e.getGiaTri(),e.getTenThongSo())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return false;
    }

    @Override
    public CaiDat selectById(Object... keys) {
        return this.selectBySql(SELECT_SQL,keys[0]).get(0);
    }

    @Override
    public List<CaiDat> selectBySql(String sql, Object... args) {
        List<CaiDat> list = new ArrayList<>();
        try{
            ResultSet rs = ConnectDB.query(sql, args);
            while(rs.next()){
                CaiDat caiDat = new CaiDat();
                caiDat.setTenThongSo(rs.getString("TenThongSo"));
                caiDat.setGiaTri(rs.getString("GiaTri"));
                list.add(caiDat);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<CaiDat> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
}
