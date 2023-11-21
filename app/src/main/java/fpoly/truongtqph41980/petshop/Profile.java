package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import fpoly.truongtqph41980.petshop.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = preferences.getInt("mataikhoan", 0);
        String tenDN = preferences.getString("tendangnhap", "");
        String matkhau = preferences.getString("matkhau", "");
        String hoten = preferences.getString("hoten", "");
        String email = preferences.getString("email", "");
        String sodienthoai = preferences.getString("sodienthoai", "");
        String diachi = preferences.getString("diachi", "");
        int tien = preferences.getInt("sotien", 0);
        String loaitaikhoan = preferences.getString("loaitaikhoan", "");
        binding.hiName.setText("Hi, "+hoten);
        binding.txtPMaNguoiDung.setText("Mã tài khoản: " + String.valueOf(mand));
        binding.txtPTenDangNhap.setText("Tên đăng nhập: " + tenDN);
        binding.txtPHoTen.setText("Họ tên: " + hoten);
        binding.txtPEmail.setText("Email: " + email);
        binding.txtPSoDienThoai.setText("Số điện thoại: " + sodienthoai);
        binding.txtPDiaChi.setText("Địa chỉ: " + diachi);
        binding.txtPSoTien.setText("Số tiền hiện có: " + String.valueOf(tien));
        binding.txtPLoaiTaiKhoan.setText("Loại tài khoản: " + loaitaikhoan);
    }
}