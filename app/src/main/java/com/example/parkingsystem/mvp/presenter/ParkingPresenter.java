package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.view.ParkingView;

public class ParkingPresenter {

    private final ParkingModel parkingModel;
    private final ParkingView parkingView;

    public ParkingPresenter(ParkingModel newParkingModel, ParkingView newParkingView) {
        this.parkingModel = newParkingModel;
        this.parkingView = newParkingView;
    }

    public void onParkingSizeCreationButtonPressed() {
        try {
            parkingModel.setParkingSize(parkingView.getSizeSubmitted());
            parkingView.showParkingSize(parkingModel.getParkingSize());
            parkingView.navigateToMenu(parkingModel.getParkingSize()); //ToDo test
        } catch (NumberFormatException exception) {
            Log.e(ParkingPresenter.class.getSimpleName(), exception.toString());
            parkingView.showInvalidSizeError();
        } catch (IllegalArgumentException exception) {
            Log.e(ParkingPresenter.class.getSimpleName(), exception.toString());
            parkingView.showInvalidNumber();
        }

    }
}