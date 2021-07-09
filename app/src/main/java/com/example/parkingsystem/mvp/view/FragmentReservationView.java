package com.example.parkingsystem.mvp.view;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.parkingsystem.R;
import com.example.parkingsystem.activities.MenuActivity;
import com.example.parkingsystem.databinding.FragmentReservationBinding;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.utilities.Picker;

import java.util.Date;

import static com.example.parkingsystem.activities.MenuActivity.RESERVATION;

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
            showToast(context.getString(R.string.error_invalid_parking_lot_number_logged));
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

    public void showReservationNotAdded() {
        if (context != null) {
            showToast(context.getString(R.string.error_another_reservation_in_place));
        }
    }

    public void showEmptyDates() {
        if (context != null) {
            showToast(context.getString(R.string.error_one_or_both_dates_are_null));
        }
    }

    public void goBackToMenu(Reservation reservation) {
        if (context != null) {
            Intent intent = new Intent(context, MenuActivity.class);
            intent.putExtra(RESERVATION, reservation);
            context.startActivity(intent);
        }
    }
}