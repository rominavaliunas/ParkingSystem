package com.example.parkingsystem.mvp.view;

import androidx.fragment.app.Fragment;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.FragmentReservationBinding;
import com.example.parkingsystem.utilities.Picker;

import java.util.Date;

public class FragmentReservationView extends FragmentView {

    private final FragmentReservationBinding binding;
    private Picker startDateTimePicker;
    private Picker endDateTimePicker;

    public FragmentReservationView(Fragment fragmentRef, FragmentReservationBinding binding) {
        super(fragmentRef);
        this.binding = binding;
        startDateTimePicker = new Picker(binding.startInputDateAndTime);
        endDateTimePicker = new Picker(binding.endInputDateAndTime);
    }

    public String getSecurityCode() {
        return binding.textReservationSecurityCode.getText().toString();
    }

    public String getParkingLotNumberEntered() {
        return binding.textReservationParkingLot.getText().toString();
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
        showToast(R.string.confirmation_reservation_created);
    }

    public void showInvalidNumber() {
        showToast(R.string.error_invalid_parking_lot_number_logged);
    }

    public void showCodeNotComplaint() {
        showToast(R.string.error_security_code_not_compliant);
    }

    public void showLotNumberGreaterThanParkingSize() {
        showToast(R.string.error_lot_number_bigger_than_parking_size);
    }

    public void showInconsistentDates() {
        showToast(R.string.error_inconsistent_dates);
    }

    public void showReservationNotAdded() {
        showToast(R.string.error_another_reservation_in_place);
    }

    public void showEmptyDates() {
        showToast(R.string.error_one_or_both_dates_are_null);
    }
}