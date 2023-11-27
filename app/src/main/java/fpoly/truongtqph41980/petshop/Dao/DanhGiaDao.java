package fpoly.truongtqph41980.petshop.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.DanhGia;

public class DanhGiaDao {
    private dbHelper dbHelper;

    public DanhGiaDao(Context context) {
        this.dbHelper = new dbHelper(context);
    }

    public List<DanhGia> getDanhGiaByMaSanPham(int maSanPham) {
        List<DanhGia> danhGiaList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM DANHGIA WHERE masanpham = " + maSanPham;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Kiểm tra xem có dữ liệu trả về hay không
        if (cursor.moveToFirst()) {
            do {
                DanhGia danhGia = new DanhGia();
                danhGia.setMaDanhGia(cursor.getInt(0));

                // Thêm đánh giá vào danh sách
                danhGiaList.add(danhGia);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và cơ sở dữ liệu
        cursor.close();
        db.close();

        return danhGiaList;
    }
}
