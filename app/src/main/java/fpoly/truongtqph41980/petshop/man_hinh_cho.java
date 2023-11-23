package fpoly.truongtqph41980.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import fpoly.truongtqph41980.petshop.ActivityManHinhCho.Man_Hinh_Cho_1;
import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Utils.PreferenceUtils;

public class man_hinh_cho extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);
        Handler handler = new Handler();
        if (PreferenceUtils.isFirstRun(this)) {
            // Nếu là lần đầu, hiển thị 3 màn hình

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(man_hinh_cho.this, Man_Hinh_Cho_1.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        } else {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(man_hinh_cho.this, man_hinh_dang_nhap.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }



    }

}