package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.databinding.ActivitySanPhamCtBinding;
import fpoly.truongtqph41980.petshop.fragment.frgGioHang;

public class SanPhamCT extends AppCompatActivity {
ActivitySanPhamCtBinding binding;
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
        binding.btnThemCtVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}