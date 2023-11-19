package fpoly.truongtqph41980.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import fpoly.truongtqph41980.petshop.databinding.ActivityMainBinding;
import fpoly.truongtqph41980.petshop.fragment.ThongKe;
import fpoly.truongtqph41980.petshop.fragment.frgGianHang;
import fpoly.truongtqph41980.petshop.fragment.frgGioHang;
import fpoly.truongtqph41980.petshop.fragment.frgNapTien;
import fpoly.truongtqph41980.petshop.fragment.frgQuanLyDonHang;
import fpoly.truongtqph41980.petshop.fragment.frgQuanLyLoaiSanPham;
import fpoly.truongtqph41980.petshop.fragment.frgQuanLyNapTien;
import fpoly.truongtqph41980.petshop.fragment.frgQuanLyNguoiDung;
import fpoly.truongtqph41980.petshop.fragment.frgQuanLySanPham;
import fpoly.truongtqph41980.petshop.fragment.frg_Ve_Chung_Toi;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    //    Menu mMenu;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigationView = binding.navigationViewMain;
        View headerLayout = binding.navigationViewMain.getHeaderView(0);
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        handleBottomNavigationItemSelected();
        binding.btnProFile.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, Profile.class)));

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_qlnguoidung) {
                replaceFragment(new frgQuanLyNguoiDung());
            } else if (item.getItemId() == R.id.action_qlsanpham) {
                replaceFragment(new frgQuanLySanPham());
            } else if (item.getItemId() == R.id.action_qlLoaisanpham) {
                replaceFragment(new frgQuanLyLoaiSanPham());
            } else if (item.getItemId() == R.id.action_qlDonHang) {
                replaceFragment(new frgQuanLyDonHang());
            } else if (item.getItemId() == R.id.action_qlNapTien) {
                replaceFragment(new frgQuanLyNapTien());
            } else if (item.getItemId() == R.id.action_qlThongKe) {
                replaceFragment(new ThongKe());
            } else if (item.getItemId() == R.id.action_qlVeChungToi) {
                replaceFragment(new frg_Ve_Chung_Toi());
            } else if (item.getItemId() == R.id.action_qlDangXuat) {
                Intent intent = new Intent(MainActivity.this, man_hinh_dang_nhap.class);
                startActivity(intent);

            }
            getSupportActionBar().setTitle(item.getTitle());
            binding.drawerLayoutMain.closeDrawer(GravityCompat.START);

            return false;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        String loaiTaiKhoan = sharedPreferences.getString("loaitaikhoan", "");
        if (!loaiTaiKhoan.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.action_qlnguoidung).setVisible(false);
            menu.findItem(R.id.action_qlsanpham).setVisible(false);
            menu.findItem(R.id.action_qlLoaisanpham).setVisible(false);
            menu.findItem(R.id.action_qlDonHang).setVisible(false);
            menu.findItem(R.id.action_qlNapTien).setVisible(false);
            menu.findItem(R.id.action_qlThongKe).setVisible(false);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayoutMain.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
//        mMenu = menu;
//        return true;
//    }
    private void handleBottomNavigationItemSelected() {

        binding.navBottomMain.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_home) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_bot_sanpham) {
                replaceFragment(new frgGianHang());

            } else if (item.getItemId() == R.id.nav_bot_giohang) {
                replaceFragment(new frgGioHang());

            } else if (item.getItemId() == R.id.nav_bot_naptien) {
                replaceFragment(new frgNapTien());

            }
            getSupportActionBar().setTitle(item.getTitle());
            return true;
        });
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
    }

}