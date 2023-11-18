package fpoly.truongtqph41980.petshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.SanPhamCT;
import fpoly.truongtqph41980.petshop.Interface.OnAddToCartListener;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
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
    private OnAddToCartListener addToCartListener;
    private viewmd cartViewModel;

    // Thiết lập listener
    public void setOnAddToCartListener(OnAddToCartListener listener) {
        this.addToCartListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGianHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();
        spDao = new SanPhamDao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvGianHang.setLayoutManager(layoutManager);
        list = spDao.getsanphamall();
        adapterGianHang = new adapter_gian_hang(getContext(),list);
        binding.rcvGianHang.setAdapter(adapterGianHang);
        cartViewModel = new ViewModelProvider(requireActivity()).get(viewmd.class);
        adapterGianHang.setOnAddToCartClickListener(new adapter_gian_hang.OnAddToCartClickListener() {
            @Override
            public void onAddToCartClick(SanPham sanPham) {
                if (addToCartListener != null) {
                    addToCartListener.onAddToCart(sanPham);
                    cartViewModel.addToCart(sanPham);
                }
            }
        });
        adapterGianHang.setOnItemClickListener(new adapter_gian_hang.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SanPham selectedSanPham = list.get(position);
                Intent intent = new Intent(getContext(), SanPhamCT.class);
                intent.putExtra("sanPham", selectedSanPham);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return gView;
    }


}