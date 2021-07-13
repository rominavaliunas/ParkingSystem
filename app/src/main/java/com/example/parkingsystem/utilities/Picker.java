package com.example.parkingsystem.utilities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public class Picker {
    private DatePickerDialog datePickerDialog;
    Date date;

    public Picker(EditText date_time) {
        showDateTimeDialog(date_time);
    }

    public Date getDate() {
        return date;
    }

    public void showDateTimeDialog(EditText date_time) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(HOUR_OF_DAY, hourOfDay);
                        calendar.set(MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        date = calendar.getTime();
                        date_time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(date_time.getContext(),
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false).show();
            }
        };

        datePickerDialog = new DatePickerDialog(date_time.getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void show() {
        datePickerDialog.show();
    }
}