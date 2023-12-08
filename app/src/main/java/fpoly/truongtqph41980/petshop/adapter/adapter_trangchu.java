package fpoly.truongtqph41980.petshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
import fpoly.truongtqph41980.petshop.Interface.OnAddToCart;
import fpoly.truongtqph41980.petshop.Interface.OnItemClick;
import fpoly.truongtqph41980.petshop.Model.SanPham;
import fpoly.truongtqph41980.petshop.databinding.ItemTrangChuBinding;

public class adapter_trangchu extends RecyclerView.Adapter<adapter_trangchu.ViewHo> {
    private ArrayList<SanPham> list;
    private Context context;
    SanPhamDao dao;


    public adapter_trangchu(ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.context = context;

        dao = new SanPhamDao(context);
    }
    private OnAddToCart mAddToCartClickListener;


    private OnItemClick mListener;
    public void setOnItemClick(OnItemClick listener){
        mListener = listener;
    }
    public SanPham getViTriSp(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }
    public void setOnAddToCartClickListenerTrangChu(OnAddToCart listener) {
        mAddToCartClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrangChuBinding biding = ItemTrangChuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHo(biding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHo holder, int position) {
        holder.biding.txttenSanPham.setText(list.get(position).getTensanpham());
        holder.biding.txtgiasp.setText(String.valueOf(list.get(position).getGia()));
        Picasso.get().load(list.get(position).getAnhSanPham()).into(holder.biding.imgAnhSpTrangChu);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());

                }
            }
        });
        if (list.get(position).getSoluong() == 0) {
            holder.biding.btnmuahang.setVisibility(View.GONE);
        } else {
            holder.biding.btnmuahang.setVisibility(View.VISIBLE);
        }
        holder.biding.btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddToCartClickListener != null) {
                    mAddToCartClickListener.onAddToCartClick(list.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHo extends RecyclerView.ViewHolder {
        ItemTrangChuBinding biding;

        public ViewHo(@NonNull ItemTrangChuBinding biding) {
            super(biding.getRoot());
            this.biding = biding;
        }
    }
}
