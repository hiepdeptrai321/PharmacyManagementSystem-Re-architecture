param(
    [string]$ProjectRoot = (Get-Location).Path
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$daoDir = Join-Path $ProjectRoot "src/main/java/com/example/pharmacymanagementsystem_qlht/dao"
$javaRoot = Join-Path $ProjectRoot "src/main/java"
$docsDir = Join-Path $ProjectRoot "docs/migration"
$generatedDir = Join-Path $docsDir "_generated"

New-Item -ItemType Directory -Force -Path $docsDir | Out-Null
New-Item -ItemType Directory -Force -Path $generatedDir | Out-Null

function Escape-Cell {
    param([string]$Text)
    if ([string]::IsNullOrWhiteSpace($Text)) {
        return "Cen kiem tra them"
    }
    $value = $Text -replace "\r?\n", "<br>"
    $value = $value -replace "\|", "\|"
    $value = $value -replace "\s{2,}", " "
    return $value.Trim()
}

function Join-Unique {
    param([object[]]$Values, [string]$Separator = "; ")
    if ($null -eq $Values) {
        return ""
    }
    $clean = @()
    foreach ($value in $Values) {
        if ($null -eq $value) {
            continue
        }
        $text = "$value".Trim()
        if ([string]::IsNullOrWhiteSpace($text)) {
            continue
        }
        $clean += $text
    }
    return ($clean | Select-Object -Unique) -join $Separator
}

function Get-QuotedStringContent {
    param([string]$Expression)
    $matches = [regex]::Matches($Expression, '"([^"\\]*(?:\\.[^"\\]*)*)"')
    if ($matches.Count -eq 0) {
        return ($Expression -replace "\s+", " ").Trim()
    }
    $parts = foreach ($match in $matches) {
        $match.Groups[1].Value
    }
    return (($parts -join " ") -replace "\s+", " ").Trim()
}

function Get-PublicMethods {
    param([string]$Content)

    $regex = [regex]'public\s+(?!class)(?:static\s+)?[\w<>\[\], ?\.]+\s+(\w+)\s*\([^)]*\)\s*(?:throws\s+[\w\.,\s]+)?\s*\{'
    $matches = $regex.Matches($Content)
    $methods = @()

    foreach ($match in $matches) {
        $name = $match.Groups[1].Value
        $openBrace = $Content.IndexOf('{', $match.Index + $match.Length - 1)
        if ($openBrace -lt 0) {
            continue
        }

        $depth = 0
        $closeBrace = -1
        for ($index = $openBrace; $index -lt $Content.Length; $index++) {
            $char = $Content[$index]
            if ($char -eq '{') {
                $depth++
            } elseif ($char -eq '}') {
                $depth--
                if ($depth -eq 0) {
                    $closeBrace = $index
                    break
                }
            }
        }

        if ($closeBrace -lt 0) {
            continue
        }

        $signature = $Content.Substring($match.Index, $openBrace - $match.Index).Trim()
        $body = $Content.Substring($openBrace + 1, $closeBrace - $openBrace - 1)

        $methods += [pscustomobject]@{
            Name = $name
            Signature = $signature
            Body = $body
        }
    }

    return $methods
}

function Get-StringAssignments {
    param([string]$Content)

    $regex = [regex]'(?ms)^\s*(?:private|public|protected)\s+(?:final\s+)?String\s+([A-Za-z0-9_]+)\s*=\s*(.*?);'
    $map = @{}
    foreach ($match in $regex.Matches($Content)) {
        $name = $match.Groups[1].Value
        $expr = $match.Groups[2].Value
        $summary = Get-QuotedStringContent -Expression $expr
        if ($summary -match '(?i)\b(SELECT|INSERT|UPDATE|DELETE|JOIN|CALL|EXEC|FROM|WHERE|TOP|OFFSET|GROUP\s+BY|NEXT\s+VALUE\s+FOR)\b') {
            if (-not $map.ContainsKey($name)) {
                $map[$name] = $summary
            }
        }
    }
    return $map
}

function Get-Imports {
    param([string]$Content)
    return @([regex]::Matches($Content, '(?m)^import\s+([^;]+);') | ForEach-Object {
        $_.Groups[1].Value.Trim()
    })
}

function Get-PrimaryModel {
    param(
        [string]$Content,
        [string[]]$ModelImports
    )

    if ($Content -match 'implements\s+DaoInterface<([^>]+)>') {
        return $Matches[1].Trim()
    }

    $signatureModels = @()
    foreach ($import in $ModelImports) {
        $short = $import.Split('.')[-1]
        if ($Content -match "\b$([regex]::Escape($short))\b") {
            $signatureModels += $short
        }
    }

    if ($signatureModels.Count -gt 0) {
        $candidate = "$(($signatureModels | Select-Object -Unique)[0])".Trim()
        if ($candidate.Length -gt 1 -and $candidate -ne "*") {
            return $candidate
        }
    }

    $importShortNames = @($ModelImports | ForEach-Object { $_.Split('.')[-1] } | Where-Object { $_ -ne "*" -and $_.Length -gt 1 } | Select-Object -Unique)
    if ($importShortNames.Count -gt 0) {
        return $importShortNames[0]
    }

    return "Cen kiem tra them"
}

function Get-TablesFromSql {
    param([string]$SqlText)
    if ([string]::IsNullOrWhiteSpace($SqlText)) {
        return @()
    }

    $regex = [regex]'(?i)\b(?:FROM|JOIN|UPDATE|INTO|DELETE\s+FROM|CALL|EXEC(?:UTE)?)\s+([A-Za-z0-9_\.]+)'
    $tables = @()
    foreach ($match in $regex.Matches($SqlText)) {
        $tables += $match.Groups[1].Value.Trim()
    }
    return @($tables | Select-Object -Unique)
}

function Get-SqlServerDependencies {
    param([string]$Text)

    $rules = [ordered]@{
        "TOP" = "TOP -> LIMIT"
        "GETDATE()" = "GETDATE() -> CURRENT_TIMESTAMP"
        "DATEPART()" = "DATEPART() -> EXTRACT()/MONTH()/YEAR()"
        "CONVERT()" = "CONVERT(date, ...) -> DATE(...) hoac CAST(...)"
        "ISNULL()" = "ISNULL() -> COALESCE()"
        "CONTEXT_INFO" = "CONTEXT_INFO -> session/audit context tai service layer"
        "Stored procedure" = "Stored procedure -> service method hoac JPA/native query"
        "CallableStatement" = "CallableStatement -> service orchestration hoac native query"
        "COLLATE" = "COLLATE -> collation/normalization o MariaDB hoac application layer"
        "OFFSET/FETCH" = "OFFSET/FETCH -> LIMIT/OFFSET"
        "SQL Server date math" = "Can doi sang function tuong ung cua MariaDB"
    }

    $found = @()
    if ($Text -match '(?i)\bTOP\s+\d+') { $found += "TOP: $($rules['TOP'])" }
    if ($Text -match '(?i)GETDATE\s*\(') { $found += "GETDATE(): $($rules['GETDATE()'])" }
    if ($Text -match '(?i)DATEPART\s*\(') { $found += "DATEPART(): $($rules['DATEPART()'])" }
    if ($Text -match '(?i)CONVERT\s*\(') { $found += "CONVERT(): $($rules['CONVERT()'])" }
    if ($Text -match '(?i)ISNULL\s*\(') { $found += "ISNULL(): $($rules['ISNULL()'])" }
    if ($Text -match '(?i)CONTEXT_INFO') { $found += "CONTEXT_INFO: $($rules['CONTEXT_INFO'])" }
    if ($Text -match '(?i)\{CALL\s+sp_|\bEXEC(?:UTE)?\s+sp_|\bCALL\s+sp_') { $found += "Stored procedure: $($rules['Stored procedure'])" }
    if ($Text -match '(?i)CallableStatement') { $found += "CallableStatement: $($rules['CallableStatement'])" }
    if ($Text -match '(?i)\bCOLLATE\b') { $found += "COLLATE: $($rules['COLLATE'])" }
    if ($Text -match '(?i)\bOFFSET\b.*\bFETCH\b') { $found += "OFFSET/FETCH: $($rules['OFFSET/FETCH'])" }
    if ($Text -match '(?i)DATEDIFF\s*\(|DATEADD\s*\(|EOMONTH\s*\(|DATEFROMPARTS\s*\(') { $found += "SQL Server date math: $($rules['SQL Server date math'])" }

    return @($found | Select-Object -Unique)
}

function Get-MethodPurpose {
    param(
        [string]$DaoName,
        [string]$MethodName
    )

    switch -Regex ($MethodName) {
        '^selectAll$' { return "Lay toan bo du lieu" }
        '^selectById$' { return "Lay du lieu theo khoa chinh" }
        '^selectBySql$' { return "Thuc thi query tong quat va map model" }
        '^insert' { return "Them moi du lieu" }
        '^update' { return "Cap nhat du lieu" }
        '^delete' { return "Xoa du lieu" }
        '^generate|^getNew|^generateNew' { return "Sinh ma/nghiep vu tao khoa moi" }
        '^findOrCreate' { return "Tim hoac tao du lieu neu chua ton tai" }
        '^authenticate|^authen|^login|^selectByTKVaMK' { return "Dang nhap/xac thuc nguoi dung" }
        '^getThongKe|^getTop|^getHoaDonTheo' { return "Thong ke/bao cao" }
        '^tong|^count' { return "Tinh tong/dem nghiep vu" }
        '^duyet' { return "Duyet nghiep vu/doi trang thai" }
        '^selectByMa|^getChiTiet|^selectActive|^search|^tim|^lay' { return "Tra cuu nghiep vu" }
        default { return "Xu ly nghiep vu trong $DaoName" }
    }
}

function Get-RemoteApiProposal {
    param([string]$DaoName)

    switch -Regex ($DaoName) {
        '^NhanVien_Dao$' { return "AuthRemote.login(); NhanVienRemote.findAll(), findById(), create(), update()" }
        '^LuongNhanVien_Dao$' { return "NhanVienRemote.getSalaryHistory(); NhanVienRemote.saveSalaryConfig()" }
        '^KhachHang_Dao$' { return "KhachHangRemote.findAll(), findById(), search(), save(), update()" }
        '^NhaCungCap_Dao$' { return "NhaCungCapRemote.findAll(), findById(), save(), update()" }
        '^LoaiHang_Dao$' { return "LoaiHangRemote.findAll(), findById(), save(), update()" }
        '^LoaiKhuyenMai_Dao$' { return "KhuyenMaiRemote.getLoaiKhuyenMai(), saveLoaiKhuyenMai()" }
        '^KhuyenMai_Dao$' { return "KhuyenMaiRemote.findAll(), save(), update(), getActiveKhuyenMai()" }
        '^ChiTietKhuyenMai_Dao$' { return "KhuyenMaiRemote.getChiTietKhuyenMai(); KhuyenMaiRemote.saveChiTietKhuyenMai()" }
        '^Thuoc_SanPham_Dao$' { return "ThuocRemote.findAll(), searchThuoc(), save(), update(), getSuggestion()" }
        '^Thuoc_SP_TheoLo_Dao$' { return "TonKhoRemote.findByThuoc(), getSoLuongTon(), reserveStock(), releaseStock()" }
        '^Thuoc_SP_TangKem_Dao$' { return "KhuyenMaiRemote.getThuocTangKem(); KhuyenMaiRemote.saveThuocTangKem()" }
        '^DonViTinh_Dao$' { return "DonViTinhRemote.findAll(), findById(), save(), update()" }
        '^ChiTietDonViTinh_Dao$' { return "ThuocRemote.getDonViTinh(); ThuocRemote.saveDonViTinh()" }
        '^NhomDuocLy_Dao$' { return "NhomDuocLyRemote.findAll(), save(), update()" }
        '^HoatChat_Dao$' { return "HoatChatRemote.findAll(), save(), update()" }
        '^ChiTietHoatChat_Dao$' { return "ThuocRemote.getHoatChat(); ThuocRemote.saveHoatChat()" }
        '^KeHang_Dao$' { return "KeHangRemote.findAll(), save(), update()" }
        '^HoaDon_Dao$' { return "HoaDonRemote.lapHoaDon(), findById(), searchHoaDon()" }
        '^ChiTietHoaDon_Dao$' { return "HoaDonRemote.getChiTietHoaDon(); HoaDonRemote.lapHoaDon()" }
        '^PhieuNhap_Dao$' { return "PhieuNhapRemote.taoPhieuNhap(), findById(), searchPhieuNhap()" }
        '^ChiTietPhieuNhap_Dao$' { return "PhieuNhapRemote.getChiTietPhieuNhap(); PhieuNhapRemote.taoPhieuNhap()" }
        '^PhieuDatHang_Dao$' { return "PhieuDatHangRemote.taoPhieuDat(), duyetPhieuDat(), findById()" }
        '^ChiTietPhieuDatHang_Dao$' { return "PhieuDatHangRemote.getChiTietPhieuDat(); PhieuDatHangRemote.taoPhieuDat()" }
        '^PhieuDoiHang_Dao$' { return "PhieuDoiHangRemote.taoPhieuDoi(), findById(), searchPhieuDoi()" }
        '^ChiTietPhieuDoiHang_Dao$' { return "PhieuDoiHangRemote.getChiTietPhieuDoi(); PhieuDoiHangRemote.taoPhieuDoi()" }
        '^PhieuTraHang_Dao$' { return "PhieuTraHangRemote.taoPhieuTra(), findById(), searchPhieuTra()" }
        '^ChiTietPhieuTraHang_Dao$' { return "PhieuTraHangRemote.getChiTietPhieuTra(); PhieuTraHangRemote.taoPhieuTra()" }
        '^ThongKe_Dao$' { return "ThongKeRemote.getThongKeBanHang(), getHoaDonTheoThoiGian()" }
        '^ThongKeTopSP_Dao$' { return "ThongKeRemote.getTopBanChay(), getTopDoanhThu()" }
        '^ThongKeXNT_Dao$' { return "ThongKeRemote.getThongKeXNT(), getThuocHetHan()" }
        '^HoatDong_Dao$' { return "HoatDongRemote.findAll(); AuditRemote.getHoatDong()" }
        '^CaiDat_Dao$' { return "CaiDatRemote.getSettings(), saveSettings()" }
        default { return "Can thiet ke Remote API theo nghiep vu cua $DaoName" }
    }
}

function Get-ServiceProposal {
    param([string]$DaoName)

    switch -Regex ($DaoName) {
        '^NhanVien_Dao$' { return "AuthService + NhanVienService" }
        '^LuongNhanVien_Dao$' { return "NhanVienService hoac LuongNhanVienService" }
        '^KhachHang_Dao$' { return "KhachHangService" }
        '^NhaCungCap_Dao$' { return "NhaCungCapService" }
        '^LoaiHang_Dao$' { return "LoaiHangService" }
        '^LoaiKhuyenMai_Dao$' { return "KhuyenMaiService hoac LoaiKhuyenMaiService" }
        '^KhuyenMai_Dao$' { return "KhuyenMaiService" }
        '^ChiTietKhuyenMai_Dao$' { return "KhuyenMaiService" }
        '^Thuoc_SanPham_Dao$' { return "ThuocService" }
        '^Thuoc_SP_TheoLo_Dao$' { return "TonKhoService hoac ThuocLoService" }
        '^Thuoc_SP_TangKem_Dao$' { return "KhuyenMaiService" }
        '^DonViTinh_Dao$' { return "DonViTinhService" }
        '^ChiTietDonViTinh_Dao$' { return "ThuocService hoac DonViTinhService" }
        '^NhomDuocLy_Dao$' { return "NhomDuocLyService" }
        '^HoatChat_Dao$' { return "HoatChatService" }
        '^ChiTietHoatChat_Dao$' { return "ThuocService hoac HoatChatService" }
        '^KeHang_Dao$' { return "KeHangService" }
        '^HoaDon_Dao$' { return "HoaDonService" }
        '^ChiTietHoaDon_Dao$' { return "HoaDonService" }
        '^PhieuNhap_Dao$' { return "PhieuNhapService" }
        '^ChiTietPhieuNhap_Dao$' { return "PhieuNhapService" }
        '^PhieuDatHang_Dao$' { return "PhieuDatHangService" }
        '^ChiTietPhieuDatHang_Dao$' { return "PhieuDatHangService" }
        '^PhieuDoiHang_Dao$' { return "PhieuDoiHangService" }
        '^ChiTietPhieuDoiHang_Dao$' { return "PhieuDoiHangService" }
        '^PhieuTraHang_Dao$' { return "PhieuTraHangService" }
        '^ChiTietPhieuTraHang_Dao$' { return "PhieuTraHangService" }
        '^ThongKe_Dao$' { return "ThongKeService hoac ReportService" }
        '^ThongKeTopSP_Dao$' { return "ThongKeService hoac ReportService" }
        '^ThongKeXNT_Dao$' { return "ThongKeService hoac ReportService" }
        '^HoatDong_Dao$' { return "HoatDongService hoac AuditService" }
        '^CaiDat_Dao$' { return "CaiDatService" }
        default { return "Can tach service theo nghiep vu cua $DaoName" }
    }
}

function Get-FunctionDescription {
    param([string]$DaoName)

    switch -Regex ($DaoName) {
        '^CaiDat_Dao$' { return "Cai dat he thong" }
        '^ChiTietDonViTinh_Dao$' { return "Thiet lap don vi tinh, gia ban, quy doi thuoc" }
        '^ChiTietHoaDon_Dao$' { return "Chi tiet hoa don va cap nhat so luong ban" }
        '^ChiTietHoatChat_Dao$' { return "Gan hoat chat cho thuoc" }
        '^ChiTietKhuyenMai_Dao$' { return "Gan thuoc vao chuong trinh khuyen mai" }
        '^ChiTietPhieuDatHang_Dao$' { return "Chi tiet phieu dat hang" }
        '^ChiTietPhieuDoiHang_Dao$' { return "Chi tiet phieu doi hang" }
        '^ChiTietPhieuNhap_Dao$' { return "Chi tiet phieu nhap va lo nhap" }
        '^ChiTietPhieuTraHang_Dao$' { return "Chi tiet phieu tra hang" }
        '^DonViTinh_Dao$' { return "Danh muc don vi tinh" }
        '^HoaDon_Dao$' { return "Lap hoa don va tra cuu hoa don" }
        '^HoatChat_Dao$' { return "Danh muc hoat chat" }
        '^HoatDong_Dao$' { return "Nhat ky hoat dong/audit" }
        '^KeHang_Dao$' { return "Danh muc ke hang/vi tri" }
        '^KhachHang_Dao$' { return "Danh muc, tim kiem va tao khach hang" }
        '^KhuyenMai_Dao$' { return "Danh muc va kich hoat khuyen mai" }
        '^LoaiHang_Dao$' { return "Danh muc loai hang" }
        '^LoaiKhuyenMai_Dao$' { return "Danh muc loai khuyen mai" }
        '^LuongNhanVien_Dao$' { return "Quan ly luong nhan vien" }
        '^NhaCungCap_Dao$' { return "Danh muc nha cung cap" }
        '^NhanVien_Dao$' { return "Dang nhap, tai khoan va danh muc nhan vien" }
        '^NhomDuocLy_Dao$' { return "Danh muc nhom duoc ly" }
        '^PhieuDatHang_Dao$' { return "Lap va duyet phieu dat hang" }
        '^PhieuDoiHang_Dao$' { return "Lap va tim kiem phieu doi hang" }
        '^PhieuNhap_Dao$' { return "Lap va tim kiem phieu nhap hang" }
        '^PhieuTraHang_Dao$' { return "Lap va tim kiem phieu tra hang" }
        '^ThongKe_Dao$' { return "Thong ke ban hang va hoa don theo thoi gian" }
        '^ThongKeTopSP_Dao$' { return "Thong ke top san pham/doanh thu" }
        '^ThongKeXNT_Dao$' { return "Thong ke xuat nhap ton va het han" }
        '^Thuoc_SanPham_Dao$' { return "Danh muc thuoc, tim kiem thuoc, goi y ban hang" }
        '^Thuoc_SP_TangKem_Dao$' { return "Thuoc tang kem trong khuyen mai" }
        '^Thuoc_SP_TheoLo_Dao$' { return "Ton kho theo lo, HSD va giu/dat hang" }
        default { return "Can kiem tra them" }
    }
}

function Get-MigrationPriorityNote {
    param([string]$Risk, [string]$DaoName)

    if ($Risk -eq "Thap") {
        return "Nen chuyen som sau khi POC dang nhap on dinh."
    }
    if ($Risk -eq "Trung binh") {
        return "Nen chuyen sau nhom CRUD, can tach service va DTO can than."
    }
    return "Nen chuyen sau cung hoac tach theo use case nho, can test nghiep vu ky."
}

function Classify-QueryGroup {
    param(
        [string]$DaoName,
        [string]$MethodName,
        [string]$SqlText,
        [string]$Body
    )

    $combined = "$MethodName`n$SqlText`n$Body"
    if ($DaoName -match '^ThongKe' -or $combined -match '(?i)\bSUM\b|\bGROUP\s+BY\b|getThongKe|getTop|getHoaDonTheo') {
        return "Nhom 4 - Thong ke bao cao"
    }

    if ($combined -match '(?i)setAutoCommit|commit\s*\(|rollback\s*\(|CallableStatement|\{CALL\s+sp_|\bEXEC(?:UTE)?\s+sp_|insertAndGetId|duyetPhieu|updateSoLuongTon|tongSoLuongDa|countByHoaDon') {
        return "Nhom 3 - Nghiep vu giao dich"
    }

    if ($SqlText -match '(?i)\bJOIN\b' -or $Body -match '(?i)\bJOIN\b') {
        return "Nhom 2 - Tra cuu co join"
    }

    return "Nhom 1 - CRUD don gian"
}

function Get-RiskLevel {
    param(
        [string]$DaoName,
        [string[]]$MethodGroups,
        [string[]]$SqlServerDependencies,
        [string]$MethodText
    )

    $groupText = $MethodGroups -join " "
    $combined = "$DaoName $groupText $MethodText $($SqlServerDependencies -join ' ')"

    if ($DaoName -match 'HoaDon|PhieuNhap|PhieuDatHang|PhieuDoiHang|PhieuTraHang|ThongKe|Thuoc_SP_TheoLo|HoatDong' -or
        $groupText -match 'Nhom 3|Nhom 4' -or
        $combined -match '(?i)CONTEXT_INFO|insertAndGetId|duyetPhieu|setAutoCommit|commit\(') {
        return "Cao"
    }

    if ($DaoName -match 'Thuoc_SanPham|KhuyenMai|NhanVien|LuongNhanVien|ChiTietDonViTinh|ChiTietKhuyenMai|ChiTietHoatChat|DonViTinh|KhachHang|NhaCungCap|LoaiHang|LoaiKhuyenMai|NhomDuocLy|KeHang|Thuoc_SP_TangKem' -or
        $groupText -match 'Nhom 2' -or
        $SqlServerDependencies.Count -gt 0 -or
        $MethodText -match '(?i)TOP\s+1|generate(New|key)|NEXT\s+VALUE\s+FOR') {
        return "Trung binh"
    }

    return "Thap"
}

function Get-CallerInfo {
    param(
        [string]$JavaRootPath,
        [string]$DaoFilePath,
        [string]$ClassName
    )

    $files = Get-ChildItem $JavaRootPath -Recurse -Filter *.java | Where-Object { $_.FullName -ne $DaoFilePath }
    $pattern = "new\s+$([regex]::Escape($ClassName))\s*\(|\b$([regex]::Escape($ClassName))\b"
    $results = @()

    foreach ($file in $files) {
        $content = Get-Content $file.FullName -Raw
        if ($content -match $pattern) {
            $isController = $file.FullName -like "*\controller\*"
            $callerName = $file.BaseName
            $callerType = if ($isController) { "Controller" } elseif ($file.FullName -like "*\dao\*") { "DAO" } else { "Class" }
            $results += [pscustomobject]@{
                Path = $file.FullName
                Name = $callerName
                Type = $callerType
            }
        }
    }

    return @($results | Sort-Object Type, Name -Unique)
}

function Get-MethodSqlSummary {
    param(
        [hashtable]$StringAssignments,
        [string]$MethodBody
    )

    $segments = @()
    foreach ($key in $StringAssignments.Keys) {
        if ($MethodBody -match "\b$([regex]::Escape($key))\b") {
            $segments += $StringAssignments[$key]
        }
    }

    $inline = [regex]::Matches($MethodBody, '(?s)String\s+[A-Za-z0-9_]+\s*=\s*(.*?);')
    foreach ($match in $inline) {
        $summary = Get-QuotedStringContent -Expression $match.Groups[1].Value
        if ($summary -match '(?i)\b(SELECT|INSERT|UPDATE|DELETE|JOIN|CALL|EXEC|FROM|WHERE|TOP|OFFSET|GROUP\s+BY|NEXT\s+VALUE\s+FOR)\b') {
            $segments += $summary
        }
    }

    if ($MethodBody -match '(?s)ConnectDB\.(?:query|update)\s*\(\s*"(.*?)"') {
        $segments += ($Matches[1] -replace "\s+", " ").Trim()
    }

    if ($MethodBody -match '(?s)\{CALL\s+[^"]+\}') {
        $segments += ([regex]::Matches($MethodBody, '\{CALL[^}]+\}') | ForEach-Object { $_.Value })
    }

    $summary = Join-Unique -Values $segments -Separator " || "
    if ([string]::IsNullOrWhiteSpace($summary)) {
        return "Khong co SQL truc tiep hoac dung query thong qua method khac."
    }
    return $summary
}

function Get-DominantGroups {
    param([string[]]$Groups)
    if ($Groups.Count -eq 0) {
        return "Can kiem tra them"
    }

    $counts = $Groups | Group-Object | Sort-Object Count -Descending
    $top = $counts | Select-Object -First 2
    return (($top | ForEach-Object { "$($_.Name) ($($_.Count))" }) -join "; ")
}

function Get-ControllerSummary {
    param([object[]]$Callers)
    $controllers = @($Callers | Where-Object { $_.Type -eq "Controller" } | ForEach-Object { $_.Name })
    if ($controllers.Count -eq 0) {
        return "Khong tim thay controller goi truc tiep"
    }
    return Join-Unique -Values $controllers
}

function Get-AdditionalCallerSummary {
    param([object[]]$Callers)
    $others = @($Callers | Where-Object { $_.Type -ne "Controller" } | ForEach-Object { "$($_.Type): $($_.Name)" })
    if ($others.Count -eq 0) {
        return ""
    }
    return "Caller khac: $(Join-Unique -Values $others)"
}

$daoFiles = Get-ChildItem $daoDir -Filter *.java | Where-Object { $_.Name -ne "DaoInterface.java" } | Sort-Object Name
$allDaoData = @()
$allQueryData = @()

foreach ($daoFile in $daoFiles) {
    $content = Get-Content $daoFile.FullName -Raw
    $package = ([regex]::Match($content, '(?m)^package\s+([^;]+);')).Groups[1].Value.Trim()
    $imports = @(Get-Imports -Content $content)
    $modelImports = @($imports | Where-Object { $_ -like "com.example.pharmacymanagementsystem_qlht.model.*" })
    $primaryModel = Get-PrimaryModel -Content $content -ModelImports $modelImports
    $relatedModels = @($primaryModel) + ($modelImports | ForEach-Object { $_.Split('.')[-1] } | Where-Object { $_ -ne "*" -and $_.Length -gt 1 })
    $stringAssignments = Get-StringAssignments -Content $content
    $methods = @(Get-PublicMethods -Content $content)
    $callers = @(Get-CallerInfo -JavaRootPath $javaRoot -DaoFilePath $daoFile.FullName -ClassName $daoFile.BaseName)

    $methodGroups = @()
    $daoTables = @()
    $daoSqlServerDeps = @()

    foreach ($method in $methods) {
        $sqlSummary = Get-MethodSqlSummary -StringAssignments $stringAssignments -MethodBody $method.Body
        $tables = @(Get-TablesFromSql -SqlText $sqlSummary)
        $sqlServerDeps = @(Get-SqlServerDependencies -Text "$sqlSummary`n$($method.Body)")
        $queryGroup = Classify-QueryGroup -DaoName $daoFile.BaseName -MethodName $method.Name -SqlText $sqlSummary -Body $method.Body
        $methodGroups += $queryGroup
        $daoTables += $tables
        $daoSqlServerDeps += $sqlServerDeps

        $queryRisk = if ($queryGroup -match 'Nhom 4|Nhom 3') { "Cao" } elseif ($queryGroup -match 'Nhom 2' -or $sqlServerDeps.Count -gt 0) { "Trung binh" } else { "Thap" }

        $notes = @()
        if ($sqlServerDeps.Count -gt 0) {
            $notes += "SQL Server-specific: $(Join-Unique -Values $sqlServerDeps)"
        }
        if ($primaryModel -match 'Display|CTPN_|ThongKe') {
            $notes += "Model tong hop/view, nen doi sang DTO/projection thay vi JPA entity truc tiep."
        }
        if ($method.Name -match 'generate|getNew|insertAndGetId') {
            $notes += "Co logic sinh ma/lay id moi, can xem xet lai khi len server va MariaDB."
        }
        if ($queryGroup -match 'Nhom 3') {
            $notes += "Can dat transaction o server service."
        }

        $allQueryData += [pscustomobject]@{
            DAO = $daoFile.BaseName
            Method = $method.Name
            Purpose = Get-MethodPurpose -DaoName $daoFile.BaseName -MethodName $method.Name
            SqlSummary = $sqlSummary
            QueryGroup = $queryGroup
            SqlServerSpecific = if ($sqlServerDeps.Count -gt 0) { "Co - $(Join-Unique -Values $sqlServerDeps)" } else { "Khong thay ro" }
            Risk = $queryRisk
            Notes = Join-Unique -Values $notes
        }
    }

    $daoRisk = Get-RiskLevel -DaoName $daoFile.BaseName -MethodGroups $methodGroups -SqlServerDependencies $daoSqlServerDeps -MethodText $content
    $daoNotes = @()
    $daoNotes += Get-MigrationPriorityNote -Risk $daoRisk -DaoName $daoFile.BaseName
    if ($daoSqlServerDeps.Count -gt 0) {
        $daoNotes += "Phu thuoc SQL Server: $(Join-Unique -Values $daoSqlServerDeps)"
    }
    if ($primaryModel -match 'Display|CTPN_|ThongKe') {
        $daoNotes += "Model tong hop/view, khong nen map 1-1 thanh JPA entity."
    }
    $otherCallerInfo = Get-AdditionalCallerSummary -Callers $callers
    if (-not [string]::IsNullOrWhiteSpace($otherCallerInfo)) {
        $daoNotes += $otherCallerInfo
    }
    if ((@($callers | Where-Object { $_.Type -eq "Controller" })).Count -gt 0) {
        $daoNotes += "Controller dang goi DAO truc tiep, can doi sang Client Service -> RMI."
    } else {
        $daoNotes += "Can kiem tra them caller controller neu nghiep vu khong di qua controller ten ro rang."
    }

    $allDaoData += [pscustomobject]@{
        DAO = $daoFile.BaseName
        Package = $package
        Models = Join-Unique -Values ($relatedModels | Select-Object -Unique)
        Controllers = Get-ControllerSummary -Callers $callers
        Function = Get-FunctionDescription -DaoName $daoFile.BaseName
        ServiceProposal = Get-ServiceProposal -DaoName $daoFile.BaseName
        RemoteProposal = Get-RemoteApiProposal -DaoName $daoFile.BaseName
        QueryGroups = Get-DominantGroups -Groups $methodGroups
        Risk = $daoRisk
        Notes = Join-Unique -Values $daoNotes
        Methods = Join-Unique -Values ($methods | ForEach-Object { $_.Name })
        Tables = Join-Unique -Values ($daoTables | Select-Object -Unique)
        SqlServerDeps = Join-Unique -Values ($daoSqlServerDeps | Select-Object -Unique)
    }
}

$jsonDaoPath = Join-Path $generatedDir "dao_inventory.json"
$jsonQueryPath = Join-Path $generatedDir "query_inventory.json"
$allDaoData | ConvertTo-Json -Depth 6 | Set-Content -Path $jsonDaoPath -Encoding UTF8
$allQueryData | ConvertTo-Json -Depth 6 | Set-Content -Path $jsonQueryPath -Encoding UTF8

$daoMappingPath = Join-Path $docsDir "DAO_MIGRATION_MAPPING.md"
$queryInventoryPath = Join-Path $docsDir "QUERY_INVENTORY.md"
$priorityPath = Join-Path $docsDir "MIGRATION_PRIORITY.md"
$checklistPath = Join-Path $docsDir "STEP_2_CHECKLIST.md"

$daoLines = @()
$daoLines += "# DAO Migration Mapping"
$daoLines += ""
$daoLines += "Tong so DAO duoc kiem ke: **$($allDaoData.Count) concrete DAO + 1 DaoInterface dung chung**"
$daoLines += ""
$daoLines += "Luu y:"
$daoLines += "- `DaoInterface.java` la hop dong CRUD dung chung, khong phai concrete DAO nen khong dua vao bang mapping ben duoi."
$daoLines += "- File nay la ban do chuyen doi tu DAO hien tai sang Service/Remote API o kien truc client-server."
$daoLines += "- Du lieu duoc tong hop tu code hien tai, cac muc 'Cen kiem tra them' can duoc xac minh khi refactor tung module."
$daoLines += ""
$daoLines += "| STT | DAO hien tai | Package | Model/Entity lien quan | Controller/Man hinh dang dung | Chuc nang | Service moi de xuat | Remote API de xuat | Nhom query chinh | Rui ro | Ghi chu |"
$daoLines += "| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |"

$stt = 1
foreach ($dao in $allDaoData) {
    $daoLines += "| $stt | $(Escape-Cell $dao.DAO) | $(Escape-Cell $dao.Package) | $(Escape-Cell $dao.Models) | $(Escape-Cell $dao.Controllers) | $(Escape-Cell $dao.Function) | $(Escape-Cell $dao.ServiceProposal) | $(Escape-Cell $dao.RemoteProposal) | $(Escape-Cell $dao.QueryGroups) | $(Escape-Cell $dao.Risk) | $(Escape-Cell $dao.Notes) |"
    $stt++
}
$daoLines | Set-Content -Path $daoMappingPath -Encoding UTF8

$queryLines = @()
$queryLines += "# Query Inventory"
$queryLines += ""
$queryLines += "Tong so method public duoc kiem ke: **$($allQueryData.Count)**"
$queryLines += ""
$queryLines += "Quy uoc nhom query:"
$queryLines += "- Nhom 1: CRUD don gian"
$queryLines += "- Nhom 2: Tra cuu co join"
$queryLines += "- Nhom 3: Nghiep vu giao dich"
$queryLines += "- Nhom 4: Thong ke bao cao"
$queryLines += ""
$queryLines += "| STT | DAO | Method | Muc dich | SQL tom tat | Nhom query | Co phu thuoc SQL Server khong? | Muc rui ro | Ghi chu chuyen doi |"
$queryLines += "| --- | --- | --- | --- | --- | --- | --- | --- | --- |"

$stt = 1
foreach ($query in $allQueryData) {
    $queryLines += "| $stt | $(Escape-Cell $query.DAO) | $(Escape-Cell $query.Method) | $(Escape-Cell $query.Purpose) | $(Escape-Cell $query.SqlSummary) | $(Escape-Cell $query.QueryGroup) | $(Escape-Cell $query.SqlServerSpecific) | $(Escape-Cell $query.Risk) | $(Escape-Cell $query.Notes) |"
    $stt++
}
$queryLines | Set-Content -Path $queryInventoryPath -Encoding UTF8

$lowRisk = $allDaoData | Where-Object { $_.Risk -eq "Thap" } | Select-Object -ExpandProperty DAO
$mediumRisk = $allDaoData | Where-Object { $_.Risk -eq "Trung binh" } | Select-Object -ExpandProperty DAO
$highRisk = $allDaoData | Where-Object { $_.Risk -eq "Cao" } | Select-Object -ExpandProperty DAO
$sqlServerHeavy = $allDaoData | Where-Object { -not [string]::IsNullOrWhiteSpace($_.SqlServerDeps) } | Sort-Object DAO

$priorityLines = @()
$priorityLines += "# Migration Priority"
$priorityLines += ""
$priorityLines += "Tai lieu nay tong hop thu tu uu tien chuyen doi dua tren inventory DAO va query hien tai."
$priorityLines += ""
$priorityLines += "## 1. Nhom nen chuyen truoc"
$priorityLines += ""
$priorityLines += "Day la nhom CRUD, it transaction, it query SQL Server-specific, phu hop de chuyen doi som sau POC dang nhap."
$priorityLines += ""
foreach ($item in $lowRisk) {
    $priorityLines += "- $item"
}
$priorityLines += ""
$priorityLines += "## 2. Nhom chuyen sau POC dang nhap"
$priorityLines += ""
$priorityLines += "Day la nhom trung binh, co join, co tim kiem nhieu dieu kien hoac lien quan nhieu man hinh."
$priorityLines += ""
foreach ($item in $mediumRisk) {
    $priorityLines += "- $item"
}
$priorityLines += ""
$priorityLines += "## 3. Nhom rui ro cao, nen lam sau cung hoac tach rat can than"
$priorityLines += ""
$priorityLines += "Day la nhom nghiep vu giao dich, thong ke, ton kho, audit, stored procedure hoac sinh ma."
$priorityLines += ""
foreach ($item in $highRisk) {
    $priorityLines += "- $item"
}
$priorityLines += ""
$priorityLines += "## 4. De xuat thu tu chuyen doi tong the"
$priorityLines += ""
$priorityLines += "1. Auth/Dang nhap (NhanVien_Dao truoc, tach AuthService)."
$priorityLines += "2. Danh muc CRUD don gian: KhachHang_Dao, NhaCungCap_Dao, LoaiHang_Dao, DonViTinh_Dao, NhomDuocLy_Dao, HoatChat_Dao, KeHang_Dao, CaiDat_Dao."
$priorityLines += "3. Nhan vien va cau hinh lien quan: LuongNhanVien_Dao, mot phan NhanVien_Dao sau khi login on dinh."
$priorityLines += "4. Khuyen mai va cau hinh danh muc phuc hop: KhuyenMai_Dao, LoaiKhuyenMai_Dao, ChiTietKhuyenMai_Dao, Thuoc_SP_TangKem_Dao."
$priorityLines += "5. Thuoc va cau truc danh muc phuc hop: Thuoc_SanPham_Dao, ChiTietDonViTinh_Dao, ChiTietHoatChat_Dao."
$priorityLines += "6. Ton kho theo lo va han su dung: Thuoc_SP_TheoLo_Dao."
$priorityLines += "7. Phieu nhap: PhieuNhap_Dao, ChiTietPhieuNhap_Dao."
$priorityLines += "8. Hoa don: HoaDon_Dao, ChiTietHoaDon_Dao."
$priorityLines += "9. Doi/tra/dat hang: PhieuDatHang_Dao, ChiTietPhieuDatHang_Dao, PhieuDoiHang_Dao, ChiTietPhieuDoiHang_Dao, PhieuTraHang_Dao, ChiTietPhieuTraHang_Dao."
$priorityLines += "10. Audit/log hoat dong: HoatDong_Dao."
$priorityLines += "11. Thong ke/bao cao: ThongKe_Dao, ThongKeTopSP_Dao, ThongKeXNT_Dao."
$priorityLines += ""
$priorityLines += "## 5. DAO/query phu thuoc SQL Server nhieu"
$priorityLines += ""
foreach ($item in $sqlServerHeavy) {
    $priorityLines += "- $($item.DAO): $(Escape-Cell $item.SqlServerDeps)"
}
$priorityLines += ""
$priorityLines += "## 6. Nhan xet tong quan"
$priorityLines += ""
$priorityLines += "- Hien tai controller JavaFX dang goi DAO truc tiep tren nhieu man hinh."
$priorityLines += "- `ConnectDB` van la diem truy cap DB trung tam, va co phu thuoc session dang nhap o UI."
$priorityLines += "- Cac DAO lien quan hoa don, nhap hang, ton kho theo lo va thong ke co muc rui ro cao nhat."
$priorityLines += "- Cac query dung `TOP`, `GETDATE()`, `DATEPART()`, `CONVERT()`, `ISNULL()`, `COLLATE`, stored procedure va `CONTEXT_INFO` se can xu ly rieng khi sang MariaDB/JPA."
$priorityLines | Set-Content -Path $priorityPath -Encoding UTF8

$checklistLines = @()
$checklistLines += "# Step 2 Checklist"
$checklistLines += ""
$checklistLines += "- [x] Da liet ke du tat ca DAO."
$checklistLines += "- [x] Da xac dinh controller/man hinh dang goi tung DAO o muc code usage hien tai."
$checklistLines += "- [x] Da gan DAO voi model/entity lien quan."
$checklistLines += "- [x] Da de xuat service thay the."
$checklistLines += "- [x] Da de xuat remote API tuong ung."
$checklistLines += "- [x] Da phan loai query thanh CRUD, join, giao dich, thong ke."
$checklistLines += "- [x] Da danh dau query phu thuoc SQL Server."
$checklistLines += "- [x] Da danh dau rui ro thap/trung binh/cao."
$checklistLines += "- [x] Da xac dinh module nen chuyen truoc."
$checklistLines += "- [x] Da xac dinh module rui ro cao can chuyen sau."
$checklistLines += "- [x] Da ghi ro cac cho can kiem tra them."
$checklistLines | Set-Content -Path $checklistPath -Encoding UTF8

Write-Host "DAO inventory generated:"
Write-Host " - $daoMappingPath"
Write-Host " - $queryInventoryPath"
Write-Host " - $priorityPath"
Write-Host " - $checklistPath"
Write-Host "Raw JSON:"
Write-Host " - $jsonDaoPath"
Write-Host " - $jsonQueryPath"
