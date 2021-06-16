package com.example.parkingsystem.mvp.presenter;

import com.example.parkingsystem.mvp.model.Parking;
import com.example.parkingsystem.mvp.view.ParkingView;

public class ParkingPresenter {

    private Parking parking;
    private ParkingView parkingView;

    public ParkingPresenter (Parking newParking, ParkingView newParkingView){
        this.parking = newParking;
        this.parkingView = newParkingView;
    }

    public void onParkingSizeCreationButtonPressed(){
        int size = parkingView.getSizeSubmitted();
        parking.setParkingSize(size);
        parkingView.setSize(String.valueOf(parking.getParkingSize()));
    }
}
