package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;

public class ReleaseModel {

    private Parking parking;
    private int parkingLotNumber;

    ReleaseModel(Parking releaseParking){
        this.parking = releaseParking;
    }

    public int parkingNumberForRelease(String size) throws IllegalArgumentException {
        this.parkingLotNumber = Integer.parseInt(size);
        if (parkingLotNumber == 0) {
            throw new IllegalArgumentException();
        }
        return parkingLotNumber;
    }

    public boolean releaseParking(String sCode, int parkingNumber) {
        boolean released = true;
        //ToDo check for a reservation with the same sCode and same parkingNumber and erase it
        return released;
    }
}