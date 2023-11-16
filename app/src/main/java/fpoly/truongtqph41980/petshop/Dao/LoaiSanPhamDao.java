package fpoly.truongtqph41980.petshop.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.model.LoaiSanPham;

public class LoaiSanPhamDao {
    private SQLiteDatabase db;

    public LoaiSanPhamDao(Context context) {
        dbHelper dbHelper = new dbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public boolean insert(String tenloai){

        ContentValues values = new ContentValues();
        values.put("tenloaisanpham", tenloai);
        long check = db.insert("LOAISANPHAM",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean update(LoaiSanPham loaiSanPham){
        ContentValues values=new ContentValues();
        values.put("tenloaisanpham",loaiSanPham.getTenloaisp());
        long check= db.update("LOAISANPHAM",values,"maloaisanpham=?",new String[]{String.valueOf(loaiSanPham.getMaloaisp())});
        if (check==-1){
            return false;
        }else {
            return true;
        }
    }
    public boolean delete(LoaiSanPham loaiSanPham){
      long row=  db.delete("LOAISANPHAM","maloaisanpham=?",new String[]{String.valueOf(loaiSanPham.getMaloaisp())});
        return (row>0);
    }

public ArrayList<LoaiSanPham> getalltheloai(){

    ArrayList<LoaiSanPham> list = new ArrayList<>();
    Cursor cursor = db.rawQuery("SELECT *FROM LOAISANPHAM",null);
    if (cursor.getCount()!=0) {
        cursor.moveToFirst();
        do {
            list.add(new LoaiSanPham(cursor.getInt(0), cursor.getString(1)));
        }while (cursor.moveToNext());
    }
    return list;
}

}
