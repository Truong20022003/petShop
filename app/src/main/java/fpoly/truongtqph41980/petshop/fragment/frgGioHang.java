package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

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

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGioHangBinding;


public class frgGioHang extends Fragment implements adapter_gio_hang.TotalPriceListener {

    private ArrayList<GioHang> list = new ArrayList<>();
    private adapter_gio_hang gioHangAdapter;
    FragmentFrgGioHangBinding binding;
    View gView;

    GioHangDao gioHangDao;
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


        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getMasp().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer masp) {

                if (isAdded() && isVisible()) {
                    if (sharedViewModel.getAddToCartClicked().getValue() != null && sharedViewModel.getAddToCartClicked().getValue()) {
                        Boolean addToCartClicked = sharedViewModel.getAddToCartClicked().getValue();
                        updateGioHangByMaSp(masp);
                        sharedViewModel.setAddToCartClicked(true); // Đặt lại trạng thái
                    }
                }
            }
        });

        list = gioHangDao.getDSGioHang();
        displayCart(list);
        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return gView;
    }






    public void updateGioHangByMaSp(int masp) {
        if (masp > 0) {
            // Lấy thông tin người dùng từ SharedPreferences
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
            int mand = sharedPreferences.getInt("mataikhoan", 0);

            // Kiểm tra nếu mã sản phẩm đã tồn tại trong giỏ hàng
            GioHang hang = gioHangDao.getGioHangByMasp(masp, mand);

            // Hiển thị giỏ hàng sau khi đã cập nhật
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
