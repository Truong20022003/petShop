package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.databinding.DialogBottomsheetSapxepBinding;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGianHangBinding;


public class frgGianHang extends Fragment {

    public frgGianHang() {
        // Required empty public constructor
    }

    FragmentFrgGianHangBinding binding;
    View gView;
    private ArrayList<SanPham> list = new ArrayList<>();
    SanPhamDao spDao;
    adapter_gian_hang adapterGianHang;

    private adapter_gio_hang gioHangAdapter;
    private GioHangDao gioHangDao;
    private ArrayList<GioHang> gioHangArrayList = new ArrayList<>();
    // Thiết lập listener


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGianHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();

        spDao = new SanPhamDao(getActivity());
        gioHangDao = new GioHangDao(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rcvGianHang.setLayoutManager(layoutManager);
        list = spDao.getsanphamall();


        adapterGianHang = new adapter_gian_hang(getActivity(), list);
        binding.rcvGianHang.setAdapter(adapterGianHang);
        gioHangAdapter = new adapter_gio_hang(getActivity(), gioHangArrayList);
        binding.btnSapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSapXep();
            }
        });
        adapterGianHang.setOnAddToCartClickListener(sanPham -> themVaoGio(sanPham));

        adapterGianHang.setOnItemClickListener(position -> {
            // truyền mã đơn hàng được click để qua màn hình đơn hàng chi tiết gọi phương thức lấy ra đơn chi tiết bằng mã đơn hàng này
            Bundle bundle = new Bundle();
            bundle.putInt("maSanPham", list.get(position).getMasanpham());
            bundle.putString("tenLoaiSP", list.get(position).getTenloaisanpham());
            frgSanPhamChiTiet frgSanPhamChiTiet = new frgSanPhamChiTiet();
            frgSanPhamChiTiet.setArguments(bundle);

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frameLayoutMain, frgSanPhamChiTiet);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        // Inflate the layout for this fragment
        return gView;
    }

    //Phương thức để lấy ra tồn kho của sản phẩm bằng mã của sản phẩm đấy
    private int getSoLuongSp(int maSanPham) {
        for (SanPham sanPham : list) {
            if (sanPham.getMasanpham() == maSanPham) {
                return sanPham.getSoluong();
            }
        }
        return 0; // Trả về 0 nếu không tìm thấy sản phẩm
    }

    //Phương thức thêm vào giỏ
    private void themVaoGio(SanPham sanPham) {

        // Lấy ra nhưng thông tin cần thiết của người dùng để thực hiện công việc
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("mataikhoan", 0);
        int maSanPham = sanPham.getMasanpham();
        int slSanPham = getSoLuongSp(maSanPham);

        //Gọi phương thức lấy ra danh sách giỏ hàng của từng người dùng
        gioHangArrayList = gioHangDao.getDanhSachGioHangByMaNguoiDung(mand);

        boolean isProductInCart = false;

        // duyệt qua danh sách giỏ hàng
        for (GioHang gioHang : gioHangArrayList) {

            //nếu sản phẩm được thêm đã có trong giỏ hàng thì thực hiện thêm số lượng
            if (gioHang.getMaSanPham() == maSanPham) {
                isProductInCart = true;
                // nếu số lượng trong giỏ hàng của sản phẩm được thêm ít hơn số lượng sản phẩm tồn kho thì cho phép +1 vào giỏ hàng
                if (gioHang.getSoLuongMua() < slSanPham) {
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    gioHangDao.updateGioHang(gioHang);
                    Snackbar.make(getView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        // nếu sản phẩm được thêm vào giỏ chưa có trong giỏ thì thực hiện thêm giỏ hàng
        if (!isProductInCart) {
            if (slSanPham > 0) {
                gioHangDao.insertGioHang(new GioHang(maSanPham, mand, 1));
            } else {
                Toast.makeText(getActivity(), "Sản phẩm hết hàng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogSapXep() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogBottomsheetSapxepBinding layoutBinding = DialogBottomsheetSapxepBinding.inflate(getLayoutInflater());
        dialog.setContentView(layoutBinding.getRoot());
        layoutBinding.radioGroupProduct.setOnCheckedChangeListener((new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbAZProduct) {
                    Toast.makeText(getContext(), "Sắp xếp từ A - Z", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<SanPham>() {
                                @Override
                                public int compare(SanPham sanPham, SanPham t1) {
                                    return sanPham.getTensanpham().compareToIgnoreCase(t1.getTensanpham());
                                }
                            });
                            adapterGianHang.notifyDataSetChanged();

                } else if (i == R.id.rbZAProduct) {
                    Toast.makeText(getContext(), "Sắp xếp từ Z - A", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham t1) {
                            return t1.getTensanpham().compareToIgnoreCase(sanPham.getTensanpham());
                        }
                    });
                    adapterGianHang.notifyDataSetChanged();

                } else if (i == R.id.rbGiaTangDan) {
                    Toast.makeText(getContext(), "Sắp xếp giá tăng dần", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham t1) {
                            return Integer.compare(sanPham.getGia(),t1.getGia());
                        }
                    });
                    adapterGianHang.notifyDataSetChanged();
                } else if (i == R.id.rbGiaGiamDan) {
                    Toast.makeText(getContext(), "Sắp xếp giá giảm dần", Toast.LENGTH_SHORT).show();
                    Collections.sort(list, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham t1) {
                            return Integer.compare(t1.getGia(),sanPham.getGia());
                        }
                    });
                    adapterGianHang.notifyDataSetChanged();
                }
            }
        }));

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}