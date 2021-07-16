package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;

public class ReleaseModel {

    private final Parking parking;

    public ReleaseModel(Parking releaseParking) {
        this.parking = releaseParking;
    }

    public Parking getParking() {
        return this.parking;
    }

    public int getSizeOfParking() {
        return this.parking.getParkingSize();
    }

    public int desiredParkingNumberForRelease(String size) throws IllegalArgumentException {
        int parkingLotNumber = Integer.parseInt(size);
        if (parkingLotNumber == 0) {
            throw new IllegalArgumentException();
        }
        return parkingLotNumber;
    }

    public boolean releaseParking(Reservation newReservation) {
        if (getParking().getReservationsList().size() == 0) {
            return false;
        }
        for (Reservation reservation : getParking().getReservationsList()) {
            if (reservation.equals(newReservation)) {
                getParking().getReservationsList().remove(reservation);
                return true;
            }
        }
        return false;
    }
}