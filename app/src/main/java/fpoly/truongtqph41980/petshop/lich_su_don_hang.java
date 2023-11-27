package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.adapter.Adapter_lich_su_don_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.databinding.ActivityLichSuDonHangBinding;
import fpoly.truongtqph41980.petshop.databinding.ActivityMainBinding;

public class lich_su_don_hang extends AppCompatActivity {
ActivityLichSuDonHangBinding binding;
    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDao dao;
    private Adapter_lich_su_don_hang adapterDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        binding = ActivityLichSuDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = preferences.getInt("mataikhoan", 0);

        dao = new DonHangDao(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvLichSuDonHang.setLayoutManager(layoutManager);
        list = dao.getDonHangByMaTaiKhoan(mand);
        adapterDonHang = new Adapter_lich_su_don_hang(list,this);
        binding.rcvLichSuDonHang.setAdapter(adapterDonHang);
        binding.imgBackLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(lich_su_don_hang.this,Profile.class));
            }
        });
    }
}