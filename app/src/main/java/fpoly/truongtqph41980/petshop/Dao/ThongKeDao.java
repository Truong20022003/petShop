package fpoly.truongtqph41980.petshop.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;

public class ThongKeDao {
    dbHelper dbs;

    public ThongKeDao(Context context) {
        dbs = new dbHelper(context);
    }

        public int tongDoanhThu(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/","");
        ngayKetThuc = ngayKetThuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tongtien) FROM DONHANG WHERE substr(ngaydathang,7) || substr(ngaydathang,4,2) || substr(ngaydathang,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau,ngayKetThuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {

            return 0;
        }
    }
    public int tongDonHang(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/","");
        ngayKetThuc = ngayKetThuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(madonhang) FROM DONHANG WHERE substr(ngaydathang,7) || substr(ngaydathang,4,2) || substr(ngaydathang,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau,ngayKetThuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {
            return 0;
        }
    }
//    public ArrayList<Sach> getTop10(){
//        ArrayList<Sach> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT PHIEUMUON.maSach, SACH.tenSach, COUNT(PHIEUMUON.maSach) FROM PHIEUMUON, SACH WHERE PHIEUMUON.maSach = SACH.maSach GROUP BY PHIEUMUON.maSach, SACH.tenSach ORDER BY COUNT(PHIEUMUON.maSach) DESC LIMIT 10",null);
//        if (cursor.getCount() != 0){
//            cursor.moveToFirst();
//            do {
//                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
//            }while (cursor.moveToNext());
//        }
//
//        return list;
//    }
public ArrayList<DonHangChiTiet> getTop3(){
    ArrayList<DonHangChiTiet> list = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase = dbs.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT SANPHAM.masanpham, SANPHAM.tensanpham, SUM(CHITIETDONHANG.soluong) AS soluongban\n" +
            "FROM SANPHAM\n" +
            "JOIN CHITIETDONHANG ON SANPHAM.masanpham = CHITIETDONHANG.masanpham\n" +
            "GROUP BY SANPHAM.masanpham\n" +
            "ORDER BY soluongban DESC\n" +
            "LIMIT 3;",null);
    if (cursor.getCount() != 0){
        cursor.moveToFirst();
        do {
            list.add(new DonHangChiTiet(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
        }while (cursor.moveToNext());
    }

    return list;
}
//    public int tongDoanhThu(String ngayBatDau, String ngayKetThuc) {
//        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tongtien) FROM DONHANG " +
//                "WHERE ngaydathang BETWEEN ? AND ?;", new String[]{ngayBatDau, ngayKetThuc});
//        if (cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        } else {
//            return 0;
//        }
//    }
//    public int tongDonHang(String ngayBatDau, String ngayKetThuc) {
//        ngayBatDau = ngayBatDau.replace("/", "");
//        ngayKetThuc = ngayKetThuc.replace("/", "");
//        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(madonhang) FROM DONHANG \n" +
//                "WHERE ngaydathang BETWEEN ? AND ?;", new String[]{ngayBatDau, ngayKetThuc});
//        if (cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        } else {
//            return 0;
//        }
//    }
}
