package fpoly.truongtqph41980.petshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.truongtqph41980.petshop.Dao.SanPhamDao;
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
