package com.example.parkingsystem.mvp.model;

import java.util.ArrayList;

public class ParkingModel {

    private int parkingSize;
    private ArrayList<ParkingLot> parkingLots;

    public ParkingModel() {
        this.parkingLots = new ArrayList<>();
    }

    public int getParkingSize() {
        return this.parkingSize;
    }

    public void setParkingSize(String size) throws NumberFormatException {
        this.parkingSize = Integer.parseInt(size);
    }

}
