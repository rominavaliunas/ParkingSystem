package com.example.parkingsystem.mvp.model;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.entities.Validator;
import com.example.parkingsystem.exceptions.ReleaseEmptyListException;
import com.example.parkingsystem.exceptions.ReleaseMoreThanOneMatchException;
import com.example.parkingsystem.exceptions.ReleaseNoMatchesException;

public class ReleaseModel {

    private Parking parking;
    private Validator validator;

    public ReleaseModel(Parking releaseParking, Validator validator) {
        this.validator = validator;
        parking = releaseParking;
    }

    public Parking getParking() {
        return parking;
    }

    public int getSizeOfParking() {
        return parking.getParkingSize();
    }

    public int desiredParkingNumberForRelease(String size) throws IllegalArgumentException {
        int parkingLotNumber = Integer.parseInt(size);
        if (parkingLotNumber <= 0) {
            throw new IllegalArgumentException();
        }
        return parkingLotNumber;
    }

    public int numberOfMatchesOfTheReservation(Reservation reservationToBeReleased) {
        int numberOfMatches = 0;
        for (Reservation reservation : getParking().getReservationsList()) {
            if (reservation.equals(reservationToBeReleased)) {
                numberOfMatches++;
            }
        }
        return numberOfMatches;
    }

    public void releaseParking(Reservation newReservation) throws ReleaseEmptyListException, ReleaseMoreThanOneMatchException, ReleaseNoMatchesException {
        if (getParking().getReservationsList().size() == 0) {
            throw new ReleaseEmptyListException();
        }
        if (numberOfMatchesOfTheReservation(newReservation) > 1) {
            throw new ReleaseMoreThanOneMatchException();
        }
        if (numberOfMatchesOfTheReservation(newReservation) == 1) {
            getParking().getReservationsList().remove(newReservation);
        } else {
            throw new ReleaseNoMatchesException();
        }
    }

    public boolean validateParkingLotNumber(int parkingNumber, int sizeOfParking) {
        return validator.validateParkingLotNumber(parkingNumber, sizeOfParking);
    }

    public boolean validateSecurityCode(String code) {
        return validator.validateSecurityCode(code);
    }
    
}