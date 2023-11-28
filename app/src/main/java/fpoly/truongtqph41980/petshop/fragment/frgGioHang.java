package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.NguoiDungDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.ViewActivity.Thanh_Toan_Hoa_Don;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGioHangBinding;


public class frgGioHang extends Fragment implements adapter_gio_hang.TotalPriceListener {

    private ArrayList<GioHang> list = new ArrayList<>();
    private adapter_gio_hang gioHangAdapter;
    FragmentFrgGioHangBinding binding;
    View gView;

    GioHangDao gioHangDao;

    private DonHangDao donHangDao;

    private adapter_don_hang adapterDonHang;
    private frgQuanLyDonHang frgQuanLyDonHang;
    private ArrayList<DonHang> listDonHang = new ArrayList<>();
    private SharedViewModel sharedViewModel;

    public frgGioHang() {
    }

    private void displayCart(ArrayList<GioHang> cartList) {
        RecyclerView rcv = binding.rcvGioHang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);

        if (gioHangAdapter == null) {
            gioHangAdapter = new adapter_gio_hang(getContext(), cartList);
            rcv.setAdapter(gioHangAdapter);

        } else {
            gioHangAdapter.updateCartList(cartList);
            gioHangAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGioHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();

        RecyclerView rcv = binding.rcvGioHang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        gioHangAdapter = new adapter_gio_hang(getContext(), list);
        rcv.setAdapter(gioHangAdapter);
        gioHangDao = new GioHangDao(getContext());
        gioHangAdapter.setTotalPriceListener(this);

        donHangDao = new DonHangDao(getContext());


        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getMasp().observe(getViewLifecycleOwner(), masp -> {

            if (isAdded() && isVisible()) {
                if (sharedViewModel.getAddToCartClicked().getValue() != null && sharedViewModel.getAddToCartClicked().getValue()) {
                    updateGioHangByMaSp(masp);
                    sharedViewModel.setAddToCartClicked(true); // Đặt lại trạng thái


                }
            }
        });
        binding.btnThanhToan.setOnClickListener(view -> {
            int totalAmount = Integer.parseInt(binding.txtTongTienThanhToan.getText().toString());
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
            int mand = sharedPreferences.getInt("mataikhoan", 0);
            int tienHienCo = sharedPreferences.getInt("sotien",0);

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayHienTai = currentDate.format(formatter);

            if (tienHienCo >= totalAmount) {
                int soTienConLai = tienHienCo - totalAmount;
                NguoiDungDao nguoiDungDao = new NguoiDungDao(getContext());
                if (nguoiDungDao.updateSoTien(mand,  soTienConLai)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("sotien", soTienConLai);
                    editor.apply();
                    DonHang donHang = new DonHang(mand,ngayHienTai, totalAmount,"Đang giao hàng");
                    if (donHangDao.insertDonHang(donHang)){
                        listDonHang.clear();
                        listDonHang.addAll(donHangDao.getDsDonHang());
                    }else {
                        Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Đã thanh toán thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thất bại khi cập nhật tài khoản!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Số tiền trong tài khoản không đủ!", Toast.LENGTH_SHORT).show();
            }


        });
        list = gioHangDao.getDSGioHang();
        displayCart(list);

        return gView;
    }






    public void updateGioHangByMaSp(int masp) {
        if (masp > 0) {
            ArrayList<GioHang> updatedCartList = gioHangDao.getDSGioHang();
            displayCart(updatedCartList);
        } else {
        }
    }

    @Override
    public void onTotalPriceUpdated(int totalAmount) {
        if (binding != null && binding.txtTongTienThanhToan != null) {
            binding.txtTongTienThanhToan.setText(String.valueOf(totalAmount));
        }
    }
}
