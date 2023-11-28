package fpoly.truongtqph41980.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.databinding.DialogXoaDonHangBinding;
import fpoly.truongtqph41980.petshop.databinding.DialogXoaNguoiDungBinding;
import fpoly.truongtqph41980.petshop.databinding.ItemQlDonHangBinding;

public class adapter_don_hang extends RecyclerView.Adapter<adapter_don_hang.Viewholder> {
    protected ArrayList<DonHang> list;
    protected DonHangDao dao;
    private Context context;

    public adapter_don_hang(ArrayList<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangDao(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQlDonHangBinding binding = ItemQlDonHangBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        DonHang donHang = list.get(position);
        holder.binding.txtMdonhang.setText("Mã đơn hàng: " +String.valueOf(donHang.getMaDonHang()));
        holder.binding.txtMnguoidung.setText("Mã người dung: " +String.valueOf(donHang.getMaTaiKhoan()));
        holder.binding.txtDHTennguoidung.setText("Tên người dùng: " +donHang.getTenTaiKhoan());
        holder.binding.txtNgayDat.setText("Ngày đặt hàng: " +donHang.getNgayDatHang());
        holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
        holder.binding.txtTongTien.setText("Tổng tiền: " +String.valueOf(donHang.getTongTien()));
//        holder.binding.txtMaSanPham.setText("Mã sản phẩm: "+String.valueOf(donHang.getMasanpham()));
//        holder.binding.txtTenSanPham.setText("Tên sản phẩm: " + donHang.getTensanpham());
        holder.binding.btnXoaDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                DialogXoaDonHangBinding dialogXoaDonHangBinding = DialogXoaDonHangBinding.inflate(inflater);
                builder.setView(dialogXoaDonHangBinding.getRoot());

                Dialog dialog = builder.create();
                dialog.show();
                dialogXoaDonHangBinding.btnOutXoaDonHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialogXoaDonHangBinding.btnConfilmXoaDonHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dao.xoaDonHang(donHang)){
                            list.clear();
                            list.addAll(dao.getDsDonHang());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ItemQlDonHangBinding binding;
        public Viewholder( ItemQlDonHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
