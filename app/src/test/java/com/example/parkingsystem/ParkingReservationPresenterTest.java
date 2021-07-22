package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReservationPresenter;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParkingReservationPresenterTest {

    private ReservationPresenter presenter;
    private ReservationModel model;
    private FragmentReservationView view;
    private MockedStatic<Log> logMockedStatic;

    @Before
    public void setup() {
        model = mock(ReservationModel.class);
        view = mock(FragmentReservationView.class);
        presenter = new ReservationPresenter(model, view);
        logMockedStatic = mockStatic(Log.class);
    }

    @Test
    public void onReservationCreationButtonPressed_validData_reservationCreated() {
        ReservationPresenter spyPresenter = spy(presenter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = calendar.getTime();
        //Given
        when(model.getParkingSize()).thenReturn(10);

        when(view.getSecurityCode()).thenReturn("sd12");
        when(view.getParkingLotNumberEntered()).thenReturn("2");
        when(view.getStartDateTime()).thenReturn(startDate);
        when(view.getEndDateTime()).thenReturn(endDate);
        when(model.setParkingLotNumber("2")).thenReturn(2);

        when(model.validateSecurityCode("sd12")).thenReturn(true);
        when(model.validateParkingLotNumber(2, 10)).thenReturn(true);
        doReturn(true).when(spyPresenter).validateDates(startDate.getTime(), endDate.getTime());

        when(model.addReservationToParking(any(Reservation.class))).thenReturn(true);

        //When
        presenter.onReservationCreationButtonPressed();
        //Then
        verify(view).showReservationConfirmation();
    }

    @Test
    public void onReservationCreationButtonPressed_anotherReservationInPlace_reservationNotAdded() {
        ReservationPresenter spyPresenter = spy(presenter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = calendar.getTime();
        //Given
        when(model.getParkingSize()).thenReturn(10);

        when(view.getSecurityCode()).thenReturn("sd12");
        when(view.getParkingLotNumberEntered()).thenReturn("2");
        when(view.getStartDateTime()).thenReturn(startDate);
        when(view.getEndDateTime()).thenReturn(endDate);
        when(model.setParkingLotNumber("2")).thenReturn(2);

        when(model.validateSecurityCode("sd12")).thenReturn(true);
        when(model.validateParkingLotNumber(2, 10)).thenReturn(true);

        doReturn(true).when(spyPresenter).validateDates(startDate.getTime(), endDate.getTime());

        when(model.addReservationToParking(any(Reservation.class))).thenReturn(false);

        //When
        presenter.onReservationCreationButtonPressed();
        //Then
        verify(view).showReservationNotAdded();
    }

    @Test
    public void onReservationCreationButtonPressed_invalidLotNumber_catchException() {
        //Given
        when(model.getParkingSize()).thenReturn(2);
        when(view.getParkingLotNumberEntered()).thenReturn("0");

        doThrow(new IllegalArgumentException()).when(model).setParkingLotNumber("0");

        //When
        presenter.onReservationCreationButtonPressed();
        //Then
        verify(view).showInvalidNumber();
    }

    @Test
    public void onReservationCreationButtonPressed_negativeLotNumber_catchException() {
        //Given
        when(model.getParkingSize()).thenReturn(2);
        when(view.getParkingLotNumberEntered()).thenReturn("-15");

        doThrow(new IllegalArgumentException()).when(model).setParkingLotNumber("-15");

        //When
        presenter.onReservationCreationButtonPressed();
        //Then
        verify(view).showInvalidNumber();
    }

    @Test
    public void validateDates_isTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        Assert.assertTrue(startDate > nowDate);
        Assert.assertTrue(startDate < endDate);
        Assert.assertTrue(presenter.validateDates(startDate, endDate));
        verify(view, never()).showInconsistentDates();
        verify(view, never()).showEmptyDates();
    }

    @Test
    public void validateDates_startDateGreaterThanEndDate_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        long nowDate = new Date().getTime();

        Assert.assertTrue(startDate > nowDate);
        Assert.assertTrue(endDate > nowDate);
        Assert.assertTrue(startDate > endDate);
        Assert.assertFalse(presenter.validateDates(startDate, endDate));
        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_bothDatesAreInThePast_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        Assert.assertTrue(startDate > endDate);
        Assert.assertFalse(startDate > nowDate);
        Assert.assertFalse(presenter.validateDates(startDate, endDate));
        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_startDateIsInThePast_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        Assert.assertTrue(startDate < endDate);
        Assert.assertFalse(startDate > nowDate);
        Assert.assertTrue(nowDate < endDate);
        Assert.assertFalse(nowDate < startDate);
        Assert.assertFalse(presenter.validateDates(startDate, endDate));

        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_endDateIsInThePast_isFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -5);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        Assert.assertTrue(startDate > endDate);
        Assert.assertTrue(startDate > nowDate);
        Assert.assertFalse(endDate > nowDate);
        Assert.assertFalse(presenter.validateDates(startDate, endDate));

        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_isNull() {
        Assert.assertFalse(presenter.validateDates(0, 0));
        verify(view).showEmptyDates();
    }

    @Test
    public void selectStartDateAndTime_success() {
        presenter.selectStartDateAndTime();

        verify(view).setStartDateTimeDialog();
    }

    @Test
    public void selectEndDateAndTime_success() {
        presenter.selectEndDateAndTime();

        verify(view).setEndDateTimeDialog();
    }

    @After
    public void destroy() {
        logMockedStatic.close();
    }
}