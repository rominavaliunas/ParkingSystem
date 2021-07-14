package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.view.ParkingView;

public class ParkingSizePresenter {

    private final ParkingModel parkingModel;
    private final ParkingView parkingView;

    public ParkingSizePresenter(ParkingModel newParkingModel, ParkingView newParkingView) {
        this.parkingModel = newParkingModel;
        this.parkingView = newParkingView;
    }

    public void onParkingSizeCreationButtonPressed() {
        try {
            parkingModel.setParkingSize(parkingView.getSizeSubmitted());
            parkingView.showParkingSize(parkingModel.getParkingSize());
            parkingView.navigateToMenu(parkingModel.getParkingSize());
        } catch (NumberFormatException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            parkingView.showInvalidSizeError();
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingSizePresenter.class.getSimpleName(), exception.toString());
            parkingView.showInvalidNumber();
        }

    }
}