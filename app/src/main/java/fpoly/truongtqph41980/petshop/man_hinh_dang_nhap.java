package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fpoly.truongtqph41980.petshop.Dao.NguoiDungDao;
import fpoly.truongtqph41980.petshop.databinding.ActivityMainBinding;
import fpoly.truongtqph41980.petshop.databinding.ActivityManHinhDangNhapBinding;

public class man_hinh_dang_nhap extends AppCompatActivity {
ActivityManHinhDangNhapBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        binding = ActivityManHinhDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NguoiDungDao nguoiDungDao = new NguoiDungDao(this);
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = binding.tiedtTenDangNhap.getText().toString();
                String passWord = binding.tiedtNhapMatKhau.getText().toString();
                if (userName.isEmpty()) {
                    binding.tinLTenDangNhap.setError("Không được để trống");

                }
                if (passWord.isEmpty()) {
                    binding.tipLMatKhau.setError("Không được để trống");
                    return;
                }
                if (nguoiDungDao.checkDangNhap(userName, passWord)) {
                    Intent intent = new Intent(man_hinh_dang_nhap.this, MainActivity.class);
                    man_hinh_dang_nhap.this.startActivity(intent);
                } else {
                    binding.tinLTenDangNhap.setError("Tên đăng nhập không đúng");
                    binding.tipLMatKhau.setError("Mật khẩu không đúng");
                }

            }
        });
        binding.txtChuyenQuaDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(man_hinh_dang_nhap.this, man_hinh_dang_ky.class);
                startActivity(intent);
            }
        });
    }
}