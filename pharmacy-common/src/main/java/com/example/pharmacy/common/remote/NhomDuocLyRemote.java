package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.NhomDuocLyDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhomDuocLyRemote extends Remote {
    String BINDING_NAME = "NhomDuocLyRemoteService";

    List<NhomDuocLyDto> findAll() throws RemoteException;

    NhomDuocLyDto findById(String maNhomDuocLy) throws RemoteException;

    String generateNewMaNhomDuocLy() throws RemoteException;

    boolean create(NhomDuocLyDto nhomDuocLy) throws RemoteException;

    boolean update(NhomDuocLyDto nhomDuocLy) throws RemoteException;

    boolean deleteById(String maNhomDuocLy) throws RemoteException;

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) throws RemoteException;
}
