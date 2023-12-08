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
import fpoly.truongtqph41980.petshop.Interface.OnItemClick;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.databinding.DialogXoaDonHangBinding;
import fpoly.truongtqph41980.petshop.databinding.ItemQlDonHangBinding;
import fpoly.truongtqph41980.petshop.databinding.ItemRcvLichSuDonHangBinding;

public class Adapter_lich_su_don_hang extends RecyclerView.Adapter<Adapter_lich_su_don_hang.ViewHolder> {
    protected ArrayList<DonHang> list;
    protected DonHangDao dao;
    private Context context;

    public Adapter_lich_su_don_hang(ArrayList<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangDao(context);
    }

    private OnItemClick mListener;

    public void setOnItemClick(OnItemClick listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvLichSuDonHangBinding binding = ItemRcvLichSuDonHangBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.binding.txtMdonhang.setText("Mã đơn hàng: " +String.valueOf(donHang.getMaDonHang()));
        holder.binding.txtMnguoidung.setText("Mã người dung: " +String.valueOf(donHang.getMaTaiKhoan()));
        holder.binding.txtDHTennguoidung.setText("Tên người dùng: " +donHang.getTenTaiKhoan());
        holder.binding.txtNgayDat.setText("Ngày đặt hàng: " +donHang.getNgayDatHang());
        holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
        holder.binding.txtTongTien.setText("Tổng tiền: " +String.valueOf(donHang.getTongTien()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ItemRcvLichSuDonHangBinding binding;
        public ViewHolder(ItemRcvLichSuDonHangBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
