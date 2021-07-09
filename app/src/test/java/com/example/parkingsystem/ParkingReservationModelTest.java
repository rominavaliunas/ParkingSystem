package com.example.parkingsystem;

import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReservationPresenter;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParkingReservationModelTest {

    private ReservationPresenter presenter;
    private ReservationModel model;
    private FragmentReservationView view;
    private Parking parking;
    private Reservation reservation;

    @Before
    public void setup() {
        parking = new Parking(10);
        model = new ReservationModel(parking);
    }

    @Test
    public void getParkingSize_sizeReturned(){
        Assert.assertEquals(10, model.getParkingSize());
    }

    @Test
    public void setParkingLotNumber_validParkingLotNumber_returnParkingLotInt() {
        Assert.assertEquals(5, model.setParkingLotNumber("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setParkingLotNumber_invalidParkingLotNumber_throwException() {
        model.setParkingLotNumber("asjdb");
    }

    @Test
    public void setParkingLotNumber_failed_catchException() {

    }

    @Test
    public void addReservationToParking_isTrue_reservationAdded() {

    }

    @Test
    public void addReservationToParking_isFalse_anotherReservationInPlace() {

    }

    @Test
    public void isReservationOnTheList_isFalse() {

    }

    @Test
    public void isReservationOnTheList_isTrue(){
        model.isReservationOnTheList(reservation);

    }
}