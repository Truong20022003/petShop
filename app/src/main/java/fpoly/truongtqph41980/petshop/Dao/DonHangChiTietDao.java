package fpoly.truongtqph41980.petshop.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;

public class DonHangChiTietDao {
    private dbHelper dbHelper;

    public DonHangChiTietDao(Context context) {
        this.dbHelper = new dbHelper(context);
    }

    public ArrayList<DonHangChiTiet> getDsDonHangChiTiet() {
        ArrayList<DonHangChiTiet> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT ct.machitietdonhang, dh.madonhang,sp.masanpham,sp.tensanpham,ct.soluong,ct.dongia,ct.thanhtien FROM CHITIETDONHANG ct, DONHANG dh,SANPHAM sp WHERE ct.madonhang = dh.madonhang and ct.masanpham = sp.masanpham", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                    donHangChiTiet.setMaChiTietDonHang(cursor.getInt(0));
                    donHangChiTiet.setMaDonHang(cursor.getInt(1));
                    donHangChiTiet.setMaSanPham(cursor.getInt(2));
                    donHangChiTiet.setTenSanPham(cursor.getString(3));
                    donHangChiTiet.setSoLuong(cursor.getInt(4));
                    donHangChiTiet.setDonGia(cursor.getInt(5));
                    donHangChiTiet.setThanhTien(cursor.getInt(6));
                    list.add(donHangChiTiet);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        }
        return list;
    }

    public boolean xoaDonHang(DonHangChiTiet donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("CHITIETDONHANG", "machitietdonhang = ?", new String[]{String.valueOf(donHang.getMaChiTietDonHang())});
        return check > 0;

    }

    public boolean insertDonHangChiTiet(DonHangChiTiet donHangChiTiet) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("madonhang", donHangChiTiet.getMaDonHang());
        values.put("masanpham", donHangChiTiet.getMaSanPham());
        values.put("soluong", donHangChiTiet.getSoLuong());
        values.put("dongia", donHangChiTiet.getDonGia());
        values.put("thanhtien", donHangChiTiet.getThanhTien());

        long check = sqLiteDatabase.insert("CHITIETDONHANG", null, values);
        return check > 0;
    }

    public boolean updateDonHangChiTiet(DonHangChiTiet donHangChiTiet) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("madonhang", donHangChiTiet.getMaDonHang());
        values.put("masanpham", donHangChiTiet.getMaSanPham());
        values.put("soluong", donHangChiTiet.getSoLuong());
        values.put("dongia", donHangChiTiet.getDonGia());
        values.put("thanhtien", donHangChiTiet.getThanhTien());

        long check = sqLiteDatabase.update("CHITIETDONHANG", values, "machitietdonhang = ?", new String[]{String.valueOf(donHangChiTiet.getMaChiTietDonHang())});
        return check > 0;
    }
}
