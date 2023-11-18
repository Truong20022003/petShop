package fpoly.truongtqph41980.petshop.Model;

public class GioHang {
    private String tenSanPham;
//    private SanPham giaSanPham;
    private int soLuongMua;

    public GioHang(String tenSanPham, int soLuongMua) {
        this.tenSanPham = tenSanPham;
        this.soLuongMua = soLuongMua;
    }

    public GioHang() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
