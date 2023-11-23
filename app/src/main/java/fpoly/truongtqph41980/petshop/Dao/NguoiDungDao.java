package fpoly.truongtqph41980.petshop.Dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.NguoiDung;

public class NguoiDungDao {
    private final dbHelper dbHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public NguoiDungDao(Context context) {
        this.dbHelper = new dbHelper(context);
        if (context != null) {
            sharedPreferences = context.getSharedPreferences("NGUOIDUNG", context.MODE_PRIVATE);
        } else {
            // Xử lý khi context là null, có thể thông báo lỗi hoặc thực hiện xử lý phù hợp
            Log.e(TAG, "Context is null in NguoiDungDao constructor");
        }

    }

    public ArrayList<NguoiDung> getAllNguoiDung() {

        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setMaTaiKhoan(cursor.getInt(0));
                    nguoiDung.setTenDangNhap(cursor.getString(1));
                    nguoiDung.setMatKhau(cursor.getString(2));
                    nguoiDung.setHoTen(cursor.getString(3));
                    nguoiDung.setEmail(cursor.getString(4));
                    nguoiDung.setSoDienThoai(cursor.getString(5));
                    nguoiDung.setDiaChi(cursor.getString(6));
                    nguoiDung.setSoTien(cursor.getInt(7));
                    nguoiDung.setLoaiTaiKhoan(cursor.getString(8));

                    list.add(nguoiDung);
                    cursor.moveToNext();
                }

            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi", e);
        }

        return list;
    }

    public boolean checkDangNhap(String tenDangNhap, String matKhau) {
        Log.d(TAG, "CheckDangNhap: " + tenDangNhap + " - " + matKhau);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM TAIKHOAN WHERE tendangnhap = ? AND matkhau = ?", new String[]{tenDangNhap, matKhau});
//            "mataikhoan integer primary key autoincrement," +
//                    " tendangnhap text not null," +
//                    " matkhau text not null," +
//                    " hoten text not null," +
//                    " email text not null," +
//                    " sodienthoai text not null," +
//                    " diachi text not null," +
//                    " sotien integer not null," +
//                    "loaitaikhoan text not null)";
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                editor = sharedPreferences.edit();
                editor.putInt("mataikhoan", cursor.getInt(0));
                editor.putString("tendangnhap", cursor.getString(1));
                editor.putString("matkhau", cursor.getString(2));
                editor.putString("hoten", cursor.getString(3));
                editor.putString("email", cursor.getString(4));
                editor.putString("sodienthoai", cursor.getString(5));
                editor.putString("diachi", cursor.getString(6));
                editor.putInt("sotien", cursor.getInt(7));
                editor.putString("loaitaikhoan", cursor.getString(8));
                editor.putString("anhtaikhoan",cursor.getString(9));
                editor.apply();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi kiểm tra đăng nhập", e);
            return false;
        }
    }

    public boolean checkDangKy(NguoiDung nguoiDung) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tendangnhap", nguoiDung.getTenDangNhap());
        values.put("matkhau", nguoiDung.getMatKhau());
        values.put("hoten", nguoiDung.getHoTen());
        values.put("email", nguoiDung.getEmail());
        values.put("sodienthoai", nguoiDung.getSoDienThoai());
        values.put("diachi", nguoiDung.getDiaChi());
        values.put("sotien", nguoiDung.getSoTien());
        values.put("loaitaikhoan", nguoiDung.getLoaiTaiKhoan());
        values.put("anhtaikhoan", nguoiDung.getAnhnguoidung());
        long result = db.insert("TAIKHOAN", null, values);
        return result != -1;
    }

    public boolean tenDangNhapDaTonTai(String tenDangNhap) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM TAIKHOAN WHERE tendangnhap =?";
        Cursor cursor = db.rawQuery(query, new String[]{tenDangNhap});
        boolean tonTai = cursor.getCount() > 0;
        cursor.close();
        return tonTai;
    }
    public boolean xoaNguoiDung(NguoiDung nd){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("TAIKHOAN","mataikhoan = ?",new String[]{String.valueOf(nd.getMaTaiKhoan())});
        return check>0;
    }
}
