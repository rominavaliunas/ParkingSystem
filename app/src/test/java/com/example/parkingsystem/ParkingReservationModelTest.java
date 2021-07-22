package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.entities.Validator;
import com.example.parkingsystem.mvp.model.ReservationModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

public class ParkingReservationModelTest {

    private ReservationModel model;
    @Mock
    private Validator validator;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Parking parking = new Parking(10);
        model = new ReservationModel(parking, validator);
    }

    @Test
    public void getParkingSize_sizeReturned() {
        Assert.assertEquals(10, model.getParkingSize());
    }

    @Test
    public void setParkingLotNumber_validParkingLotNumber_returnParkingLotInt() {
        Assert.assertEquals(5, model.setParkingLotNumber("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setParkingLotNumber_invalidParkingLotNumber_throwException() {
        model.setParkingLotNumber("0");
    }

    @Test
    public void addReservationToParking_reservationAdded_isTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationTakesPlaceBeforeExistingOne_isTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationTakesPlaceAfterExistingOne_isTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationWithSameArguments_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertFalse(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationBeginsBeforeExistingOneEnds_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertFalse(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationFinishesInTheMiddleOfAnExistingOne_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertFalse(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
    }

    @Test
    public void addReservationToParking_secondReservationTakesPlaceInTheMiddleOfAnExistingOne_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertFalse(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
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