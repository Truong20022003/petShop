package fpoly.truongtqph41980.petshop.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangChiTietDao;
import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Interface.OnItemClick;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgQuanLyDonHangBinding;


public class frgQuanLyDonHang extends Fragment {


    public frgQuanLyDonHang() {
        // Required empty public constructor
    }

    FragmentFrgQuanLyDonHangBinding binding;
    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDao dao;
    private adapter_don_hang adapterDonHang;
    DonHangChiTietDao chiTietDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgQuanLyDonHangBinding.inflate(inflater, container, false);
        dao = new DonHangDao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvDonHang.setLayoutManager(layoutManager);
        list = dao.getDsDonHang();
        adapterDonHang = new adapter_don_hang(list, getContext());
        binding.rcvDonHang.setAdapter(adapterDonHang);
        chiTietDao = new DonHangChiTietDao(getContext());
        adapterDonHang.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang", maDonHang);
                frgDonHangChiTiet frgDonHangChiTiet = new frgDonHangChiTiet();
                frgDonHangChiTiet.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frameLayoutMain, frgDonHangChiTiet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}