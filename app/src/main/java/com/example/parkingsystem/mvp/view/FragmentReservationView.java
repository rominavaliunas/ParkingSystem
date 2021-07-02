package com.example.parkingsystem.mvp.view;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.FragmentReservationBinding;
import com.example.parkingsystem.utilities.Picker;

import java.util.Date;

public class FragmentReservationView extends FragmentView {

    private final FragmentReservationBinding binding;
    private final Context context;
    private Picker startDateTimePicker;
    private Picker endDateTimePicker;

    public FragmentReservationView(Fragment fragmentRef, FragmentReservationBinding binding) {
        super(fragmentRef);
        this.binding = binding;
        this.context = getContext();
        startDateTimePicker = new Picker(binding.startInputDateAndTime);
        endDateTimePicker = new Picker(binding.endInputDateAndTime);
    }

    public String getSecurityCode() {
        String code = binding.textReservationSecurityCode.getText().toString();
        return code;
    }

    public String getParkingLotNumberEntered() {
        String number = binding.textReservationParkingLot.getText().toString();
        return number;
    }

    public void setStartDateTimeDialog() {
        startDateTimePicker.show();
    }

    public void setEndDateTimeDialog() {
        endDateTimePicker.show();
    }

    public Date getStartDateTime() {
        return startDateTimePicker.getDate();
    }

    public Date getEndDateTime() {
        return endDateTimePicker.getDate();
    }

    public void showReservationConfirmation() {
        if (context != null) {
            showToast(context.getString(R.string.confirmation_reservation_created));
        }
    }

    public void showInvalidNumber() {
        if (context != null) {
            showToast(context.getString(R.string.error_invalid_number_logged));
        }
    }

    public void showCodeNotComplaint() {
        if (context != null) {
            showToast(context.getString(R.string.error_security_code_not_compliant));
        }
    }

    public void showLotNumberGreaterThanParkingSize() {
        if (context != null) {
            showToast(context.getString(R.string.error_lot_number_bigger_than_parking_size));
        }
    }

    public void showInconsistentDates() {
        if (context != null) {
            showToast(context.getString(R.string.error_inconsistent_dates));
        }
    }
}