package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.view.ParkingView;

public class ParkingPresenter {

    private ParkingModel parkingModel;
    private ParkingView parkingView;

    public ParkingPresenter(ParkingModel newParkingModel, ParkingView newParkingView) {
        this.parkingModel = newParkingModel;
        this.parkingView = newParkingView;
    }

    public void onParkingSizeCreationButtonPressed() {
        try {
            if ((Integer.parseInt(parkingView.getSizeSubmitted())) == 0) {
                parkingView.showInvalidNumber();
            } else {
                parkingModel.setParkingSize(parkingView.getSizeSubmitted());
                parkingView.showParkingSize(parkingModel.getParkingSize());
            }
        } catch (NumberFormatException exception) {
            Log.e(ParkingPresenter.class.getSimpleName(), exception.toString());
            parkingView.showInvalidSizeError();
        }

    }
}