package com.example.parkingsystem.mvp.model;

public class ParkingModel {

    private int parkingSize;

    public int getParkingSize() {
        return this.parkingSize;
    }

    public void setParkingSize(String size) throws IllegalArgumentException {
        this.parkingSize = Integer.parseInt(size);
        if (parkingSize == 0) {
            throw new IllegalArgumentException();
        }
    }

}
