# Jackson Mapper Refactor

## Converted mappers
- pharmacy-server/src/main/java/mapper/ClassMapper.java

## Manual handling kept
- pharmacy-server/src/main/java/mapper/ClassMapper.java
  - Map SDT, GPKD, MSThue because DTO field names differ from entity.
  - Preserve defaults for gioiTinh and trangThai when DTO values are null.
  - Map ngayKetThuc <-> ngayNghiLam and keep username/password/role defaults.
  - Map LuongNhanVienEntity.maNV <-> LuongNhanVienDto.nhanVien.
- pharmacy-client/src/main/java/com/example/pharmacy/client/session/UserContextMapper.java
  - Kept manual because field names and role normalization differ between types.

## Notes
- Generic mapper added at pharmacy-common/src/main/java/com/example/pharmacy/common/mapper/JacksonMapper.java.
- Jackson dependencies added to pharmacy-common/pom.xml with parent version property.
- Removed server mapper classes under pharmacy-server/src/main/java/com/example/pharmacy/server/mapper/.

## Commands
- mvn clean compile
