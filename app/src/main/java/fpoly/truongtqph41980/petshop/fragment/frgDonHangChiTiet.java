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

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangChiTietDao;
import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang_chi_tiet;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgDonHangChiTietBinding;


public class frgDonHangChiTiet extends Fragment {

    FragmentFrgDonHangChiTietBinding binding;

    public frgDonHangChiTiet() {
        // Required empty public constructor
    }

    private ArrayList<DonHangChiTiet> list = new ArrayList<>();

    private adapter_don_hang_chi_tiet adapterDonHangChiTiet;
    DonHangChiTietDao chiTietDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgDonHangChiTietBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvDonHangChiTiet.setLayoutManager(layoutManager);
        chiTietDao = new DonHangChiTietDao(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang", 0);
            Log.d("Mã đơn hàng", String.valueOf(maDonHang));
            if (maDonHang != 0) {
               list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterDonHangChiTiet = new adapter_don_hang_chi_tiet(list, getContext());
                binding.rcvDonHangChiTiet.setAdapter(adapterDonHangChiTiet);

            }
        }

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgQuanLyDonHang frgQuanLyDonHang=new frgQuanLyDonHang();//fragment được chuyển đến sau khi ấn
                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain,frgQuanLyDonHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}