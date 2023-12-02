package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangChiTietDao;
import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.NguoiDungDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Model.viewmd;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.ViewActivity.Thanh_Toan_Hoa_Don;
import fpoly.truongtqph41980.petshop.Viewmd.SharedViewModel;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gian_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_gio_hang;
import fpoly.truongtqph41980.petshop.adapter.swipe;
import fpoly.truongtqph41980.petshop.databinding.DialogConfilmThanhToanBinding;
import fpoly.truongtqph41980.petshop.databinding.DialogThemSanPhamBinding;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgGioHangBinding;


public class frgGioHang extends Fragment implements adapter_gio_hang.TotalPriceListener {

    private ArrayList<GioHang> list = new ArrayList<>();
    private adapter_gio_hang gioHangAdapter;
    FragmentFrgGioHangBinding binding;
    View gView;

    GioHangDao gioHangDao;

    private DonHangDao donHangDao;

    private adapter_don_hang adapterDonHang;
    private frgQuanLyDonHang frgQuanLyDonHang;
    private ArrayList<DonHang> listDonHang = new ArrayList<>();
    private SharedViewModel sharedViewModel;
    private DonHangChiTietDao chiTietDao;
    private ArrayList<SanPham> sanPhams = new ArrayList<>();
    private SanPhamDao sanPhamDao;

    public frgGioHang() {
    }

    private void displayCart(ArrayList<GioHang> cartList) {
        RecyclerView rcv = binding.rcvGioHang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);

        if (gioHangAdapter == null) {
            gioHangAdapter = new adapter_gio_hang(getContext(),sharedViewModel, cartList);
            rcv.setAdapter(gioHangAdapter);

        } else {
            gioHangAdapter.updateCartList(cartList);
gioHangAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgGioHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();

        RecyclerView rcv = binding.rcvGioHang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        gioHangAdapter = new adapter_gio_hang(getContext(),sharedViewModel, list);
        rcv.setAdapter(gioHangAdapter);
        gioHangDao = new GioHangDao(getActivity());
        ItemTouchHelper sw=new ItemTouchHelper(new swipe(gioHangAdapter));
        sw.attachToRecyclerView(rcv);
        gioHangAdapter.setTotalPriceListener(this);

        chiTietDao = new DonHangChiTietDao(getContext());
        donHangDao = new DonHangDao(getContext());

        sanPhamDao = new SanPhamDao(getContext());

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getMasp().observe(getViewLifecycleOwner(), masp -> {

            if (isAdded() && isVisible()) {
                if (sharedViewModel.getAddToCartClicked().getValue() != null && sharedViewModel.getAddToCartClicked().getValue()) {
                    updateGioHangByMaSp(masp);
//                    sharedViewModel.setAddToCartClicked(true); // Đặt lại trạng thái
                }
            }
        });
        list = gioHangDao.getDSGioHang();
        displayCart(list);

        binding.btnThanhToan.setOnClickListener(view -> {
            showDialogThanhToan();

        });


        return gView;
    }


    public void updateGioHangByMaSp(int masp) {
        if (masp > 0) {
            ArrayList<GioHang> updatedCartList = gioHangDao.getDSGioHang();
            list.clear();
            list.addAll(updatedCartList);

            displayCart(updatedCartList);
            gioHangAdapter.notifyDataSetChanged();
        } else {
        }
    }

    @Override
    public void onTotalPriceUpdated(int totalAmount) {
        if (binding != null && binding.txtTongTienThanhToan != null) {
            binding.txtTongTienThanhToan.setText(String.valueOf(totalAmount));
        }
    }

    private void showDialogThanhToan() {
        LayoutInflater layoutInflater = getLayoutInflater();
        DialogConfilmThanhToanBinding thanhToanBinding = DialogConfilmThanhToanBinding.inflate(layoutInflater);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(thanhToanBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();
        thanhToanBinding.btnThanhToan.setOnClickListener(view -> {
            for (GioHang gioHang : list) {
                if (gioHang.getSoLuong() == 0) {
                    Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenSanPham() + " đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            int totalAmount = Integer.parseInt(binding.txtTongTienThanhToan.getText().toString());
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
            int mand = sharedPreferences.getInt("mataikhoan", 0);
            int tienHienCo = sharedPreferences.getInt("sotien", 0);

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayHienTai = currentDate.format(formatter);

            if (tienHienCo >= totalAmount) {
                int soTienConLai = tienHienCo - totalAmount;
                NguoiDungDao nguoiDungDao = new NguoiDungDao(getContext());
                if (nguoiDungDao.updateSoTien(mand, soTienConLai)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("sotien", soTienConLai);
                    editor.apply();
                    DonHang donHang = new DonHang(mand, ngayHienTai, totalAmount, "Chờ phê duyệt");
                    int orderId = donHangDao.insertDonHang(donHang);
                    if (orderId != 0) {
                        listDonHang.clear();
                        listDonHang.addAll(donHangDao.getDsDonHang());
                        if (totalAmount > 0) {
                            for (GioHang gioHang : list) {
                                if (gioHang.isSelected()) {
                                    SanPhamDao sanPhamDao = new SanPhamDao(getContext());
                                    SanPham sanPham = sanPhamDao.getSanPhamById(gioHang.getMaSanPham());
                                    if (sanPham != null) {
                                        DonHangChiTiet chiTietDonHan = new DonHangChiTiet(orderId, gioHang.getMaSanPham(), gioHang.getSoLuongMua(), sanPham.getGia(), gioHang.getSoLuongMua() * sanPham.getGia());
                                        chiTietDao.insertDonHangChiTiet(chiTietDonHan);
                                    } else {
                                        Toast.makeText(getContext(), "Sản phẩm không tìm thấy trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Cập nhật số lượng sản phẩm sau khi thanh toán thành công
                        for (GioHang gioHang : list) {
                            int newQuantity = gioHang.getSoLuong() - gioHang.getSoLuongMua();
                            if (newQuantity < 0) {
                                Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenSanPham() + "không đủ số lượng trong kho", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            sanPhamDao.updateSlSanPham(gioHang.getMaSanPham(), newQuantity);
                        }
                        for (GioHang selected : list) {
                            if (selected.isSelected()) {
                                gioHangDao.deleteGioHang(selected);
                            }
                        }

                        binding.txtTongTienThanhToan.setText(String.valueOf(0));
                        list = gioHangDao.getDSGioHang();
                        gioHangAdapter.updateCartList(list);
                        displayCart(list);

                        Snackbar.make(getView(), "Thanh toán thành công", Snackbar.LENGTH_SHORT).show();


                        Bundle bundle = new Bundle();
                        bundle.putInt("maDonHang", orderId);

                        frgConfilmThanhToan frgConfilmThanhToan = new frgConfilmThanhToan();
                        frgConfilmThanhToan.setArguments(bundle);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutMain, frgConfilmThanhToan);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thất bại khi thêm đơn hàng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Thất bại khi cập nhật tài khoản!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Số tiền trong tài khoản không đủ!", Toast.LENGTH_SHORT).show();
            }
        });
        thanhToanBinding.btnThoat.setOnClickListener(view -> dialog.dismiss());

    }
}
