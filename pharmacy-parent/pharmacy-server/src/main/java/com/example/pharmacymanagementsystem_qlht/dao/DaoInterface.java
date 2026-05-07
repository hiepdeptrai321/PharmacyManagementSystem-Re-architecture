package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;

import java.util.List;

public interface DaoInterface<Entity> {

        boolean insert(Entity e);
        boolean update(Entity e);
        boolean deleteById(Object... keys);
    Entity selectById(Object... keys);
        List<Entity> selectBySql(String sql, Object... args);
        List<Entity> selectAll();

}
