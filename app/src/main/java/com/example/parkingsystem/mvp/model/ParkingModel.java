package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Reservation;

import java.util.ArrayList;

public class ParkingModel {

    private int parkingSize;
    private ArrayList<Reservation> parkingReservations;

    public ParkingModel() {
        this.parkingReservations = new ArrayList<>();
    }

    public int getParkingSize() {
        return this.parkingSize;
    }

    public void setParkingSize(String size) throws IllegalArgumentException {
        this.parkingSize = Integer.parseInt(size);
        if (parkingSize == 0) {
            throw new IllegalArgumentException();
        }
    }

    public void addReservation(Reservation reservation, int parkingNumber){
        if (parkingNumber< parkingReservations.size()){
            parkingReservations.add(reservation);
        }
    }

}
