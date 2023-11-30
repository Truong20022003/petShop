package fpoly.truongtqph41980.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DanhGiaDao;
import fpoly.truongtqph41980.petshop.Dao.DonHangChiTietDao;
import fpoly.truongtqph41980.petshop.Model.DanhGia;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.databinding.DialogDanhGiaBinding;
import fpoly.truongtqph41980.petshop.databinding.ItemDonHangChiTietBinding;

public class adapter_don_hang_chi_tiet extends RecyclerView.Adapter<adapter_don_hang_chi_tiet.ViewHolder> {
    private ArrayList<DonHangChiTiet> list;
    private Context context;
    private DonHangChiTietDao dao;
    private DanhGiaDao dao2;

    public adapter_don_hang_chi_tiet(ArrayList<DonHangChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangChiTietDao(context);
        dao2=new DanhGiaDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDonHangChiTietBinding binding = ItemDonHangChiTietBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtDonGia.setText("Giá: " + String.valueOf(list.get(position).getDonGia()));
        holder.binding.txtmaChiTietDon.setText("Mã chi tiết đơn: " + String.valueOf(list.get(position).getMaChiTietDonHang()));
        holder.binding.txtMaDonHang.setText("Mã đơn hàng: " + String.valueOf(list.get(position).getMaDonHang()));
        holder.binding.txtMaSanPham.setText("Mã sản phẩm: " + String.valueOf(list.get(position).getMaSanPham()));
        holder.binding.txtThanhTien.setText("Thành tiền: " + String.valueOf(list.get(position).getThanhTien()));
        holder.binding.txtSoLuong.setText("Số lượng: " + String.valueOf(list.get(position).getSoLuong()));
        holder.binding.txttensanpham.setText("Tên sản phẩm: " + list.get(position).getTenSanPham());
        Picasso.get().load(list.get(position).getAnhsanpham()).into(holder.binding.imgAnhsp);
        DonHangChiTiet ct = list.get(position);
//        holder.binding.btnthemdanhgia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
//                DialogDanhGiaBinding dialogDanhGiaBinding = DialogDanhGiaBinding.inflate(layoutInflater);
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(dialogDanhGiaBinding.getRoot());
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                dialogDanhGiaBinding.btnthembinhluan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String danhgia = dialogDanhGiaBinding.eddanhgia.getText().toString();
//                        String nhanxet = dialogDanhGiaBinding.ednhanxet.getText().toString();
//                        LocalDate currentDate = LocalDate.now();
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                        String ngayHienTai = currentDate.format(formatter);
//                        if (danhgia.isEmpty()) {
//                            dialogDanhGiaBinding.eddanhgia.setError("Vui lòng không để trống");
//                        } else {
//                            dialogDanhGiaBinding.eddanhgia.setError(null);
//                        }
//                        if (nhanxet.isEmpty()) {
//                            dialogDanhGiaBinding.ednhanxet.setError("Vui lòng không để trống");
//                        } else {
//                            dialogDanhGiaBinding.ednhanxet.setError(null);
//                        }
//                        if (dialogDanhGiaBinding.eddanhgia.getError() == null && dialogDanhGiaBinding.ednhanxet.getError() == null) {
//                            boolean check=dao2.addDanhGia(ct.getMaSanPham(), danhgia, nhanxet, ngayHienTai);
//                            if (check) {
//                               ArrayList<DanhGia>list1=new ArrayList<>();
//                                list1 =dao2.getDanhGiaByMaSanPham(ct.getMaSanPham());
//                                Toast.makeText(context, "Thêm  thành công", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            } else {
//                                Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(context, "truong lol", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDonHangChiTietBinding binding;

        public ViewHolder(ItemDonHangChiTietBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
