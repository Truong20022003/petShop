package fpoly.truongtqph41980.petshop.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.DanhGia;
import fpoly.truongtqph41980.petshop.Model.DonHang;

public class DanhGiaDao {
    private dbHelper dbHelper;

    public DanhGiaDao(Context context) {
        this.dbHelper = new dbHelper(context);
    }

    public ArrayList<DanhGia> getDanhGiaByMaSanPham(int maSanPham) {
        ArrayList<DanhGia> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT " +
                    "DANHGIA.madanhgia, DANHGIA.mataikhoan, TAIKHOAN.hoten, DANHGIA.masanpham, SANPHAM.tensanpham, DANHGIA.danhgia, DANHGIA.nhanxet, DANHGIA.ngaydanhgia " +
                    "FROM DANHGIA " +
                    "INNER JOIN TAIKHOAN ON DANHGIA.mataikhoan = TAIKHOAN.mataikhoan " +
                    "INNER JOIN SANPHAM ON DANHGIA.masanpham = SANPHAM.masanpham " +
                    "WHERE DANHGIA.masanpham = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maSanPham)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DanhGia danhGia = new DanhGia();
                    danhGia.setMaDanhGia(cursor.getInt(0));
                    danhGia.setMaTaiKhoan(cursor.getInt(1));
                    danhGia.setTenTaiKhoan(cursor.getString(2));
                    danhGia.setMaSanPham(cursor.getInt(3));
                    danhGia.setTenSanPham(cursor.getString(4));
                    danhGia.setDanhGia(cursor.getString(5));
                    danhGia.setNhanXet(cursor.getString(6));
                    danhGia.setNgayDanhGia(cursor.getString(7));

                    list.add(danhGia);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
        return list;
    }

}
