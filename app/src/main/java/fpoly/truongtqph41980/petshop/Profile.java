package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;

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
        String urlAnh = preferences.getString("anhtaikhoan","");
        binding.hiName.setText("Hi, " + hoten);
        binding.txtPMaNguoiDung.setText("Mã tài khoản: " + String.valueOf(mand));
        binding.txtPTenDangNhap.setText("Tên đăng nhập: " + tenDN);
        binding.txtPHoTen.setText("Họ tên: " + hoten);
        binding.txtPEmail.setText("Email: " + email);
        binding.txtPSoDienThoai.setText("Số điện thoại: " + sodienthoai);
        binding.txtPDiaChi.setText("Địa chỉ: " + diachi);
        binding.txtPSoTien.setText("Số tiền hiện có: " + String.valueOf(tien));
        binding.txtPLoaiTaiKhoan.setText("Loại tài khoản: " + loaitaikhoan);
//        load(list.get(position).getAnhnguoidung()).into(holder.binding.imgAnhQlNguoiDung);
        Picasso.get().load(urlAnh).into(binding.imgAvatarProfile);
        binding.btnPDangXuat.setOnClickListener(view ->
                startActivity(new Intent(Profile.this, man_hinh_dang_nhap.class))
        );
        binding.imgBack.setOnClickListener(view ->
                startActivity(new Intent(Profile.this, MainActivity.class))
        );
        binding.btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, lich_su_don_hang.class));
            }
        });
        binding.btnhoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,sua_Thong_tin_nguoi_dung.class));
            }
        });
    }

}