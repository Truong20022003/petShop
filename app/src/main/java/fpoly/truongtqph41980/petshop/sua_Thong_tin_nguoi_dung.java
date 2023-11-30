package fpoly.truongtqph41980.petshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.truongtqph41980.petshop.Dao.NguoiDungDao;
import fpoly.truongtqph41980.petshop.Model.NguoiDung;
import fpoly.truongtqph41980.petshop.databinding.ActivitySuaThongTinNguoiDungBinding;

public class sua_Thong_tin_nguoi_dung extends AppCompatActivity {
    ActivitySuaThongTinNguoiDungBinding biding;
    NguoiDungDao dao;
    NguoiDung nguoiDung = new NguoiDung();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_nguoi_dung);
        biding = ActivitySuaThongTinNguoiDungBinding.inflate(getLayoutInflater());
        setContentView(biding.getRoot());
        SharedPreferences preferences = getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        String tenDN = preferences.getString("tendangnhap", "");
        String matkhau = preferences.getString("matkhau", "");
        String hoten = preferences.getString("hoten", "");
        String email = preferences.getString("email", "");
        String sodienthoai = preferences.getString("sodienthoai", "");
        String diachi = preferences.getString("diachi", "");
        String urlAnh = preferences.getString("anhtaikhoan", "");
//        binding.txtPMaNguoiDung.setText("Mã tài khoản: " + String.valueOf(mand));
//        binding.txtPTenDangNhap.setText("Tên đăng nhập: " + tenDN);
//        binding.txtPHoTen.setText("Họ tên: " + hoten);
//        binding.txtPEmail.setText("Email: " + email);
//        binding.txtPSoDienThoai.setText("Số điện thoại: " + sodienthoai);
//        binding.txtPDiaChi.setText("Địa chỉ: " + diachi);
        dao = new NguoiDungDao(this);
        biding.edtTenDangNhapDangKy.setText(tenDN);
        biding.edtNhapHoTen.setText(hoten);
        biding.edtNhapEmailDangKy.setText(email);
        biding.edtNhapDiaChiDangKy.setText(diachi);
        biding.edtNhapSDT.setText(String.valueOf(sodienthoai));
        //////


        biding.btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendangnhap = biding.edtTenDangNhapDangKy.getText().toString();
                String matkhaucu = biding.edmatKhau.getText().toString();
                String matkhaumoi = biding.edmatKhauMoi.getText().toString();
                String nhaplaimkmoi = biding.edtNhapLaiPassMoi.getText().toString();
                String hoten = biding.edtNhapHoTen.getText().toString();
                String email = biding.edtNhapEmailDangKy.getText().toString();
                String diachi = biding.edtNhapDiaChiDangKy.getText().toString();
                String sodienthoai = biding.edtNhapSDT.getText().toString();
//                newPass.equals(reNewPass)
                if (tendangnhap.isEmpty()) {
                    biding.edtTenDangNhapDangKy.setError("Vui lòng nhập tên đăng nhập");
                } else {
                    biding.edtTenDangNhapDangKy.setError(null);
                }
                ////
                if (matkhaumoi.isEmpty() || nhaplaimkmoi.isEmpty()) {
                    biding.edmatKhauMoi.setError("Vui lòng nhập lại mật khẩu");
                    biding.edtNhapLaiPassMoi.setError("Vui lòng nhập lại mật khẩu");
                } else if (!matkhaumoi.equals(nhaplaimkmoi)) {
                    biding.edtNhapLaiPassMoi.setError("Mật khẩu không trùng nhau");
                    biding.edmatKhauMoi.setError("Mật khẩu không trùng nhau");
                } else {
                    biding.edmatKhauMoi.setError(null);
                    biding.edtNhapLaiPassMoi.setError(null);
                    if (matkhaucu.equals(matkhau)) {
                        boolean result = dao.updatekhachhang(nguoiDung);
                        if (result) {
                            // Đăng ký thành công
                            Intent intent = new Intent(sua_Thong_tin_nguoi_dung.this, Profile.class);
                            startActivity(intent);
                            Toast.makeText(sua_Thong_tin_nguoi_dung.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Đăng ký thất bại
                            Toast.makeText(sua_Thong_tin_nguoi_dung.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        biding.edmatKhau.setError("mật khẩu cũ không trùng khớp");
                    }
                }
                //
                if (hoten.isEmpty()) {
                    biding.edtNhapHoTen.setError("Vui lòng nhập họ tên");
                } else {
                    biding.edtNhapHoTen.setError(null);
                }
                ///
                if (sodienthoai.isEmpty()) {
                    biding.edtNhapSDT.setError("Vui lòng nhập số điện thoại");
                } else if (!isValidPhoneNumber(sodienthoai)) {
                    biding.edtNhapSDT.setError("Số điện thoại không hợp lệ");
                } else {
                    biding.edtNhapSDT.setError(null);
                }
                ////
                if (diachi.isEmpty()) {
                    biding.edtNhapDiaChiDangKy.setError("Vui lòng nhập địa chỉ");
                } else {
                    biding.edtNhapDiaChiDangKy.setError(null);
                }
                ///
                if (email.isEmpty()) {
                    biding.edtNhapEmailDangKy.setError("Vui lòng nhập email");
                } else if (!isValidEmail(email)) {
                    biding.edtNhapEmailDangKy.setError("Email không hợp lệ");
                } else {
                    biding.edtNhapEmailDangKy.setError(null);
                }
                if (matkhaucu.isEmpty()) {
                    biding.edmatKhau.setError("Vui lòng nhập mật khẩu");
                } else {
                    biding.edmatKhau.setError(null);
                }
                ////

            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0\\d{9}";
        return phoneNumber.matches(regex);
    }

    // Hàm kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+(\\.+[a-z]+)?";
        return email.matches(regex);
    }
}