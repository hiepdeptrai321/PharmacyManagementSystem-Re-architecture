package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.NhomDuocLyRemote;
import com.example.pharmacy.server.service.NhomDuocLyService;
import com.example.pharmacy.common.model.NhomDuocLyDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class NhomDuocLyRemoteAdapter extends UnicastRemoteObject implements NhomDuocLyRemote {
    private final NhomDuocLyService nhomDuocLyService;

    public NhomDuocLyRemoteAdapter(NhomDuocLyService nhomDuocLyService) throws RemoteException {
        super();
        this.nhomDuocLyService = Objects.requireNonNull(nhomDuocLyService, "nhomDuocLyService must not be null");
    }

    @Override
    public List<NhomDuocLyDto> findAll() throws RemoteException {
        return nhomDuocLyService.findAll();
    }

    @Override
    public NhomDuocLyDto findById(String maNhomDuocLy) throws RemoteException {
        return nhomDuocLyService.findById(maNhomDuocLy);
    }

    @Override
    public String generateNewMaNhomDuocLy() throws RemoteException {
        return nhomDuocLyService.generateNewMaNhomDuocLy();
    }

    @Override
    public boolean create(NhomDuocLyDto nhomDuocLy) throws RemoteException {
        return nhomDuocLyService.create(nhomDuocLy);
    }

    @Override
    public boolean update(NhomDuocLyDto nhomDuocLy) throws RemoteException {
        return nhomDuocLyService.update(nhomDuocLy);
    }

    @Override
    public boolean deleteById(String maNhomDuocLy) throws RemoteException {
        return nhomDuocLyService.deleteById(maNhomDuocLy);
    }

    @Override
    public List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) throws RemoteException {
        return nhomDuocLyService.findThuocNamesByNhomDuocLy(maNhomDuocLy);
    }
}
