package fpoly.truongtqph41980.petshop.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class dbHelper extends SQLiteOpenHelper {
    static String DB_NAME = "PetShop";
    static int DB_VERSION = 3;

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        ///1. Bảng tài khoản
        String taiKhoan = "CREATE TABLE TAIKHOAN(" +
                "mataikhoan integer primary key autoincrement," +
                " tendangnhap text not null," +
                " matkhau text not null," +
                " hoten text not null," +
                " email text not null," +
                " sodienthoai text not null," +
                " diachi text not null," +
                " sotien integer not null," +
                "loaitaikhoan text not null," +
                " anhtaikhoan text not null)";
        sqLiteDatabase.execSQL(taiKhoan);
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(1,'truongtq','123','Trần Quang Trường','truong@gmail.com','0787613866','nghe an',10000,'admin','https://i.pinimg.com/474x/4a/4e/2b/4a4e2bb5dc8078b76c2a160deeb92882.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(2,'doandt','123','Đỗ Thế Đoàn','truong@gmail.com','0787613866','nghe an',10000,'admin','https://t3.ftcdn.net/jpg/05/64/28/18/360_F_564281876_b90Cpe6MxpjC8ZVSR49Dl1UvKIjPDNK6.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(3,'hoangtn','123','Trần Ngọc hoàng','truong@gmail.com','0787613866','nghe an',10000,'admin','https://i.pinimg.com/originals/26/82/bf/2682bf05bc23c0b6a1145ab9c966374b.png')");
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(4,'truongtq1','123','Trần Quang Trường','truong@gmail.com','0787613866','nghe an',10000,'khachhang','https://e7.pngegg.com/pngimages/811/219/png-clipart-dog-cartoon-cuteness-kitten-avatar-mammal-face-thumbnail.png')");
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(5,'doandt1','123','Đỗ Thế Đoàn','truong@gmail.com','0787613866','nghe an',10000,'khachhang','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSR089qbmlk25UijJ0nXXJof8ibc80Jq-UuDA&usqp=CAU')");
        sqLiteDatabase.execSQL("INSERT INTO TAIKHOAN VALUES(6,'hoangtn1','123','Trần Ngọc hoàng','truong@gmail.com','0787613866','nghe an',10000,'khachhang','https://i.pinimg.com/originals/26/82/bf/2682bf05bc23c0b6a1145ab9c966374b.png')");


        //2. Bảng loại sản phẩm
        String loaiSanPham = "CREATE TABLE LOAISANPHAM(" +
                "maloaisanpham integer primary key autoincrement," +
                " tenloaisanpham text not null)";
        sqLiteDatabase.execSQL(loaiSanPham);
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(1,'Đồ ăn cho chó')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(2,'Đồ ăn cho mèo')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(3,'Phụ kiện cho chó')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(4,'Phụ kiện cho mèo')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(5,'Sữa tắm')");


        ///3. Bảng sản phẩm
        String sanPham = "CREATE TABLE SANPHAM(" +
                " masanpham integer primary key autoincrement," +
                " tensanpham text not null," +
                " gia integer not null," +
                " maloaisanpham integer REFERENCES LOAISANPHAM(maloaisanpham)," +
                " mota text not null," +
                " anhsanpham text not null," +
                " soluong integer not null)";
        sqLiteDatabase.execSQL(sanPham);
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(1,'Xúc xích cho mèo',10,2,' Hạt cho mèo vị cá biển BIOLINE Cat Cod Sausage là một loại thức ăn bổ sung tươi ngon và tự nhiên cho mèo yêu của bạn. Sản phẩm được sản xuất từ thịt cá biển tươi ngon, ít chất béo, ít calo và sử dụng nước tinh khiết.','https://cdn-img-v2.webbnc.net/uploadv2/web/12/12107/product/2019/11/11/08/48/1573462095_pro-dog-adult.jpg',12)");
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(2,'Xúc xích cho chó',50,1,' Hạt cho mèo vị cá biển BIOLINE Cat Cod Sausage là một loại thức ăn bổ sung tươi ngon và tự nhiên cho mèo yêu của bạn. Sản phẩm được sản xuất từ thịt cá biển tươi ngon, ít chất béo, ít calo và sử dụng nước tinh khiết.','https://pethouse.com.vn/wp-content/uploads/2023/01/ezgif-5-1e317ae8fd-800x800.jpg',222)");
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(3,'Hạt cho mèo',20,2,' Hạt cho mèo vị cá biển BIOLINE Cat Cod Sausage là một loại thức ăn bổ sung tươi ngon và tự nhiên cho mèo yêu của bạn. Sản phẩm được sản xuất từ thịt cá biển tươi ngon, ít chất béo, ít calo và sử dụng nước tinh khiết.','https://kingspet.vn/wp-content/uploads/2022/10/do-an-cho-meo-0.jpg',323)");
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(4,'Pate cho mèo',30,2,' Hạt cho mèo vị cá biển BIOLINE Cat Cod Sausage là một loại thức ăn bổ sung tươi ngon và tự nhiên cho mèo yêu của bạn. Sản phẩm được sản xuất từ thịt cá biển tươi ngon, ít chất béo, ít calo và sử dụng nước tinh khiết.','https://propetvn.com/thumbs/414x571x1/upload/photo/maximum11-2471-7550.png',22)");
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(5,'Túi đựng mèo',40,4,' Hạt cho mèo vị cá biển BIOLINE Cat Cod Sausage là một loại thức ăn bổ sung tươi ngon và tự nhiên cho mèo yêu của bạn. Sản phẩm được sản xuất từ thịt cá biển tươi ngon, ít chất béo, ít calo và sử dụng nước tinh khiết.','https://salt.tikicdn.com/cache/w1200/media/catalog/producttmp/78/9d/63/68a697cd3ed0e2b1e7c3521a9ddf021c.jpg',0)");
        //4. Bảng giỏ hàng
        String gioHang = "CREATE TABLE GIOHANG(" +
                "magiohang integer primary key autoincrement, " +
                "mataikhoan integer REFERENCES TAIKHOAN(mataikhoan)," +
                " masanpham integer REFERENCES SANPHAM(masanpham)," +
                " soluong integer not null)";
        sqLiteDatabase.execSQL(gioHang);
//        sqLiteDatabase.execSQL("INSERT INTO GIOHANG VALUES(1,2,1,3)");
//        sqLiteDatabase.execSQL("INSERT INTO GIOHANG VALUES(2,4,2,4)");
//        sqLiteDatabase.execSQL("INSERT INTO GIOHANG VALUES(3,1,1,1)");
//        sqLiteDatabase.execSQL("INSERT INTO GIOHANG VALUES(4,3,4,10)");
//        sqLiteDatabase.execSQL("INSERT INTO GIOHANG VALUES(5,5,3,7)");

        //5. Bảng đơn hàng
        String donHang = "CREATE TABLE DONHANG(" +
                " madonhang integer primary key autoincrement," +
                " mataikhoan integer REFERENCES TAIKHOAN(mataikhoan)," +
                " ngaydathang text not null," +
                " tongtien integer not null," +
                " trangthai text not null)";
        sqLiteDatabase.execSQL(donHang);
        sqLiteDatabase.execSQL("INSERT INTO DONHANG VALUES(1,2,'16/11/2023',100,'Đã nhận hàng')");
        sqLiteDatabase.execSQL("INSERT INTO DONHANG VALUES(2,5,'16/12/2023',200,'Đã nhận hàng')");
        sqLiteDatabase.execSQL("INSERT INTO DONHANG VALUES(3,2,'17/09/2023',300,'Đã nhận hàng')");
        sqLiteDatabase.execSQL("INSERT INTO DONHANG VALUES(4,4,'18/01/2023',400,'Đã nhận hàng')");
        sqLiteDatabase.execSQL("INSERT INTO DONHANG VALUES(5,3,'19/11/2023',50,'Đã nhận hàng')");



        //7. Bảng chi tiết đơn hàng
        String chiTietDonHang = "CREATE TABLE CHITIETDONHANG(" +
                "machitietdonhang integer primary key autoincrement," +
                " madonhang integer REFERENCES DONHANG(madonhang)," +
                " masanpham integer REFERENCES SANPHAM(masanpham)," +
                "soluong integer not null," +
                " dongia integer not null," +
                " thanhtien integer not null)";
        sqLiteDatabase.execSQL(chiTietDonHang);
        sqLiteDatabase.execSQL("INSERT INTO CHITIETDONHANG VALUES(1,2,5,5,20,20)");
        sqLiteDatabase.execSQL("INSERT INTO CHITIETDONHANG VALUES(2,2,1,4,30,30)");
        sqLiteDatabase.execSQL("INSERT INTO CHITIETDONHANG VALUES(3,3,2,3,30,30)");
        sqLiteDatabase.execSQL("INSERT INTO CHITIETDONHANG VALUES(4,2,3,2,30,30)");
        sqLiteDatabase.execSQL("INSERT INTO CHITIETDONHANG VALUES(5,3,5,5,10,10)");

        //8. Bảng thanh toán
        String thanhToan = "CREATE TABLE THANHTOAN(" +
                "mathanhtoan integer primary key autoincrement," +
                " madonhang integer REFERENCES DONHANG(madonhang)," +
                " ngaythanhtoan text not null," +
                " sotien integer not null)";
        sqLiteDatabase.execSQL(thanhToan);
        sqLiteDatabase.execSQL("INSERT INTO THANHTOAN VALUES(1,2,'16/11/2023',30)");
        sqLiteDatabase.execSQL("INSERT INTO THANHTOAN VALUES(2,3,'14/11/2023',300)");
        sqLiteDatabase.execSQL("INSERT INTO THANHTOAN VALUES(3,4,'19/11/2023',50)");
        sqLiteDatabase.execSQL("INSERT INTO THANHTOAN VALUES(4,1,'18/11/2023',70)");
        sqLiteDatabase.execSQL("INSERT INTO THANHTOAN VALUES(5,5,'17/11/2023',40)");

        //9. Bảng đánh giá
        String danhGia = "CREATE TABLE DANHGIA(" +
                "madanhgia integer primary key autoincrement," +
                " mataikhoan integer REFERENCES TAIKHOAN(mataikhoan)," +
                " masanpham integer REFERENCES SANPHAM(masanpham)," +
                " danhgia text not null," +
                " nhanxet text not null," +
                " ngaydanhgia text not null)";
        sqLiteDatabase.execSQL(danhGia);
//        sqLiteDatabase.execSQL("INSERT INTO DANHGIA VALUES(1,1,2,'Tốt','Mèo rất thích','11/11/2023')");
//        sqLiteDatabase.execSQL("INSERT INTO DANHGIA VALUES(2,5,3,'Khá tốt','Mèo rất thích','11/12/2023')");
//        sqLiteDatabase.execSQL("INSERT INTO DANHGIA VALUES(3,4,4,'Khá tốt','Mèo rất thích','12/13/2023')");
//        sqLiteDatabase.execSQL("INSERT INTO DANHGIA VALUES(4,3,3,'Tốt','Mèo rất thích','11/14/2023')");
//        sqLiteDatabase.execSQL("INSERT INTO DANHGIA VALUES(5,2,2,'Tốt','Mèo rất thích','11/15/2023')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TAIKHOAN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SANPHAM");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISANPHAM");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GIOHANG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LICHSUMUA");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DONHANG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHITIETDONHANG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHTOAN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANHGIA");
            onCreate(sqLiteDatabase);

        }
    }

}
