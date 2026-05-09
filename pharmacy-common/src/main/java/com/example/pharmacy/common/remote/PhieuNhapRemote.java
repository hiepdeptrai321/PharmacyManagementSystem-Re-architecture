package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhieuNhapRemote extends Remote {
    String BINDING_NAME = "PhieuNhapRemoteService";

    String generateNewMaPhieuNhap() throws RemoteException;

    String createPurchaseOrder(PhieuNhapRequest request, UserContext actor) throws RemoteException;

    List<PhieuNhapDto> findAll() throws RemoteException;

    PhieuNhapDto findById(String maPhieuNhap) throws RemoteException;

    List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) throws RemoteException;
}
