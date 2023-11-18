package fpoly.truongtqph41980.petshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgSanPhamChiTietBinding;


public class frgSanPhamChiTiet extends Fragment {


    public frgSanPhamChiTiet() {
        // Required empty public constructor
    }

    FragmentFrgSanPhamChiTietBinding binding;
    View sView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgSanPhamChiTietBinding.inflate(inflater,container,false);
        sView = binding.getRoot();
        // Inflate the layout for this fragment
        return sView;
    }
}