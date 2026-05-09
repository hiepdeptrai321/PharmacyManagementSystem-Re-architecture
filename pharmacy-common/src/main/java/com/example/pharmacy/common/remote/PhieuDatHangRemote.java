package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.PhieuDatHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhieuDatHangRemote extends Remote {
    String BINDING_NAME = "PhieuDatHangRemoteService";

    String generateNewMaPhieuDatHang() throws RemoteException;

    String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor) throws RemoteException;

    List<PhieuDatHang> findAll() throws RemoteException;

    PhieuDatHang findById(String maPhieuDat) throws RemoteException;

    List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat) throws RemoteException;

    boolean approve(String maPhieuDat, UserContext actor) throws RemoteException;
}
