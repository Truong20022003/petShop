package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DanhGiaDao;
import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.DanhGia;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_danh_gia;
import fpoly.truongtqph41980.petshop.adapter.adapter_san_pham;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgSanPhamChiTietBinding;


public class frgSanPhamChiTiet extends Fragment {


    public frgSanPhamChiTiet() {
        // Required empty public constructor
    }
FragmentFrgSanPhamChiTietBinding binding;
    SanPham sanPham;
    SanPhamDao dao;
    SharedViewModel sharedViewModel;
    GioHangDao gioHangDao;
    private ArrayList<SanPham> list = new ArrayList<>();
    adapter_danh_gia adapter;
    ArrayList<DanhGia>list1=new ArrayList<>();
    DanhGiaDao danhGiaDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgSanPhamChiTietBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        dao = new SanPhamDao(getContext());
        gioHangDao = new GioHangDao(getActivity());
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        list = dao.getsanphamall();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.rcvDanhGia.setLayoutManager(layoutManager);
        Bundle bundle = getArguments();
        if (bundle!= null){
            int maSanPham = bundle.getInt("maSanPham");
            String tenLoai = bundle.getString("tenLoaiSP");
            sanPham = dao.getSanPhamById(maSanPham);

            binding.txtMaSp.setText("Mã sản phẩm: "+ String.valueOf(maSanPham));
            binding.txttensp.setText("Tên: "+ sanPham.getTensanpham());
            binding.txtGiaSp.setText("Giá: "+ String.valueOf(sanPham.getGia()));
            binding.txtLoaisp.setText("Loại: "+ tenLoai);
            binding.txtSoluotban.setText("số lượt bán: 200");
            binding.txtSoluong.setText("Số lượng: " + String.valueOf(sanPham.getSoluong()));
            binding.txtMotaChiTiet.setText("Mô tả: " + sanPham.getMota());
            Picasso.get().load(sanPham.getAnhSanPham()).into(binding.imganhsp);
            danhGiaDao=new DanhGiaDao(getContext());
            list1=danhGiaDao.getDanhGiaByMaSanPham(maSanPham);

            adapter = new adapter_danh_gia(list1, getContext());
            binding.rcvDanhGia.setAdapter(adapter);
            binding.btnThemCtVaoGio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCart(sanPham);
                }
            });

            if (sanPham.getSoluong() == 0) {
                binding.btnThemCtVaoGio.setVisibility(View.GONE);
                binding.txtHetHang.setVisibility(View.VISIBLE);
            } else {
                binding.btnThemCtVaoGio.setVisibility(View.VISIBLE);
                binding.txtHetHang.setVisibility(View.GONE);
            }

        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgGianHang frgGianHang = new frgGianHang();


                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, frgGianHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
    private int getSoLuongSp(int maSanPham) {
        for (SanPham sanPham : list) {
            if (sanPham.getMasanpham() == maSanPham) {
                return sanPham.getSoluong();
            }
        }
        return 0; // Trả về 0 nếu không tìm thấy sản phẩm
    }
    private void addToCart(SanPham sanPham) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("mataikhoan", 0);
        int maSanPham = sanPham.getMasanpham();
        int slSanPham = getSoLuongSp(maSanPham);

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        if (!sharedViewModel.isProductInCart(maSanPham)) {
            if (slSanPham > 0) {
                // Nếu số lượng sản phẩm > 0, thêm sản phẩm vào giỏ hàng với số lượng là 1
                sharedViewModel.setMasp(maSanPham);
                sharedViewModel.setAddToCartClicked(true);
                sharedViewModel.addProductToCart(maSanPham);
//                sharedViewModel.setQuantityToAdd(1);
                gioHangDao.insertGioHang(new GioHang(maSanPham, mand, 1));
                Snackbar.make(getView(), "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
            }
        } else {
            GioHang gioHang = gioHangDao.getGioHangByMasp(maSanPham, mand);
            if (gioHang != null) {
                if (gioHang.getSoLuongMua() < slSanPham) {
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    gioHangDao.updateGioHang(gioHang);
                    Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}