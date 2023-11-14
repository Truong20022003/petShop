package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        binding.btnDangNhap.setOnClickListener(view -> {
            Intent intent = new Intent(man_hinh_dang_nhap.this, MainActivity.class);
            startActivity(intent);
        });
    }
}