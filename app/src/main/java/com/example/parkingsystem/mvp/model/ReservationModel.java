package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;

import java.util.ArrayList;

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

    public int getParkingSize() {
        return this.parking.getParkingSize();
    }


    public boolean addReservationToParking(Reservation reservation) {
        if (!isReservationOnTheList(reservation)) {
            ArrayList<Reservation> listOfReservations = parking.getReservationsList();
            parking.getReservationsList().add(reservation);
            return true;
        }
        return false;
    }

    public boolean isReservationOnTheList(Reservation reservation) {
        boolean onTheList = false;
        for (Reservation reservationList : parking.getReservationsList()) {
            if (reservation.getParkingLot() == reservationList.getParkingLot() &&
                    reservation.getStartDateTime() >= reservationList.getStartDateTime() &&
                    reservation.getStartDateTime() < reservationList.getEndDateTime()) {

                onTheList = true;
            }
        }
        return onTheList;
    }
}
