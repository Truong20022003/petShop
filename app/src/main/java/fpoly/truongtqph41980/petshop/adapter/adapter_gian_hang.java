package fpoly.truongtqph41980.petshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Model.GioHang;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.Utlis.Utils;
import fpoly.truongtqph41980.petshop.databinding.ItemGianHangBinding;
import fpoly.truongtqph41980.petshop.databinding.ItemSanphamBinding;

public class adapter_gian_hang extends RecyclerView.Adapter<adapter_gian_hang.ViewHolder> {
    private final Context context;
    private final ArrayList<SanPham> list;
    SanPhamDao dao;

    public adapter_gian_hang(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        dao = new SanPhamDao(context);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Biến để lưu trữ listener
    private OnItemClickListener mListener;

    // Phương thức để thiết lập listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //nút thêm vào giỏ hàng
    public interface OnAddToCartClickListener {
        void onAddToCartClick(SanPham sanPham);
    }

    private OnAddToCartClickListener mAddToCartClickListener;

    public void setOnAddToCartClickListener(OnAddToCartClickListener listener) {
        mAddToCartClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGianHangBinding binding = ItemGianHangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txttenHat.setText("Tên sp:" + list.get(position).getTensanpham());
        holder.binding.txtgiaHat.setText("Giá sp:" + String.valueOf(list.get(position).getGia()));
        holder.binding.txttrangThaiSanPham.setText("Số lượt bán: 200");
        holder.binding.txtgiaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddToCartClickListener != null) {
                    mAddToCartClickListener.onAddToCartClick(list.get(holder.getAdapterPosition()));

                }
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemGianHangBinding binding;

        public ViewHolder(ItemGianHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
