package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhieuDatHangRemote extends Remote {
    String BINDING_NAME = "PhieuDatHangRemoteService";

    String generateNewMaPhieuDatHang() throws RemoteException;

    String create(PhieuDatHangDto phieuDatHang, List<ChiTietPhieuDatHangDto> details, UserContext actor) throws RemoteException;

    List<PhieuDatHangDto> findAll() throws RemoteException;

    PhieuDatHangDto findById(String maPhieuDat) throws RemoteException;

    List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat) throws RemoteException;

    boolean approve(String maPhieuDat, UserContext actor) throws RemoteException;
}
