package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.entities.Parking;
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

    public boolean onReservationCreationButtonPressed() {
        int parkingNumber;
        try {
            parkingNumber = model.setParkingLotNumber(view.getParkingLotNumberEntered());
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            view.showInvalidNumber();
            return false;
        }
        String securityCode = view.getSecurityCode();
        long startDateTime = view.getStartDateTime().getTime();
        long endDateTime = view.getEndDateTime().getTime();

        if (!model.validateSecurityCode(securityCode)) {
            view.showCodeNotComplaint();
        }
        if (!model.validateParkingLotNumber(parkingNumber, model.getParkingSize())) {
            view.showLotNumberGreaterThanParkingSize();
        }
        if (model.validateSecurityCode(securityCode) && model.validateParkingLotNumber(parkingNumber, model.getParkingSize()) && validateDates(startDateTime, endDateTime)) {
            Reservation reservation = new Reservation(securityCode, parkingNumber, startDateTime, endDateTime);
            if (model.addReservationToParking(reservation)) {
                view.showReservationConfirmation();
                return true;
            } else {
                view.showReservationNotAdded();
            }
        }
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

    public Parking getReservationsOnTheParking() {
        return model.getParking();
    }
}