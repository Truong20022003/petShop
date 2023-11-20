package fpoly.truongtqph41980.petshop.Model;

public class GioHang {
    private int maGioHang;
    private int maSanPham;
    private int maNguoiDung;

    private int soLuongMua;
    private String tenSanPham;
    private int giaSanPham;

    public GioHang() {
    }


    public GioHang(int maSanPham, int maNguoiDung, int soLuongMua) {
        this.maSanPham = maSanPham;
        this.maNguoiDung = maNguoiDung;
        this.soLuongMua = soLuongMua;
    }

    public GioHang(int maGioHang, int maSanPham, int maNguoiDung, int soLuongMua, String tenSanPham, int giaSanPham) {
        this.maGioHang = maGioHang;
        this.maSanPham = maSanPham;
        this.maNguoiDung = maNguoiDung;
        this.soLuongMua = soLuongMua;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }
}
