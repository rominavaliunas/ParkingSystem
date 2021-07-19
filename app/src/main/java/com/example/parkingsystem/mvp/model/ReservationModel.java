package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;

public class ReservationModel {

    private final Parking parking;

    public ReservationModel(Parking parking) {
        this.parking = parking;
    }

    public int setParkingLotNumber(String size) throws IllegalArgumentException {
        int parkingNumber = Integer.parseInt(size);
        if (parkingNumber == 0) {
            throw new IllegalArgumentException();
        }
        return parkingNumber;
    }

    public int getParkingSize() {
        return this.parking.getParkingSize();
    }

    public Parking getParking() {
        return this.parking;
    }

    public boolean addReservationToParking(Reservation reservation) {
        if (!isReservationOnTheList(reservation)) {
            parking.getReservationsList().add(reservation);
            return true;
        }
        return false;
    }

    private boolean isReservationOnTheList(Reservation newReservation) {
        if (parking.getReservationsList().size() == 0) {
            return false;
        }
        for (Reservation reservation : parking.getReservationsList()) {
            if (newReservation.getParkingLot() == reservation.getParkingLot() &&
                    //When new reservation begins after the reservation in place
                    ((newReservation.getStartDateTime() >= reservation.getEndDateTime() ||
                            //When new reservation begins before the reservation in place
                            (newReservation.getEndDateTime() <= reservation.getStartDateTime())))) {
                return false;
            } else if (newReservation.getParkingLot() != reservation.getParkingLot()) {
                return false;
            }
        }
        return true;
    }
}