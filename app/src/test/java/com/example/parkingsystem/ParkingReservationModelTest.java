package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReservationModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class ParkingReservationModelTest {

    private ReservationModel model;

    @Before
    public void setup() {
        Parking parking = new Parking(10);
        model = new ReservationModel(parking);
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
        calendar.add(Calendar.DAY_OF_YEAR, 1);
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
    public void addReservationToParking_secondReservationTakesPlaceBeforeExistingOneBegins_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate2ndReservation = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();
        Assert.assertTrue(model.addReservationToParking(new Reservation("sdj", 2, startDate.getTime(), endDate.getTime())));
        Assert.assertFalse(model.addReservationToParking(new Reservation("sdj", 2, startDate2ndReservation.getTime(), endDate2ndReservation.getTime())));
    }
}