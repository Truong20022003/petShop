package fpoly.truongtqph41980.petshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgTrangChuBinding;


public class frgTrangChu extends Fragment {


    public frgTrangChu() {
        // Required empty public constructor
    }

    FragmentFrgTrangChuBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgTrangChuBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}