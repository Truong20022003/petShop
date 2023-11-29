package fpoly.truongtqph41980.petshop.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);

                                // Kiểm tra nếu ngày chọn là ngày hiện tại hoặc sau ngày hiện tại
                                if (selectedCalendar.after(Calendar.getInstance())) {
                                    String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);

                                    // binding.btnlichBatDau.setText(ngay + "/" + thang + "/" + year);
                                    binding.btnlichBatDau.setText(year + "/" + thang + "/" + ngay);
                                } else {
                                    // Hiển thị thông báo hoặc thực hiện hành động khác nếu người dùng chọn ngày trước hoặc bằng ngày hiện tại.
                                    // Ví dụ: Toast.makeText(getContext(), "Chọn ngày trong tương lai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                // Đặt giới hạn cho DatePickerDialog để chỉ cho phép chọn ngày trong tương lai
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);

                                // Kiểm tra nếu ngày chọn là ngày hiện tại hoặc sau ngày hiện tại
                                if (selectedCalendar.after(Calendar.getInstance())) {
                                    String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);

                                    // binding.btnlichBatDau.setText(ngay + "/" + thang + "/" + year);
                                    binding.btnlichKetThuc.setText(year + "/" + thang + "/" + ngay);
                                } else {
                                    // Hiển thị thông báo hoặc thực hiện hành động khác nếu người dùng chọn ngày trước hoặc bằng ngày hiện tại.
                                    // Ví dụ: Toast.makeText(getContext(), "Chọn ngày trong tương lai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                // Đặt giới hạn cho DatePickerDialog để chỉ cho phép chọn ngày trong tương lai
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        binding.btnthongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDao dao = new ThongKeDao(getContext());
//                "Ngày bắt đầu: "+ ngay + "/" + thang + "/" + year
                String ngaybd = binding.btnlichBatDau.getText().toString();
                String ngaykt = binding.btnlichKetThuc.getText().toString();
                Log.d("ngay bat dau ",ngaybd);
                Log.d("ngay ket thuc ",ngaykt);
                int tongtien = dao.tongDoanhThu(ngaybd, ngaykt);
                binding.txttongTien.setText(String.valueOf(tongtien) );
                int tongdon = dao.tongDonHang(ngaybd, ngaykt);
                binding.txtsoLuongDon.setText(String.valueOf(tongdon));
            }
        });
        return vieww;
    }
}