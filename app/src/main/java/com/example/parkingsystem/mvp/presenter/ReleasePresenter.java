package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.exceptions.ReleaseEmptyListException;
import com.example.parkingsystem.exceptions.ReleaseMoreThanOneMatchException;
import com.example.parkingsystem.exceptions.ReleaseNoMatchesException;
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
            Log.e(ParkingSizePresenter.class.getSimpleName(), "Invalid parking lot", exception);
            view.showNegativeOrZeroParkingNumber();
            return false;
        }
        String securityCode = view.getSecurityCode();
        if (!model.validateSecurityCode(securityCode)) {
            view.showCodeNotComplaint();
        }
        if (!model.validateParkingLotNumber(parkingNumber, model.getSizeOfParking())) {
            view.showInvalidParkingNumberForRelease();
        }
        if (model.validateSecurityCode(securityCode) && model.validateParkingLotNumber(parkingNumber, model.getSizeOfParking())) {
            Reservation newReservation = new Reservation(securityCode, parkingNumber);
            try {
                model.releaseParking(newReservation);
                view.showParkingReleasedConfirmation();
                return true;
            } catch (ReleaseMoreThanOneMatchException exception) {
                view.showBugMessage();
            } catch (ReleaseNoMatchesException exception) {
                view.showWeCannotFindYourParking();
            } catch (ReleaseEmptyListException exception) {
                view.showNoReservationInPlaceYet();
            }
        }
        return false;
    }

    public Parking getReservationsOnTheParking() {
        return model.getParking();
    }
}