package fpoly.truongtqph41980.petshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DanhGiaDao;
import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.DanhGia;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_danh_gia;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.ActivitySanPhamCtBinding;
import fpoly.truongtqph41980.petshop.fragment.frgGianHang;

public class SanPhamCT extends AppCompatActivity implements ViewModelStoreOwner {
    ActivitySanPhamCtBinding binding;
    private SharedViewModel sharedViewModel;
    private GioHangDao gioHangDao;
    private DanhGiaDao danhGiaDao;
    private adapter_danh_gia adapterDanhGia;
    private ArrayList<DanhGia> list = new ArrayList<>();

    private ArrayList<SanPham> listSanPham = new ArrayList<>();
    private SanPhamDao sanPhamDao;
//    private adapter_gio_hang gioHangAdapter;
//    frgGianHang frgGianHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySanPhamCtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SanPhamCT.this, MainActivity.class));
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            SanPham selectedSanPham = intent.getParcelableExtra("sanPham");
            if (selectedSanPham != null) {
                binding.txtMaSp.setText("Mã sản phẩm: " + selectedSanPham.getMasanpham());
                binding.txttensp.setText("Tên sản phẩm: " + selectedSanPham.getTensanpham());
                binding.txtSoluotban.setText("Số lượt bán: " + "200"); // Thay "200" bằng thông tin thực tế
                binding.txtGiaSp.setText("Giá: " + selectedSanPham.getGia());
                binding.txtLoaisp.setText("Loại sản phẩm: " + selectedSanPham.getTenloaisanpham());
                binding.txtMotaChiTiet.setText("Mô tả: " + selectedSanPham.getMota());
                danhGiaDao = new DanhGiaDao(SanPhamCT.this);
                list = danhGiaDao.getDanhGiaByMaSanPham(selectedSanPham.getMasanpham());
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                binding.rcvDanhGia.setLayoutManager(layoutManager);
                adapterDanhGia = new adapter_danh_gia(list, this);
                binding.rcvDanhGia.setAdapter(adapterDanhGia);
                if (selectedSanPham.getSoluong() == 0) {
                    binding.btnThemCtVaoGio.setVisibility(View.GONE);
                    binding.txtHetHang.setVisibility(View.VISIBLE);
                } else {
                    binding.btnThemCtVaoGio.setVisibility(View.VISIBLE);
                    binding.txtHetHang.setVisibility(View.GONE);
                }
            }
        }
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

//        sharedViewModel = new ViewModelProvider().get(SharedViewModel.class);
        gioHangDao = new GioHangDao(this);
        sanPhamDao = new SanPhamDao(this);
        listSanPham = sanPhamDao.getsanphamall();

        binding.btnThemCtVaoGio.setOnClickListener(view -> {
            SanPham selectedSanPham = intent.getParcelableExtra("sanPham");
            if (selectedSanPham != null) {
                addToCart(selectedSanPham);
            }
        });


    }

    private void addToCart(SanPham sanPham) {
        int maSanPham = sanPham.getMasanpham();
        int slSanPham = getSoLuongSp(maSanPham);
        int mand = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE).getInt("mataikhoan", 0);
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        if (!sharedViewModel.isProductInCart(maSanPham)) {
            if (slSanPham > 0) {
                // Nếu số lượng sản phẩm > 0, thêm sản phẩm vào giỏ hàng với số lượng là 1
                sharedViewModel.setMasp(maSanPham);
                sharedViewModel.setAddToCartClicked(true);
                sharedViewModel.addProductToCart(maSanPham);
                sharedViewModel.setQuantityToAdd(1);
                gioHangDao.insertGioHang(new GioHang(maSanPham, mand, 1));
                Snackbar.make(getWindow().getDecorView().getRootView(), "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show();
            } else {
                // Nếu số lượng sản phẩm <= 0, thông báo người dùng
                Toast.makeText(this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nếu sản phẩm đã có trong giỏ hàng
            GioHang gioHang = gioHangDao.getGioHangByMasp(maSanPham, mand);
            if (gioHang != null) {
                if (slSanPham > 0 && gioHang.getSoLuongMua() < slSanPham) {
                    // Nếu có số lượng sản phẩm > 0 và số lượng trong giỏ hàng chưa đạt giới hạn
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    gioHangDao.updateGioHang(gioHang);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Nếu số lượng trong giỏ hàng đã đạt giới hạn hoặc số lượng sản phẩm <= 0, thông báo người dùng
                    Toast.makeText(this, "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getSoLuongSp(int maSanPham) {
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getMasanpham() == maSanPham) {
                return sanPham.getSoluong();
            }
        }
        return 0;
    }


}