package fpoly.truongtqph41980.petshop.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.Model.GioHang;

public class DonHangDao {
    private dbHelper dbHelper;

    public DonHangDao(Context context) {
        this.dbHelper = new dbHelper(context);
    }

    public ArrayList<DonHang> getDsDonHang() {
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT DONHANG.madonhang, TAIKHOAN.mataikhoan, TAIKHOAN.hoten, DONHANG.ngaydathang, DONHANG.tongtien, DONHANG.trangthai " +
                    "FROM DONHANG,TAIKHOAN WHERE DONHANG.mataikhoan = TAIKHOAN.mataikhoan", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaTaiKhoan(cursor.getInt(1));
                    donHang.setTenTaiKhoan(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));

                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        }
        return list;
    }

    public boolean xoaDonHang(DonHang donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("DONHANG", "madonhang = ?", new String[]{String.valueOf(donHang.getMaDonHang())});
        return check > 0;

    }

    public boolean updateDonHang(DonHang donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mataikhoan", donHang.getMaTaiKhoan());
        values.put("trangthai", donHang.getTrangthai());
        long check = sqLiteDatabase.update("DONHANG", values, "madonhang = ?", new String[]{String.valueOf(donHang.getMaDonHang())});
        return check > 0;

    }

    public int insertDonHang(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mataikhoan", donHang.getMaTaiKhoan());
        values.put("ngaydathang", donHang.getNgayDatHang());
        values.put("tongtien", donHang.getTongTien());
        values.put("trangthai", donHang.getTrangthai());

        try {
            long insertedId = db.insert("DONHANG", null, values);
            db.close();

            // Kiểm tra xem đơn hàng đã được chèn thành công hay không
            if (insertedId > 0) {
                return (int) insertedId; // Trả về ID của đơn hàng nếu thành công
            } else {
                return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi chèn đơn hàng", e);
            return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
        }
    }

    public ArrayList<DonHang> getDonHangByMaTaiKhoan(int maTaiKhoan) {
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query = "SELECT DONHANG.madonhang, TAIKHOAN.mataikhoan, TAIKHOAN.hoten, DONHANG.ngaydathang, DONHANG.tongtien, DONHANG.trangthai FROM DONHANG JOIN TAIKHOAN ON DONHANG.mataikhoan = TAIKHOAN.mataikhoan WHERE TAIKHOAN.mataikhoan = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maTaiKhoan)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaTaiKhoan(cursor.getInt(1));
                    donHang.setTenTaiKhoan(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));
                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        }
        return list;
    }
}
