package com.example.parkingsystem.mvp.presenter;
import android.util.Log;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

public class ReleasePresenter {
    private FragmentReleaseView view;
    private ReleaseModel model;
    public ReleasePresenter(FragmentReleaseView releaseView, ReleaseModel releaseModel){
        this.view = releaseView;
        this.model = releaseModel;
    }
    public void onReservationCreationButtonPressed() {
        int parkingNumber = 0;
        try {
            parkingNumber = model.parkingNumberForRelease(view.getParkingLotNumberEntered());
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            view.showInvalidParkingNumberForRelease();
            return;
        }
        String securityCode = view.getSecurityCode();
        if (validateSecurityCode(securityCode) && validateParkingLotNumber(parkingNumber)) {
            if (model.releaseParking(securityCode, parkingNumber)) {
                view.showParkingReleasedConfirmation();
                //view.goBackToMenu(reservation);
            } else {
                view.showWeCannotFindYourParking();
            }
        }
    }
    private boolean validateParkingLotNumber(int parkingNumber) {
        boolean isValid = true;
        return isValid;
    }
    private boolean validateSecurityCode(String sCode) {
        boolean isValid = true;
        return isValid;
    }
}
