package fpoly.truongtqph41980.petshop.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGioHangBinding;


public class frgGioHang extends Fragment {


    public frgGioHang() {
        // Required empty public constructor
    }

    private ArrayList<SanPham> cartItemList = new ArrayList<>();
    private adapter_gio_hang gioHangAdapter;
    FragmentFrgGioHangBinding binding;
    View gView;
    private viewmd cartViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(requireActivity()).get(viewmd.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGioHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<SanPham>>() {
    @Override
    public void onChanged(ArrayList<SanPham> sanPhams) {
        updateCartRecyclerView(sanPhams);
    }
});
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            SanPham cartItem = bundle.getParcelable("sanPham");
//            if (cartItem != null) {
//                addToCart(cartItem);
//            }
//        }


        return gView;
    }


    public void addToCart(SanPham sanPham) {
        cartItemList.add(sanPham);
        updateCartRecyclerView(cartItemList);
    }

    private void updateCartRecyclerView(ArrayList<SanPham> cartItems) {
        // Kiểm tra xem adapter đã được khởi tạo hay chưa
        if (gioHangAdapter == null) {
            gioHangAdapter = new adapter_gio_hang(cartItems);
            binding.rcvGioHang.setAdapter(gioHangAdapter);
            binding.rcvGioHang.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            // Nếu adapter đã tồn tại, cập nhật danh sách sản phẩm
            gioHangAdapter.setCartItems(cartItems);
            gioHangAdapter.notifyDataSetChanged();
        }
    }
}