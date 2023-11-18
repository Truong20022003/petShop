package fpoly.truongtqph41980.petshop.Model;

public class SanPham {
private int masanpham;
private String tensanpham;
private int gia;
private int maloaisanpham;
private String tenloaisanpham;
private String mota;
    public SanPham() {
    }

    public SanPham(int masanpham, String tensanpham, int gia, int maloaisanpham, String tenloaisanpham,String mota) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.maloaisanpham = maloaisanpham;
        this.tenloaisanpham = tenloaisanpham;
        this.mota=mota;
    }

    public SanPham(int masanpham, String tensanpham, int gia, int maloaisanpham, String mota) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.maloaisanpham = maloaisanpham;
        this.mota = mota;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public SanPham(int masanpham, String tensanpham, int gia, int maloaisanpham) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.maloaisanpham = maloaisanpham;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMaloaisanpham() {
        return maloaisanpham;
    }

    public void setMaloaisanpham(int maloaisanpham) {
        this.maloaisanpham = maloaisanpham;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }
}
