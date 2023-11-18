package fpoly.truongtqph41980.petshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.databinding.ItemGioHangBinding;

public class adapter_gio_hang extends RecyclerView.Adapter<adapter_gio_hang.ViewHolder> {

    private ArrayList<SanPham> cartItemList;

    public adapter_gio_hang(ArrayList<SanPham> cartItemList) {
        this.cartItemList = cartItemList;
    }


    // Thêm phương thức này để cập nhật danh sách sản phẩm trong giỏ hàng
    public void setCartItems(ArrayList<SanPham> cartItems) {
        this.cartItemList = cartItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGioHangBinding binding = ItemGioHangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham cartItem = cartItemList.get(position);
        holder.binding.txttensp.setText(cartItem.getTensanpham());
        holder.binding.txtgia.setText(String.valueOf(cartItem.getGia()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemGioHangBinding binding;

        public ViewHolder(ItemGioHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
