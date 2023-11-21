package fpoly.truongtqph41980.petshop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_trangchu;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgTrangChuBinding;


public class frgTrangChu extends Fragment {
    View view;
    FragmentFrgTrangChuBinding binding;
    ArrayList<SanPham> list;
    SanPhamDao dao;
    adapter_trangchu adapter;

    public frgTrangChu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgTrangChuBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        view = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("NGUOIDUNG", Context.MODE_PRIVATE);
        String hoten=preferences.getString("hoten","");
        binding.txttieuDe.setText("Hi "+hoten+".");
        dao = new SanPhamDao(getContext());
        list = dao.trangchugetall();
        StaggeredGridLayoutManager gridLayoutManager = new  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rcvtrangchu.setLayoutManager(gridLayoutManager);
        adapter = new adapter_trangchu(list, getContext());
        binding.rcvtrangchu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}