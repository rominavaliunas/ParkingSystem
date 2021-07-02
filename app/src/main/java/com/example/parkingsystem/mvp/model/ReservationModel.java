package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;

public class ReservationModel {

    private final Parking parking;
    private int parkingNumber;

    public ReservationModel(Parking parking) {
        this.parking = parking;
    }

    public int setParkingLotNumber(String size) throws IllegalArgumentException {
        this.parkingNumber = Integer.parseInt(size);
        if (parkingNumber == 0) {
            throw new IllegalArgumentException();
        }
        return parkingNumber;
    }

    public Parking getParking() {
        return this.parking;
    }


    public void addReservationToParking(Reservation reservation) {
        parking.getReservationsList().add(reservation);
    }

}
