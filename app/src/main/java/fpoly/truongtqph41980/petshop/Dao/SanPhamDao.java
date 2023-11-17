package fpoly.truongtqph41980.petshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Database.dbHelper;
import fpoly.truongtqph41980.petshop.Model.SanPham;

public class SanPhamDao {
    dbHelper dbs;

    public SanPhamDao(Context context) {
        dbs = new dbHelper(context);
    }
    public ArrayList<SanPham> getsanphamall(){
        ArrayList<SanPham> list = new ArrayList();
        SQLiteDatabase database = dbs.getReadableDatabase();
        Cursor cursor = database.rawQuery("select sp.masanpham, sp.tensanpham, sp.gia, lsp.maloaisanpham,lsp.tenloaisanpham from SANPHAM sp, LOAISANPHAM lsp where sp.maloaisanpham = lsp.maLoaisanpham",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean insert(String tensanpham, int gia, int maloaisanpham){
        SQLiteDatabase db = dbs.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensanpham",tensanpham);
        values.put("gia",gia);
        values.put("maloaisanpham",maloaisanpham);
        long check = db.insert("SANPHAM",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean update(int masanpham,String tensanpham, int gia, int maloaisanpham){
        SQLiteDatabase db = dbs.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensanpham",tensanpham);
        values.put("gia",gia);
        values.put("maloaisanpham",maloaisanpham);
        long check = db.update("SANPHAM",values,"masanpham = ?", new String[]{String.valueOf(masanpham)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public int delete(int masanpham) {
        SQLiteDatabase db = dbs.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CHITIETDONHANG where masanpham = ?", new String[]{String.valueOf(masanpham)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("SANPHAM", "masanpham = ?", new String[]{String.valueOf(masanpham)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }
}
