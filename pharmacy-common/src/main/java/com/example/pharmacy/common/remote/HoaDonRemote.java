package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.HoaDonDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HoaDonRemote extends Remote {
    String BINDING_NAME = "HoaDonRemoteService";

    String generateNewMaHoaDon() throws RemoteException;

    String createInvoice(CreateHoaDonRequest request, UserContext actor) throws RemoteException;

    List<HoaDonDto> findAll() throws RemoteException;

    HoaDonDto findById(String maHoaDon) throws RemoteException;

    List<ChiTietHoaDonDto> findDetailsByMaHD(String maHoaDon) throws RemoteException;

    List<HoaDonDto> search(HoaDonSearchRequest request) throws RemoteException;
}
