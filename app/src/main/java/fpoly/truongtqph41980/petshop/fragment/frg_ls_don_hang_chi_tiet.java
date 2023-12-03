package fpoly.truongtqph41980.petshop.fragment;

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
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang_chi_tiet;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgLsDonHangChiTietBinding;


public class frg_ls_don_hang_chi_tiet extends Fragment {

    public frg_ls_don_hang_chi_tiet() {
        // Required empty public constructor
    }
FragmentFrgLsDonHangChiTietBinding binding;
    private ArrayList<DonHangChiTiet> list = new ArrayList<>();

    private adapter_don_hang_chi_tiet adapterDonHangChiTiet;
    DonHangChiTietDao chiTietDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgLsDonHangChiTietBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvLichSuDonHang.setLayoutManager(layoutManager);
        chiTietDao = new DonHangChiTietDao(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang", 0);
            Log.d("Mã đơn hàng", String.valueOf(maDonHang));
            if (maDonHang != 0) {
                list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterDonHangChiTiet = new adapter_don_hang_chi_tiet(list, getContext());
                binding.rcvLichSuDonHang.setAdapter(adapterDonHangChiTiet);

            }
        }

        binding.imgBackLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frg_lich_su_don_hang frgLichSuDonHang=new frg_lich_su_don_hang();//fragment được chuyển đến sau khi ấn
                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain,frgLichSuDonHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}