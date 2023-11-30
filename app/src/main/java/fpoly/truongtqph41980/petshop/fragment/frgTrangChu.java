package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.Slideiten;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_slide;
import fpoly.truongtqph41980.petshop.adapter.adapter_sp_namngang;
import fpoly.truongtqph41980.petshop.adapter.adapter_trangchu;
import fpoly.truongtqph41980.petshop.databinding.DialogChiTietSanPhamBinding;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgTrangChuBinding;


public class frgTrangChu extends Fragment {
    View view;
    FragmentFrgTrangChuBinding binding;
    ArrayList<SanPham> list;
    SanPhamDao dao;
    adapter_trangchu adapter;
    ArrayList<SanPham> listdem;
    private List<Slideiten> slidelist;
    private Handler slideHanlder = new Handler(Looper.getMainLooper());
    private SharedViewModel sharedViewModel;
    private adapter_gio_hang gioHangAdapter;
    private GioHangDao gioHangDao;
    private boolean hasMatchingProducts = true; // Thêm biến boolean
    public frgTrangChu() {
        // Required empty public constructor
    }
    private adapter_sp_namngang adapteranh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgTrangChuBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        view = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("NGUOIDUNG", Context.MODE_PRIVATE);
        String hoten = preferences.getString("hoten", "");
        binding.txttieuDe.setText("Hi " + hoten + ".");
        slidelist = new ArrayList<>(); // Khởi tạo slidelist trước khi sử dụng
        slidelist.add(new Slideiten(R.drawable.anh10));
        slidelist.add(new Slideiten(R.drawable.anh11));
        slidelist.add(new Slideiten(R.drawable.anh12));
        slidelist.add(new Slideiten(R.drawable.anh6));
        slidelist.add(new Slideiten(R.drawable.anh8));
        slidelist.add(new Slideiten(R.drawable.anh9));
        binding.viewpage.setAdapter(new adapter_slide(slidelist, binding.viewpage));
        binding.chamduoi.setViewPager(binding.viewpage);
        //cài đặt thuộc tính viewpager 2
        binding.viewpage.setClipToPadding(false);
        binding.viewpage.setClipChildren(false);
        binding.viewpage.setOffscreenPageLimit(3);///nhìn đc 3 ảnh :2 ảnh 2 bên và một ảnh ở giữa
        binding.viewpage.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        binding.viewpage.setPageTransformer(compositePageTransformer);
        binding.viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHanlder.removeCallbacks(sildeRunnable);
                slideHanlder.postDelayed(sildeRunnable, 3000);
                // Kiểm tra nếu đang ở vị trí cuối cùng
//                if (position == slidelist.size() - 1) {
//                    // Post runnable để tự động cuộn về vị trí đầu tiên
//                    slideHanlder.postDelayed(sildeRunnable, 3000);
//                } else {
//                    // Nếu không phải vị trí cuối cùng, hủy runnable
//                    slideHanlder.removeCallbacks(sildeRunnable);
//                }
            }
        });
        dao = new SanPhamDao(getContext());
        list = dao.getsanphamall();
        listdem = dao.getsanphamall();
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rcvNamngang.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        binding.rcvtrangchu.setLayoutManager(gridLayoutManager);
        adapteranh = new adapter_sp_namngang(list,getContext());
        adapter = new adapter_trangchu(list, getContext());
        binding.rcvtrangchu.setAdapter(adapter);
        binding.rcvNamngang.setAdapter(adapteranh);

        binding.edtimKiem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    binding.viewpage.setVisibility(View.GONE);
                    binding.chamduoi.setVisibility(View.GONE);
                }
            }
        });
        binding.edtimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase(); // Chuyển đổi sang chữ thường

                if (searchText.isEmpty()) {
                    hasMatchingProducts = true;
                    binding.viewpage.setVisibility(View.GONE);
                    binding.rcvtrangchu.setVisibility(View.VISIBLE);
                    binding.chamduoi.setVisibility(View.GONE);
                    binding.tenkoquantrong.setText("Sản phẩm ");
//                    binding.nen.setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(listdem);
                    adapter.notifyDataSetChanged();
                } else {
                    binding.viewpage.setVisibility(View.GONE);
                    binding.chamduoi.setVisibility(View.GONE);
                    binding.tenkoquantrong.setText("Sản phẩm không có trong giỏ hàng");
//                    binding.nen.setVisibility(View.VISIBLE);
                    binding.rcvtrangchu.setVisibility(View.VISIBLE);
                    list.clear();
                    for (SanPham sp : listdem) {
                        if (sp.getTensanpham().toLowerCase().contains(searchText)) {
                            list.add(sp);
                        }
                    }
                    if (list.isEmpty()) {
                        hasMatchingProducts = false;
                    } else {
                        hasMatchingProducts = true;
                    }
                    adapter.notifyDataSetChanged();
                }
                updateText();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter.notifyDataSetChanged();
        gioHangDao = new GioHangDao(getContext());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        gioHangAdapter = new adapter_gio_hang(getContext(), new ArrayList<>());
        adapter.setOnAddToCartClickListenerTrangChu(new adapter_trangchu.OnAddToCartClickListenerTrangChu() {
            @Override
            public void onAddToCartClick(SanPham sanPham) {
                addToCart(sanPham);
                Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemClick(new adapter_trangchu.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                showDialogChiTietSanPham(adapter.getViTriSp(position));
            }
        });
        return view;
    }

    private Runnable sildeRunnable = new Runnable() {
        @Override
        public void run() {
//            binding.viewpage.setCurrentItem(binding.viewpage.getCurrentItem() + 1);
            int vitri = binding.viewpage.getCurrentItem();
            if (vitri == slidelist.size() - 1) {
                binding.viewpage.setCurrentItem(0);
            } else {
                binding.viewpage.setCurrentItem(vitri + 1);
            }
        }
    };

    @Override
    public void onPause() {
        //khi thoat ra ngoai man home
        super.onPause();
        slideHanlder.removeCallbacks(sildeRunnable);
    }

    @Override
    public void onResume() {
        //khi quay tro lai man home
        super.onResume();
        slideHanlder.postDelayed(sildeRunnable, 3000);
    }
    private void addToCart(SanPham sanPham) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);

        int mand = sharedPreferences.getInt("mataikhoan", 0);
        if (!sharedViewModel.isProductInCart(sanPham.getMasanpham())) {
            sharedViewModel.setMasp(sanPham.getMasanpham());
            sharedViewModel.setAddToCartClicked(true);
            sharedViewModel.addProductToCart(sanPham.getMasanpham());
            sharedViewModel.setQuantityToAdd(1);

            gioHangDao.insertGioHang(new GioHang(sanPham.getMasanpham(), mand, 1));

        } else {

            GioHang hang = gioHangDao.getGioHangByMasp(sanPham.getMasanpham(),mand);
            if (hang != null) {
                hang.setSoLuongMua(hang.getSoLuongMua() + 1);
                gioHangDao.updateGioHang(hang);
            } else {
                GioHang newCartItem = new GioHang(sanPham.getMasanpham(), mand, 1);
                gioHangDao.insertGioHang(newCartItem);
            }

            gioHangAdapter.notifyDataSetChanged();

        }
        ArrayList<GioHang> updatedCartList = gioHangDao.getDSGioHang();
        gioHangAdapter.updateCartList(updatedCartList);
        gioHangAdapter.notifyDataSetChanged();

    }
    private void updateText() {
        if (!hasMatchingProducts) {
            binding.tenkoquantrong.setText("Sản phẩm không có trong giỏ hàng.");
            binding.tenkoquantrong.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.mau_hong));
            binding.nen.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.mau_hong));

        } else {
            binding.tenkoquantrong.setText("Sản phẩm ");

        }
    }

    private void showDialogChiTietSanPham(SanPham sanPham){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogChiTietSanPhamBinding chiTietSanPhamBinding = DialogChiTietSanPhamBinding.inflate(getLayoutInflater());
        dialog.setContentView(chiTietSanPhamBinding.getRoot());

        if (sanPham != null) {
            chiTietSanPhamBinding.txtMaSanPham.setText("Mã: " + String.valueOf(sanPham.getMasanpham()));
            chiTietSanPhamBinding.txtTenSanPham.setText("Tên:" + sanPham.getTensanpham());
            chiTietSanPhamBinding.txtGiaSanPham.setText("Giá: " + String.valueOf(sanPham.getGia()));
            chiTietSanPhamBinding.txtLoaiSanPham.setText("Loại sản phẩm: "+ sanPham.getTenloaisanpham());
            chiTietSanPhamBinding.txtSoLuotBan.setText("Số lượt bán: 200");
            chiTietSanPhamBinding.txtMoTa.setText("Mô tả: " + sanPham.getMota());


        }
        chiTietSanPhamBinding.btnDongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        chiTietSanPhamBinding.btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(sanPham);
//                Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Đã cập nhật giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}