package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fpoly.truongtqph41980.petshop.databinding.ActivityManHinhDangKyBinding;
import fpoly.truongtqph41980.petshop.databinding.ActivityManHinhDangNhapBinding;

public class man_hinh_dang_ky extends AppCompatActivity {
ActivityManHinhDangKyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ky);
        binding = ActivityManHinhDangKyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
//    private boolean validateDangKy(){
//        String tenDangNhap = binding.edtTenDangNhapDangKy.getText().toString().trim();
//        String matKhau = binding.edtNhapPassDangKy.getText().toString().trim();
//        String nhapLaiMatKhau = binding.edtNhapLaiPassDangKy.getText().toString().trim();
//        String hoTen = binding.edtNhapHoTenDangKy.getText().toString().trim();
//        String sdt = binding.edtNhapSDTDangKy.getText().toString().trim();
//        String diaChi = binding.edtNhapDiaChiDangKy.getText().toString().trim();
//        String email = binding.edtNhapEmailDangKy.getText().toString().trim();
//
//
//    }
}