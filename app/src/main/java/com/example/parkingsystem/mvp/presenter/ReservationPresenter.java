package com.example.parkingsystem.mvp.presenter;

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

    public void startDT() {
        view.setStartDateTimeDialog();
    }

    public void endDT() {
        view.setEndDateTimeDialog();
    }

    public void onReservationCreationButtonPressed() {
        try {
            String sCode = view.getSecurityCode();
            int parkingNumber = model.setParkingLotNumber(view.getParkingLotNumberEntered());
            long startDateTime = view.getStartDateTime().getTime();
            long endDateTime = view.getEndDateTime().getTime();

            boolean validateData = (validateSecurityCode(sCode) && validateParkingLotNumber(parkingNumber) && validateDates(startDateTime, endDateTime));

            if (validateData) {
                Reservation reservation = new Reservation(sCode, parkingNumber, startDateTime, endDateTime);
                model.addReservationToParking(reservation);
                view.showReservationConfirmation();
                view.goBackToMenu(reservation);
                // ToDo send reservation to menu

            }

        } catch (IllegalArgumentException exception) {
            Log.e(ParkingPresenter.class.getSimpleName(), exception.toString());
            view.showInvalidNumber();
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
        if (model.getParking().getParkingSize() >= parkingNumber) {
            return true;
        }
        view.showLotNumberGreaterThanParkingSize();
        return false;
    }

    public boolean validateDates(long startDateAndTime, long endDateAndTime) {
        long timeNow = new Date().getTime();
        if (startDateAndTime < endDateAndTime && startDateAndTime > timeNow) {
            return true;
        }
        view.showInconsistentDates();
        return false;
    }

}
