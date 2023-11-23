package fpoly.truongtqph41980.petshop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.Slideiten;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_slide;
import fpoly.truongtqph41980.petshop.adapter.adapter_trangchu;
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
        list = dao.trangchugetall();
        listdem = dao.trangchugetall();
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rcvtrangchu.setLayoutManager(gridLayoutManager);
        adapter = new adapter_trangchu(list, getContext());
        binding.rcvtrangchu.setAdapter(adapter);
        binding.edtimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase(); // Chuyển đổi sang chữ thường

                if (searchText.isEmpty()) {
                    // Nếu không có ký tự tìm kiếm, hiển thị lại slide và RecyclerView ban đầu
                    binding.viewpage.setVisibility(View.VISIBLE);
                    binding.rcvtrangchu.setVisibility(View.VISIBLE);
                    binding.chamduoi.setVisibility(View.VISIBLE);
                    binding.tenkoquantrong.setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(listdem);
                    adapter.notifyDataSetChanged();
                } else {
                    // Nếu có ký tự tìm kiếm, ẩn slide và hiển thị RecyclerView với kết quả tìm kiếm
                    binding.viewpage.setVisibility(View.GONE);
                    binding.chamduoi.setVisibility(View.GONE);
                    binding.tenkoquantrong.setText("Sản phẩm không có trong giỏ hàng");
                    binding.rcvtrangchu.setVisibility(View.VISIBLE);
                    list.clear();
                    for (SanPham sp : listdem) {
                        // Chuyển đổi tên sản phẩm sang chữ thường và kiểm tra xem có chứa ký tự tìm kiếm không
                        if (sp.getTensanpham().toLowerCase().contains(searchText)) {
                            list.add(sp);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        adapter.notifyDataSetChanged();

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
}