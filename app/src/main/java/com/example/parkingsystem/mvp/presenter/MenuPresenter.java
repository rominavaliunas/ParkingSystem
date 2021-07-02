package com.example.parkingsystem.mvp.presenter;

import com.example.parkingsystem.entities.Parking;

public class MenuPresenter {
    private Parking parking;

    public MenuPresenter(int parkingSize) {
        parking = new Parking(parkingSize);
    }

    public Parking getParking() {
        return parking;
    }

}
