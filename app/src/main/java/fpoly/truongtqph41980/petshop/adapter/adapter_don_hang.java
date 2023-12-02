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
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.databinding.DialogUpdateTrangThaiDonhangBinding;
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

    public interface OnItemClick {
        void onItemClick(int position);
    }

    private OnItemClick mListener;

    public void setOnItemClick(OnItemClick listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQlDonHangBinding binding = ItemQlDonHangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        DonHang donHang = list.get(position);
        holder.binding.txtMdonhang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDonHang()));
        holder.binding.txtMnguoidung.setText("Mã người dung: " + String.valueOf(donHang.getMaTaiKhoan()));
        holder.binding.txtDHTennguoidung.setText("Tên người dùng: " + donHang.getTenTaiKhoan());
        holder.binding.txtNgayDat.setText("Ngày đặt hàng: " + donHang.getNgayDatHang());
        holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
        holder.binding.txtTongTien.setText("Tổng tiền: " + String.valueOf(donHang.getTongTien()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());

                }
            }
        });
        holder.binding.btnchinhsuaTrangThai.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            DialogUpdateTrangThaiDonhangBinding dialogUpdateTrangThaiDonhangBinding = DialogUpdateTrangThaiDonhangBinding.inflate(inflater);
            builder.setView(dialogUpdateTrangThaiDonhangBinding.getRoot());
            Dialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.nen_dialog_doan);
            dialogUpdateTrangThaiDonhangBinding.btnxacnhanTrangthai.setOnClickListener(view12 -> {
                String trangthai = dialogUpdateTrangThaiDonhangBinding.txtTrangThai.getText().toString();
                if (trangthai.equals("")) {
                    dialogUpdateTrangThaiDonhangBinding.txtTrangThai.setError("Vui lòng không để trống trạng thái");

                }
                list = dao.getDonHangByMaTaiKhoan(donHang.getMaTaiKhoan());
                donHang.setMaDonHang(donHang.getMaDonHang());
                donHang.setTrangthai(trangthai);
                boolean check = dao.updateDonHang(donHang);
                if (check) {
                    list.clear();
                    list.addAll(dao.getDsDonHang());

                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Thay đổi trang thái thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            dialogUpdateTrangThaiDonhangBinding.btnhuyTrangthai.setOnClickListener(view1 -> dialog.dismiss());
        });
        holder.binding.btnXoaDonHang.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            DialogXoaDonHangBinding dialogXoaDonHangBinding = DialogXoaDonHangBinding.inflate(inflater);
            builder.setView(dialogXoaDonHangBinding.getRoot());

            Dialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.nen_dialog_doan);
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
//                    if (dao.xoaDonHang(donHang)) {
//                        list.clear();
//                        list.addAll(dao.getDsDonHang());
//                        notifyDataSetChanged();
//                        dialog.dismiss();
//                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
//                    }
                    int check = dao.xoaDonHang(list.get(holder.getAdapterPosition()).getMaDonHang());
                    switch (check) {
                        case 1:
                            list.clear();
                            list = dao.getDsDonHang();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công Đơn hàng", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(context, "Xóa không thành công Đơn hàng", Toast.LENGTH_SHORT).show();
                            break;
                        case -1:
                            Toast.makeText(context, "Không xóa được Đơn hàng này vì đang còn tồn tại trong chi tiết hóa đơn", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemQlDonHangBinding binding;

        public Viewholder(ItemQlDonHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
