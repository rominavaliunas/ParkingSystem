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

    public int getParkingSize() {
        return this.parking.getParkingSize();
    }


    public boolean addReservationToParking(Reservation reservation) {
        if (!isReservationOnTheList(reservation)) {
            parking.getReservationsList().add(reservation);
            return true;
        }
        return false;
    }

    private boolean isReservationOnTheList(Reservation reservation) {
        boolean onTheList = false;
        for (Reservation reservationList : parking.getReservationsList()) {
            if (reservation.getParkingLot() == reservationList.getParkingLot() &&
                    reservation.getStartDateTime() < reservationList.getEndDateTime()) {
                onTheList = true;
                break;
            }
        }
        return onTheList;
    }
}
