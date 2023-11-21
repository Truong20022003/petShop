package fpoly.truongtqph41980.petshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.NguoiDungDao;
import fpoly.truongtqph41980.petshop.Model.NguoiDung;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_nguoi_dung;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgQuanLyNguoiDungBinding;


public class frgQuanLyNguoiDung extends Fragment {


    private FragmentFrgQuanLyNguoiDungBinding binding;
    private View vView;
    private NguoiDungDao nguoiDungDao;
    private adapter_nguoi_dung adapterNguoiDung;
    private ArrayList<NguoiDung> list = new ArrayList<>();

    public frgQuanLyNguoiDung() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgQuanLyNguoiDungBinding.inflate(inflater, container, false);
        vView = binding.getRoot();
        nguoiDungDao = new NguoiDungDao(getContext());
        list = nguoiDungDao.getAllNguoiDung();
        RecyclerView rcv = binding.rcvNguoiDung;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapterNguoiDung = new adapter_nguoi_dung(list, getContext());
        rcv.setAdapter(adapterNguoiDung);
        // Inflate the layout for this fragment
        binding.flNguoiDung.setOnClickListener(view -> {
            frgAddNguoiDung frgAddNguoiDung = new frgAddNguoiDung();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutMain, frgAddNguoiDung);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String tenDangNhap = bundle.getString("tenDangNhap");
            String matKhau = bundle.getString("matKhau");
            String hoTen = bundle.getString("hoTen");
            String email = bundle.getString("email");
            String soDienThoai = bundle.getString("soDienThoai");
            String diaChi = bundle.getString("diaChi");
            int tien = bundle.getInt("soTien");
            String loaiTaiKhoan = bundle.getString("loaiTaiKhoan");
            NguoiDung nd = new NguoiDung(tenDangNhap, matKhau, hoTen, email, soDienThoai, diaChi, tien, loaiTaiKhoan);
            if (nguoiDungDao.checkDangKy(nd)) {
                list.clear();
                list.addAll(nguoiDungDao.getAllNguoiDung());
                adapterNguoiDung.notifyDataSetChanged();
                Toast.makeText(getContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
            }
        }

        return vView;
    }
}