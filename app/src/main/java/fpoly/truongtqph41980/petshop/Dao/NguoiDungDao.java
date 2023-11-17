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

    public NguoiDungDao(Context context) {
        this.dbHelper = new dbHelper(context);
        sharedPreferences = context.getSharedPreferences("NGUOIDUNG", context.MODE_PRIVATE);

    }
    public ArrayList<NguoiDung> getAllNguoiDung(){

        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN",null);
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
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
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }

        return list;
    }
    public boolean checkDangNhap(String tenDangNhap, String matKhau){
        Log.d(TAG, "CheckDangNhap: " + tenDangNhap + " - " + matKhau);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM TAIKHOAN WHERE tendangnhap = ? AND matkhau = ?", new String[]{tenDangNhap, matKhau});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mataikhoan", cursor.getString(0));
                editor.putString("tendangnhap", cursor.getString(1));
                editor.putString("matkhau", cursor.getString(2));
                editor.putString("loaitaikhoan", cursor.getString(3));
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

        long result = db.insert("TAIKHOAN", null, values);
        return result != -1;
    }

}
