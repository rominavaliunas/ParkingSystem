package com.example.parkingsystem.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

import java.util.Date;

public class ReservationPresenter {

    private final ReservationModel model;
    private final FragmentReservationView view;

    public ReservationPresenter(ReservationModel reservationModel, FragmentReservationView reservationView) {
        this.model = reservationModel;
        this.view = reservationView;
    }

    public void selectStartDateAndTime() {
        view.setStartDateTimeDialog();
    }

    public void selectEndDateAndTime() {
        view.setEndDateTimeDialog();
    }

    public void onReservationCreationButtonPressed() {
        int parkingNumber = 0;
        try {
            parkingNumber = model.setParkingLotNumber(view.getParkingLotNumberEntered());
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            view.showInvalidNumber();
            return;
        }
        String securityCode = view.getSecurityCode();
        long startDateTime = view.getStartDateTime().getTime();
        long endDateTime = view.getEndDateTime().getTime();

        if (validateSecurityCode(securityCode) && validateParkingLotNumber(parkingNumber) && validateDates(startDateTime, endDateTime)) {
            Reservation reservation = new Reservation(securityCode, parkingNumber, startDateTime, endDateTime);
            if (model.addReservationToParking(reservation)) {
                view.showReservationConfirmation();
                view.goBackToMenu(reservation);
            } else {
                view.showReservationNotAdded();
            }
        }
    }

    public boolean validateSecurityCode(String code) {
        if (!code.isEmpty() && code.length() < 10) {
            return true;
        }
        view.showCodeNotComplaint();
        return false;
    }

    public boolean validateParkingLotNumber(int parkingNumber) {
        if (model.getParkingSize() >= parkingNumber) {
            return true;
        }
        view.showLotNumberGreaterThanParkingSize();
        return false;
    }

    public boolean validateDates(long startDateAndTime, long endDateAndTime) {
        if (startDateAndTime != 0 || endDateAndTime != 0) {
            long timeNow = new Date().getTime();
            if (startDateAndTime < endDateAndTime && startDateAndTime > timeNow) {
                return true;
            }
            view.showInconsistentDates();
            return false;
        }
        view.showEmptyDates();
        return false;
    }
}