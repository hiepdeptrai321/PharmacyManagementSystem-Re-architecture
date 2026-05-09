package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.NhomDuocLy;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhomDuocLyRemote extends Remote {
    String BINDING_NAME = "NhomDuocLyRemoteService";

    List<NhomDuocLy> findAll() throws RemoteException;

    NhomDuocLy findById(String maNhomDuocLy) throws RemoteException;

    String generateNewMaNhomDuocLy() throws RemoteException;

    boolean create(NhomDuocLy nhomDuocLy) throws RemoteException;

    boolean update(NhomDuocLy nhomDuocLy) throws RemoteException;

    boolean deleteById(String maNhomDuocLy) throws RemoteException;

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) throws RemoteException;
}
