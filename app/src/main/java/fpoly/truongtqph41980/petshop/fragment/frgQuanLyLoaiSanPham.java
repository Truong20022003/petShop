package fpoly.truongtqph41980.petshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.truongtqph41980.petshop.R;


public class frgQuanLyLoaiSanPham extends Fragment {



    public frgQuanLyLoaiSanPham() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_quan_ly_loai_san_pham, container, false);
    }
}