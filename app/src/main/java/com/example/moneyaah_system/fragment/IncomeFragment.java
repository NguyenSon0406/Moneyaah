package com.example.moneyaah_system.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.moneyaah_system.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class IncomeFragment extends Fragment {
    String[] items = new String[]{"Trả thêm giờ", "Tiền lương", "Tiền cấp", "Tiền thưởng", "Khác"};

    private EditText selectDate,edt_money,edt_note;
    private String getMoney, getNote;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_income, container, false);
        Spinner dropdown = inflate.findViewById(R.id.spinner1);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate = inflate.findViewById(R.id.edt_select_date);
        edt_money = inflate.findViewById((R.id.txt_money));
        edt_note = inflate.findViewById((R.id.txt_note));
        Bundle data = getArguments();
        if(data!=null)
        {
            getMoney =data.getString("money").toString();
            getNote = data.getString("note").toString();
            edt_note.setText(""+getNote);
            edt_money.setText(getMoney);
        }

//        Intent myIntent = getActivity().getIntent();
//        String getMoney = myIntent.getStringExtra("money");
//        String getNote = myIntent.getStringExtra("note");
//        txt_note.setText(""+getNote);
//        txt_money.setText(""+getMoney);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        IncomeFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;
                        selectDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        selectDate.setText(dtf.format(now).toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflate.getContext(),  android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        return inflate;
    }
}