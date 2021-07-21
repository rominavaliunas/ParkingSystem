package com.example.parkingsystem.entities;

public class Validator {

    public boolean validateSecurityCode(String code) {
        return !code.isEmpty() && code.length() <= 10;
    }

    public boolean validateParkingLotNumber(int parkingNumber, int parkingSize) {
        return parkingNumber <= parkingSize;
    }
}
