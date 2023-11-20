package fpoly.truongtqph41980.petshop.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.GioHangDao;
import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.databinding.ItemGioHangBinding;

public class adapter_gio_hang extends RecyclerView.Adapter<adapter_gio_hang.ViewHolder> {

    private ArrayList<GioHang> list;
    Context context;
    GioHangDao dao;

    public adapter_gio_hang(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
        dao = new GioHangDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGioHangBinding binding = ItemGioHangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);



        // Hiển thị thông tin sản phẩm
        holder.binding.txtgia.setText(String.valueOf(gioHang.getGiaSanPham()));
        holder.binding.txtsoluong.setText(String.valueOf(gioHang.getSoLuongMua()));
        holder.binding.txttensp.setText(gioHang.getTenSanPham());

    }

    public void updateCartList(ArrayList<GioHang> updatedList) {
//        this.list = updatedList;
        list.clear();
        list.addAll(updatedList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemGioHangBinding binding;

        public ViewHolder(ItemGioHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
