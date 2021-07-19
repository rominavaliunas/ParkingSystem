package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

public class ReleasePresenter {
    private final FragmentReleaseView view;
    private final ReleaseModel model;

    public ReleasePresenter(FragmentReleaseView releaseView, ReleaseModel releaseModel) {
        this.view = releaseView;
        this.model = releaseModel;
    }

    public boolean onReleaseReservationButtonPressed() {
        int parkingNumber;
        try {
            parkingNumber = model.desiredParkingNumberForRelease(view.getLotNumberToBeReleased());
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            view.showNegativeOrZeroParkingNumber();
            return false;
        }
        String securityCode = view.getSecurityCode();
        if (validateSecurityCode(securityCode) && validateParkingLotNumber(parkingNumber)) {
            Reservation newReservation = new Reservation(securityCode, parkingNumber);
            if (model.numberOfMatchesOfTheReservation(newReservation) > 1) {
                view.showBugMessage();
                return false;
            }
            if (model.releaseParking(newReservation)) {
                view.showParkingReleasedConfirmation();
                return true;
            } else {
                view.showWeCannotFindYourParking();
                return false;
            }
        }
        return false;
    }

    public boolean validateParkingLotNumber(int parkingNumber) {
        if (model.getSizeOfParking() >= parkingNumber) {
            return true;
        }
        if (parkingNumber <= 0) {
            view.showNegativeOrZeroParkingNumber();
        } else {
            view.showInvalidParkingNumberForRelease();
        }
        return false;
    }

    public boolean validateSecurityCode(String code) {
        if (!code.isEmpty() && code.length() < 10) {
            return true;
        }
        view.showCodeNotComplaint();
        return false;
    }

    public Parking getReservationsOnTheParking() {
        return model.getParking();
    }
}