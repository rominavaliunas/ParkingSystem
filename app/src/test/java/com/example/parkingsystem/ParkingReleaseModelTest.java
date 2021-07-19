package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingReleaseModelTest {

    private ReleaseModel model;

    @Before
    public void setup() {
        Parking parking = new Parking(10);
        model = new ReleaseModel(parking);
    }

    @Test
    public void getSizeOfParking_sizeReturned() {
        Assert.assertEquals(10, model.getSizeOfParking());
    }

    @Test
    public void desiredParkingNumberForRelease_validParkingLotNumberForRelease_returnNumber() {
        Assert.assertEquals(2, model.desiredParkingNumberForRelease("2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void desiredParkingNumberForRelease_parkingLotNumberIsZero_throwException() {
        model.desiredParkingNumberForRelease("0");
    }

    @Test
    public void numberOfMatchesOfTheReservation_noMatches_returnZero() {
        Assert.assertEquals(0, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 2)));
    }

    @Test
    public void numberOfMatchesOfTheReservation_oneMatch_returnOne() {

    }

    @Test
    public void numberOfMatchesOfTheReservation_moreThanOneCoincidence_returnCoincidencesNumber() {

    }

    @Test
    public void releaseParking_parkingHasBeenReleased_isTrue() {
        Reservation reservationToBeReleased = new Reservation("ABC", 1);

    }

    @Test
    public void releaseParking_sizeOfListIsZero_returnFalse() {
        Assert.assertFalse(model.releaseParking(new Reservation("ABC", 1)));
    }

    @Test
    public void releaseParking_numberOfMatchesOfTheReservationIsMoreThanOne_isFalse() {

    }
}
