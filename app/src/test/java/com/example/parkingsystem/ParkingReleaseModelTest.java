package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;

import org.junit.Assert;
import org.junit.Test;

public class ParkingReleaseModelTest {

    private ReleaseModel model;

    @Test
    public void getSizeOfParking_sizeReturned() {
        model = new ReleaseModel(new Parking(10));
        Assert.assertEquals(10, model.getSizeOfParking());
    }

    @Test
    public void desiredParkingNumberForRelease_validParkingLotNumberForRelease_returnNumber() {
        model = new ReleaseModel(new Parking(10));
        Assert.assertEquals(2, model.desiredParkingNumberForRelease("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void desiredParkingNumberForRelease_parkingLotNumberIsZero_throwException() {
        model = new ReleaseModel(new Parking(10));
        model.desiredParkingNumberForRelease("0");
    }

    @Test
    public void numberOfMatchesOfTheReservation_noMatches_returnZero() {
        model = new ReleaseModel(new Parking(10));
        Assert.assertEquals(0, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 2)));
    }

    @Test
    public void numberOfMatchesOfTheReservation_oneMatch_releaseReservation() {
        Parking parking = new Parking(10);
        Reservation reservation = new Reservation("1223", 5);
        parking.getReservationsList().add(reservation);

        model = new ReleaseModel(parking);

        Assert.assertEquals(1, model.numberOfMatchesOfTheReservation(new Reservation("1223", 5)));
    }

    @Test
    public void numberOfMatchesOfTheReservation_moreThanOneCoincidence_returnTwoCoincidences() {
        Parking parking = new Parking(10);
        Reservation reservation = new Reservation("Cheese", 1);
        parking.getReservationsList().add(reservation);
        Reservation secondReservation = new Reservation("Cheese", 1);
        parking.getReservationsList().add(secondReservation);

        model = new ReleaseModel(parking);

        Assert.assertEquals(2, model.numberOfMatchesOfTheReservation(new Reservation("Cheese", 1)));
    }

    @Test
    public void releaseParking_parkingHasBeenReleased_isTrue() {
        Parking parking = new Parking(10);
        Reservation reservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(reservation);

        model = new ReleaseModel(parking);

        Assert.assertEquals(1, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 1)));
        Assert.assertTrue(model.releaseParking(new Reservation("ABC", 1)));
    }

    @Test
    public void releaseParking_sizeOfReservationsListIsZero_returnFalse() {
        model = new ReleaseModel(new Parking(10));
        Assert.assertFalse(model.releaseParking(new Reservation("ABC", 1)));
    }

    @Test
    public void releaseParking_numberOfMatchesOfTheReservationIsMoreThanOne_isFalse() {
        Parking parking = new Parking(10);
        Reservation reservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(reservation);
        Reservation secondReservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(secondReservation);

        model = new ReleaseModel(parking);

        Assert.assertEquals(2, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 1)));
        Assert.assertFalse(model.releaseParking(new Reservation("ABC", 1)));
    }
}