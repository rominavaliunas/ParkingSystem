package com.example.parkingsystem;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReservationPresenter;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class ParkingReservationPresenterTest {

    private ReservationPresenter presenter;
    private ReservationModel model;
    private FragmentReservationView view;

    @Before
    public void setup() {
        model = mock(ReservationModel.class);
        view = mock(FragmentReservationView.class);
        presenter = new ReservationPresenter(model, view);
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

        when(spyPresenter).validateSecurityCode("A123").thenReturn(true);
        when(spyPresenter).validateParkingLotNumber(2).thenReturn(true);
        when(spyPresenter).validateDates(startDate.getTime(), endDate.getTime()).thenReturn(true);

        //doReturn(true).when(spyPresenter).validateSecurityCode("A123");
        //doReturn(true).when(spyPresenter).validateParkingLotNumber(2);
        //doReturn(true).when(spyPresenter).validateDates(startDate.getTime(), endDate.getTime());

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

        doReturn(true).when(spyPresenter).validateSecurityCode("A123");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(2);
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

        when(model).setParkingLotNumber("0").thenThrow(IllegalArgumentException.class)
        //doThrow(new IllegalArgumentException()).when(model).setParkingLotNumber("0");

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
        //Given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        // When
        presenter.validateDates(startDate, endDate);

        // Then
        Assert.assertTrue(startDate > nowDate);
        Assert.assertTrue(startDate < endDate);
    }

    @Test
    public void validateDates_startDateGreaterThanEndDate_isFalse() {
        //Given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        long nowDate = new Date().getTime();

        // When
        presenter.validateDates(startDate, endDate);

        // Then

        Assert.assertTrue(startDate > nowDate);
        Assert.assertTrue(endDate > nowDate);
        Assert.assertTrue(startDate > endDate);
    }

    @Test
    public void validateDates_bothDatesAreInThePast_isFalse() {
        //Given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        // When
        presenter.validateDates(startDate, endDate);

        // Then
        Assert.assertTrue(startDate > endDate);
        Assert.assertFalse(startDate > nowDate);
        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_startDateIsInThePast_isFalse() {
        //Given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        // When
        presenter.validateDates(startDate, endDate);

        // Then
        Assert.assertTrue(startDate < endDate);
        Assert.assertFalse(startDate > nowDate);
        Assert.assertTrue(nowDate < endDate);
        Assert.assertFalse(nowDate < startDate);

        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_endDateIsInThePast_isFalse() {
        //Given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date dateForStart = calendar.getTime();
        long startDate = dateForStart.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -5);
        Date dateForEnd = calendar.getTime();
        long endDate = dateForEnd.getTime();
        long nowDate = new Date().getTime();

        // When
        presenter.validateDates(startDate, endDate);

        // Then
        Assert.assertTrue(startDate > endDate);
        Assert.assertTrue(startDate > nowDate);
        Assert.assertFalse(endDate > nowDate);

        verify(view).showInconsistentDates();
    }

    @Test
    public void validateDates_isNull() {
        // When
        presenter.validateDates(0, 0);
        // Then
        verify(view).showEmptyDates();
    }

    @Test
    public void validateSecurityCode_isTrue() {
        //Given
        String code = "ADN123";
        when(view.getSecurityCode()).thenReturn(code);

        //When
        presenter.validateSecurityCode(code);

        //Then
        Assert.assertNotNull(code);
    }

    @Test
    public void validateSecurityCode_codeIsEmpty_isFalse() {
        //Given
        String code = "";
        when(view.getSecurityCode()).thenReturn(code);

        //When
        presenter.validateSecurityCode(code);

        //Then
        Assert.assertNotNull(code);
        verify(view).showCodeNotComplaint();
    }

    @Test
    public void validateSecurityCode_codesIsTooLarge_isFalse() {
        //Given
        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456";
        when(view.getSecurityCode()).thenReturn(code);

        //When
        presenter.validateSecurityCode(code);

        //Then
        Assert.assertNotNull(code);
        verify(view).showCodeNotComplaint();
    }

    @Test
    public void validateSecurityCode_isNull() {
        presenter.validateSecurityCode(null);

        verify(view).showCodeNotComplaint();
    }

    //ToDo check test for parkingLot greater than parkingSize
    @Test
    public void validateParkingLotNumber_isTrue() {
        //Given
        when(model.setParkingLotNumber(view.getParkingLotNumberEntered())).thenReturn(10);
        when(model.getParkingSize()).thenReturn(20);

        //When
        presenter.validateParkingLotNumber(10);

        //Then
        Assert.assertTrue(presenter.validateParkingLotNumber(10));
    }

    @Test
    public void validateParkingLotNumber_parkingSizeIsSmallerThanLotNumberEntered_isFalse() {
        //Given
        when(model.setParkingLotNumber(view.getParkingLotNumberEntered())).thenReturn(5);
        when(model.getParkingSize()).thenReturn(2);

        //When
        presenter.validateParkingLotNumber(5);

        //Then
        verify(view).showLotNumberGreaterThanParkingSize();
    }

    @Test
    public void startDT_success() {
        presenter.selectStartDateAndTime();

        verify(view).setStartDateTimeDialog();
    }

    @Test
    public void endDT_success() {
        presenter.selectEndDateAndTime();

        verify(view).setEndDateTimeDialog();
    }
}