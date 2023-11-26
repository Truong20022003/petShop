package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.databinding.ActivitySanPhamCtBinding;
import fpoly.truongtqph41980.petshop.fragment.frgGioHang;

public class SanPhamCT extends AppCompatActivity {
    ActivitySanPhamCtBinding binding;
    private SharedViewModel sharedViewModel;
    private GioHangDao gioHangDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySanPhamCtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            }
        }
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        gioHangDao = new GioHangDao(this);
        binding.btnThemCtVaoGio.setOnClickListener(view -> {
            SanPham selectedSanPham = intent.getParcelableExtra("sanPham");
            if (selectedSanPham != null) {
                int maSanPham = selectedSanPham.getMasanpham();

                // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                if (!sharedViewModel.isProductInCart(maSanPham)) {
                    sharedViewModel.setMasp(maSanPham);
                    sharedViewModel.setAddToCartClicked(true);
                    sharedViewModel.addProductToCart(maSanPham);
                    sharedViewModel.setQuantityToAdd(1);

                    int mand = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE).getInt("mataikhoan", 0);
                    gioHangDao.insertGioHang(new GioHang(maSanPham, mand, 1));
                } else {
                    // Sản phẩm đã tồn tại trong giỏ hàng, thực hiện cập nhật số lượng
                    int mand = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE).getInt("mataikhoan", 0);
                    GioHang gioHang = gioHangDao.getGioHangByMasp(maSanPham, mand);
                    if (gioHang != null) {
                        gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                        gioHangDao.updateGioHang(gioHang);
                    } else {
                        GioHang newCartItem = new GioHang(maSanPham, mand, 1);
                        gioHangDao.insertGioHang(newCartItem);
                    }
                }

                Toast.makeText(SanPhamCT.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

}