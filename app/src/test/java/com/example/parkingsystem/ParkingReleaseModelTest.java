package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.entities.Validator;
import com.example.parkingsystem.exceptions.ReleaseEmptyListException;
import com.example.parkingsystem.exceptions.ReleaseMoreThanOneMatchException;
import com.example.parkingsystem.exceptions.ReleaseNoMatchesException;
import com.example.parkingsystem.mvp.model.ReleaseModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class ParkingReleaseModelTest {

    private ReleaseModel model;
    private Parking parking;
    @Mock
    private Validator validator;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        parking = new Parking(10);
        model = new ReleaseModel(parking, validator);
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
        Reservation reservation = new Reservation("1223", 5);
        parking.getReservationsList().add(reservation);

        Assert.assertEquals(1, model.numberOfMatchesOfTheReservation(new Reservation("1223", 5)));
    }

    @Test
    public void numberOfMatchesOfTheReservation_moreThanOneCoincidence_returnTwoCoincidences() {
        Reservation reservation = new Reservation("Cheese", 1);
        parking.getReservationsList().add(reservation);
        Reservation secondReservation = new Reservation("Cheese", 1);
        parking.getReservationsList().add(secondReservation);

        Assert.assertEquals(2, model.numberOfMatchesOfTheReservation(new Reservation("Cheese", 1)));
    }

    @Test
    public void releaseParking_parkingHasBeenReleased_success() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        Reservation reservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(reservation);

        Assert.assertEquals(1, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 1)));
        model.releaseParking(new Reservation("ABC", 1));
        Assert.assertFalse(parking.getReservationsList().contains(new Reservation("ABC", 1)));
    }

    @Test(expected = ReleaseEmptyListException.class)
    public void releaseParking_sizeOfReservationsListIsZero_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        model.releaseParking(new Reservation("ABC", 1));
    }

    @Test(expected = ReleaseMoreThanOneMatchException.class)
    public void releaseParking_numberOfMatchesOfTheReservationIsMoreThanOne_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        Reservation reservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(reservation);
        Reservation secondReservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(secondReservation);

        Assert.assertEquals(2, model.numberOfMatchesOfTheReservation(new Reservation("ABC", 1)));
        model.releaseParking(new Reservation("ABC", 1));
    }

    @Test(expected = ReleaseNoMatchesException.class)
    public void releaseParking_noReservationMatchesYourSearch_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        Reservation reservation = new Reservation("ABC", 1);
        parking.getReservationsList().add(reservation);

        Assert.assertEquals(0, model.numberOfMatchesOfTheReservation(new Reservation("1234", 1)));
        model.releaseParking(new Reservation("1234", 1));
    }

    @Test
    public void validateSecurityCode_isTrue() {
        when(validator.validateSecurityCode("123")).thenReturn(true);
        Assert.assertTrue(model.validateSecurityCode("123"));
    }

    @Test
    public void validateSecurityCode_codeIsEmpty_isFalse() {
        when(validator.validateSecurityCode("")).thenReturn(false);
        Assert.assertFalse(model.validateSecurityCode(""));
    }

    @Test
    public void validateSecurityCode_codeIsLargerThanExpected_isFalse() {
        when(validator.validateSecurityCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).thenReturn(false);
        Assert.assertFalse(model.validateSecurityCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    @Test
    public void validateParkingLotNumber_isTrue() {
        when(validator.validateParkingLotNumber(2, 10)).thenReturn(true);
        Assert.assertTrue(model.validateParkingLotNumber(2, 10));
    }

    @Test
    public void validateParkingLotNumber_isGreaterThanParkingSize_isFalse() {
        when(validator.validateParkingLotNumber(5, 3)).thenReturn(false);
        Assert.assertFalse(model.validateParkingLotNumber(5, 3));
    }
}