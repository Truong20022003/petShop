package fpoly.truongtqph41980.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.databinding.ActivityMainBinding;
import fpoly.truongtqph41980.petshop.fragment.ThongKe;
import fpoly.truongtqph41980.petshop.fragment.frgDonHang;
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
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View headerLayout = binding.navigationViewMain.getHeaderView(0);
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        handleBottomNavigationItemSelected();


        binding.navigationViewMain.setNavigationItemSelectedListener(item -> {
            handleNavigationViewItemSelected(item);
            return false;
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            binding.drawerLayoutMain.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        mMenu = menu;
        return true;
    }
    private void handleBottomNavigationItemSelected() {

        binding.navBottomMain.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_home) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);

            } else if (item.getItemId() == R.id.nav_bot_sanpham) {
                replaceFragment(new frgDonHang());

            } else if (item.getItemId() == R.id.nav_bot_giohang) {
                replaceFragment(new frgGioHang());

            } else if (item.getItemId() == R.id.nav_bot_naptien) {
                replaceFragment(new frgNapTien());

            }
            getSupportActionBar().setTitle(item.getTitle());
            return true;
        });
    }


    private void handleNavigationViewItemSelected(MenuItem item) {

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
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, man_hinh_dang_nhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            }, 2000);

        }
        getSupportActionBar().setTitle(item.getTitle());
        binding.drawerLayoutMain.closeDrawer(GravityCompat.START);
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
    }

}