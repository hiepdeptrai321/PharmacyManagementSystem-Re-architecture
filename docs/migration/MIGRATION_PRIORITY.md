# Migration Priority

Tai lieu nay tong hop thu tu uu tien chuyen doi dua tren inventory DAO va query hien tai.

## 1. Nhom nen chuyen truoc

Day la nhom CRUD, it transaction, it query SQL Server-specific, phu hop de chuyen doi som sau POC dang nhap.

- CaiDat_Dao
- HoatChat_Dao

## 2. Nhom chuyen sau POC dang nhap

Day la nhom trung binh, co join, co tim kiem nhieu dieu kien hoac lien quan nhieu man hinh.

- ChiTietDonViTinh_Dao
- ChiTietHoatChat_Dao
- ChiTietKhuyenMai_Dao
- DonViTinh_Dao
- KeHang_Dao
- KhachHang_Dao
- KhuyenMai_Dao
- LoaiHang_Dao
- LoaiKhuyenMai_Dao
- LuongNhanVien_Dao
- NhaCungCap_Dao
- NhomDuocLy_Dao
- Thuoc_SP_TangKem_Dao

## 3. Nhom rui ro cao, nen lam sau cung hoac tach rat can than

Day la nhom nghiep vu giao dich, thong ke, ton kho, audit, stored procedure hoac sinh ma.

- ChiTietHoaDon_Dao
- ChiTietPhieuDatHang_Dao
- ChiTietPhieuDoiHang_Dao
- ChiTietPhieuNhap_Dao
- ChiTietPhieuTraHang_Dao
- HoaDon_Dao
- HoatDong_Dao
- NhanVien_Dao
- PhieuDatHang_Dao
- PhieuDoiHang_Dao
- PhieuNhap_Dao
- PhieuTraHang_Dao
- ThongKe_Dao
- ThongKeTopSP_Dao
- ThongKeXNT_Dao
- Thuoc_SanPham_Dao
- Thuoc_SP_TheoLo_Dao

## 4. De xuat thu tu chuyen doi tong the

1. Auth/Dang nhap (NhanVien_Dao truoc, tach AuthService).
2. Danh muc CRUD don gian: KhachHang_Dao, NhaCungCap_Dao, LoaiHang_Dao, DonViTinh_Dao, NhomDuocLy_Dao, HoatChat_Dao, KeHang_Dao, CaiDat_Dao.
3. Nhan vien va cau hinh lien quan: LuongNhanVien_Dao, mot phan NhanVien_Dao sau khi login on dinh.
4. Khuyen mai va cau hinh danh muc phuc hop: KhuyenMai_Dao, LoaiKhuyenMai_Dao, ChiTietKhuyenMai_Dao, Thuoc_SP_TangKem_Dao.
5. Thuoc va cau truc danh muc phuc hop: Thuoc_SanPham_Dao, ChiTietDonViTinh_Dao, ChiTietHoatChat_Dao.
6. Ton kho theo lo va han su dung: Thuoc_SP_TheoLo_Dao.
7. Phieu nhap: PhieuNhap_Dao, ChiTietPhieuNhap_Dao.
8. Hoa don: HoaDon_Dao, ChiTietHoaDon_Dao.
9. Doi/tra/dat hang: PhieuDatHang_Dao, ChiTietPhieuDatHang_Dao, PhieuDoiHang_Dao, ChiTietPhieuDoiHang_Dao, PhieuTraHang_Dao, ChiTietPhieuTraHang_Dao.
10. Audit/log hoat dong: HoatDong_Dao.
11. Thong ke/bao cao: ThongKe_Dao, ThongKeTopSP_Dao, ThongKeXNT_Dao.

## 5. DAO/query phu thuoc SQL Server nhieu

- ChiTietPhieuDoiHang_Dao: ISNULL(): ISNULL() -> COALESCE()
- ChiTietPhieuNhap_Dao: TOP: TOP -> LIMIT
- DonViTinh_Dao: TOP: TOP -> LIMIT
- HoaDon_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; TOP: TOP -> LIMIT
- KeHang_Dao: TOP: TOP -> LIMIT
- KhachHang_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; SQL Server date math: Can doi sang function tuong ung cua MariaDB; TOP: TOP -> LIMIT; CONVERT(): CONVERT(date, ...) -> DATE(...) hoac CAST(...)
- KhuyenMai_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; TOP: TOP -> LIMIT
- LoaiHang_Dao: TOP: TOP -> LIMIT
- LuongNhanVien_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; TOP: TOP -> LIMIT
- NhaCungCap_Dao: TOP: TOP -> LIMIT
- NhanVien_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; Stored procedure: Stored procedure -> service method hoac JPA/native query; CallableStatement: CallableStatement -> service orchestration hoac native query
- NhomDuocLy_Dao: TOP: TOP -> LIMIT
- PhieuDatHang_Dao: TOP: TOP -> LIMIT; Stored procedure: Stored procedure -> service method hoac JPA/native query; CallableStatement: CallableStatement -> service orchestration hoac native query
- PhieuDoiHang_Dao: TOP: TOP -> LIMIT
- PhieuNhap_Dao: TOP: TOP -> LIMIT; Stored procedure: Stored procedure -> service method hoac JPA/native query
- PhieuTraHang_Dao: TOP: TOP -> LIMIT
- ThongKe_Dao: Stored procedure: Stored procedure -> service method hoac JPA/native query; GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; CallableStatement: CallableStatement -> service orchestration hoac native query
- ThongKeTopSP_Dao: ISNULL(): ISNULL() -> COALESCE()
- ThongKeXNT_Dao: Stored procedure: Stored procedure -> service method hoac JPA/native query; GETDATE(): GETDATE() -> CURRENT_TIMESTAMP
- Thuoc_SanPham_Dao: COLLATE: COLLATE -> collation/normalization o MariaDB hoac application layer; OFFSET/FETCH: OFFSET/FETCH -> LIMIT/OFFSET; TOP: TOP -> LIMIT; Stored procedure: Stored procedure -> service method hoac JPA/native query
- Thuoc_SP_TheoLo_Dao: GETDATE(): GETDATE() -> CURRENT_TIMESTAMP; TOP: TOP -> LIMIT; Stored procedure: Stored procedure -> service method hoac JPA/native query

## 6. Nhan xet tong quan

- Hien tai controller JavaFX dang goi DAO truc tiep tren nhieu man hinh.
- ConnectDB van la diem truy cap DB trung tam, va co phu thuoc session dang nhap o UI.
- Cac DAO lien quan hoa don, nhap hang, ton kho theo lo va thong ke co muc rui ro cao nhat.
- Cac query dung TOP, GETDATE(), DATEPART(), CONVERT(), ISNULL(), COLLATE, stored procedure va CONTEXT_INFO se can xu ly rieng khi sang MariaDB/JPA.
