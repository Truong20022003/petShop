package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.SanPhamCT;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGianHangBinding;


public class frgGianHang extends Fragment {

    public frgGianHang() {
        // Required empty public constructor
    }

    FragmentFrgGianHangBinding binding;
    View gView;
    private ArrayList<SanPham> list = new ArrayList<>();
    SanPhamDao spDao;
    adapter_gian_hang adapterGianHang;
    private SharedViewModel sharedViewModel;
    private adapter_gio_hang gioHangAdapter;
    private GioHangDao gioHangDao;
    // Thiết lập listener
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGianHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();
        spDao = new SanPhamDao(getContext());
        gioHangDao = new GioHangDao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvGianHang.setLayoutManager(layoutManager);
        list = spDao.getsanphamall();
        adapterGianHang = new adapter_gian_hang(getContext(), list);
        binding.rcvGianHang.setAdapter(adapterGianHang);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        gioHangAdapter = new adapter_gio_hang(getContext(), new ArrayList<>());
        adapterGianHang.setOnAddToCartClickListener(new adapter_gian_hang.OnAddToCartClickListener() {
            @Override
            public void onAddToCartClick(SanPham sanPham) {

                addToCart(sanPham);

            }
        });

        adapterGianHang.setOnItemClickListener(position -> {
            SanPham selectedSanPham = list.get(position);
            Intent intent = new Intent(getContext(), SanPhamCT.class);
            intent.putExtra("sanPham", selectedSanPham);
            startActivity(intent);
        });
        // Inflate the layout for this fragment
        return gView;
    }

    private void addToCart(SanPham sanPham) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);

        int mand = sharedPreferences.getInt("mataikhoan", 0);
        if (!sharedViewModel.isProductInCart(sanPham.getMasanpham())) {
            sharedViewModel.setMasp(sanPham.getMasanpham());
            sharedViewModel.setAddToCartClicked(true);
            sharedViewModel.addProductToCart(sanPham.getMasanpham());
            sharedViewModel.setQuantityToAdd(1);

            gioHangDao.insertGioHang(new GioHang(sanPham.getMasanpham(), mand, 1));

        } else {

            GioHang hang = gioHangDao.getGioHangByMasp(sanPham.getMasanpham(),mand);
            if (hang != null) {
                hang.setSoLuongMua(hang.getSoLuongMua() + 1);
                gioHangDao.updateGioHang(hang);
            } else {
                GioHang newCartItem = new GioHang(sanPham.getMasanpham(), mand, 1);
                gioHangDao.insertGioHang(newCartItem);
            }

            gioHangAdapter.notifyDataSetChanged();

        }
        ArrayList<GioHang> updatedCartList = gioHangDao.getDSGioHang();
        gioHangAdapter.updateCartList(updatedCartList);
        gioHangAdapter.notifyDataSetChanged();
        Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
    }

}