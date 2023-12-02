package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
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


public class frgGianHang extends Fragment implements ViewModelStoreOwner {

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
    private ArrayList<GioHang> gioHangArrayList = new ArrayList<>();
    // Thiết lập listener


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGianHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();

        spDao = new SanPhamDao(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rcvGianHang.setLayoutManager(layoutManager);
        list = spDao.getsanphamall();
        adapterGianHang = new adapter_gian_hang(getActivity(), list, sharedViewModel);
        binding.rcvGianHang.setAdapter(adapterGianHang);
//        sharedViewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(SharedViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        gioHangAdapter = new adapter_gio_hang(getActivity(), sharedViewModel, gioHangArrayList);

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

    private int getSoLuongSp(int maSanPham) {
        for (SanPham sanPham : list) {
            if (sanPham.getMasanpham() == maSanPham) {
                return sanPham.getSoluong();
            }
        }
        return 0; // Trả về 0 nếu không tìm thấy sản phẩm
    }
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return new ViewModelStore();
    }

    private void addToCart(SanPham sanPham) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("mataikhoan", 0);
        int maSanPham = sanPham.getMasanpham();
        int slSanPham = getSoLuongSp(maSanPham);

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        if (!sharedViewModel.isProductInCart(maSanPham)) {
            if (slSanPham > 0) {
                // Nếu số lượng sản phẩm > 0, thêm sản phẩm vào giỏ hàng với số lượng là 1
                sharedViewModel.setMasp(maSanPham);
                sharedViewModel.setAddToCartClicked(true);
                sharedViewModel.addProductToCart(maSanPham);
//                sharedViewModel.setQuantityToAdd(1);
                gioHangDao.insertGioHang(new GioHang(maSanPham, mand, 1));
                Snackbar.make(getView(), "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
            }
        } else {
            GioHang gioHang = gioHangDao.getGioHangByMasp(maSanPham, mand);
            if (gioHang != null) {
                if (gioHang.getSoLuongMua() < slSanPham) {
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    gioHangDao.updateGioHang(gioHang);
                    Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}