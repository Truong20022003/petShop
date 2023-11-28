package fpoly.truongtqph41980.petshop.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

import fpoly.truongtqph41980.petshop.Dao.ThongKeDao;
import fpoly.truongtqph41980.petshop.databinding.FragmentThongKeBinding;


public class ThongKe extends Fragment {

    FragmentThongKeBinding binding;
    View vieww;

    public ThongKe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThongKeBinding.inflate(inflater, container, false);
        vieww = binding.getRoot();
        Calendar calendar = Calendar.getInstance();
        binding.btnlichBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10) {
                                    ngay = "0" + dayOfMonth;
                                } else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf((month + 1));
                                }
                                binding.btnlichBatDau.setText("Ngày bắt đầu: " + year + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        binding.btnlichtruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10){
                                    thang = "0" + (month + 1);
                                }else {
                                    thang = String.valueOf((month + 1));
                                }
                                binding.btnlichBatDau.setText("Ngày bắt đầu: "+year + "/" + thang + "/" +ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        binding.btnlichKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10) {
                                    ngay = "0" + dayOfMonth;
                                } else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf((month + 1));
                                }
                                binding.btnlichKetThuc.setText("Ngày kết thúc: " + year + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        binding.btnlichsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10) {
                                    ngay = "0" + dayOfMonth;
                                } else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf((month + 1));
                                }
                                binding.btnlichKetThuc.setText("Ngày kết thúc: " + year + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
//        binding.btnthongKe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ThongKeDao dao = new ThongKeDao(getContext());
//                String ngaybd = binding.btnlichBatDau.getText().toString();
//                String ngaykt = binding.btnlichKetThuc.getText().toString();
//                int tongtien = dao.tongDoanhThu(ngaybd, ngaykt);
//                binding.txttongTien.setText(tongtien + " VND");
//                int tongdon = dao.tongDonHang(ngaybd, ngaykt);
//                binding.txtsoLuongDon.setText(tongdon + " đơn");
//            }
//        });
        return vieww;
    }
}