package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.presenter.ReleasePresenter;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParkingReleasePresenterTest {

    private ReleasePresenter presenter;
    private ReleaseModel model;
    private FragmentReleaseView view;
    private MockedStatic<Log> logMockedStatic;

    @Before
    public void setup() {
        model = mock(ReleaseModel.class);
        view = mock(FragmentReleaseView.class);
        presenter = new ReleasePresenter(view, model);
        logMockedStatic = mockStatic(Log.class);
    }

    @Test
    public void onReleaseReservationButtonPressed_validData_ParkingReleased() {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);

        doReturn(true).when(spyPresenter).validateSecurityCode("ABC");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(2);

        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(1);

        when(model.releaseParking(new Reservation("ABC", 2))).thenReturn(true);

        Assert.assertTrue(spyPresenter.onReleaseReservationButtonPressed());

        verify(view).showParkingReleasedConfirmation();
    }

    @Test
    public void onReleaseReservationButtonPressed_invalidData_reservationDoesNotExist() {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);

        doReturn(true).when(spyPresenter).validateSecurityCode("ABC");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(2);

        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(0);

        when(model.releaseParking(new Reservation("ABC", 2))).thenReturn(false);

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(view).showWeCannotFindYourParking();
    }

    @Test
    public void onReleaseReservationButtonPressed_validData_reservationHasMoreThanOneMatch() {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);

        doReturn(true).when(spyPresenter).validateSecurityCode("ABC");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(2);

        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(3);

        when(model.releaseParking(new Reservation("ABC", 2))).thenReturn(false);

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(view).showBugMessage();
    }

    @Test
    public void onReleaseReservationButtonPressed_invalidParkingNumberToBeReleased_catchException() {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("");
        when(model.desiredParkingNumberForRelease("")).thenReturn(0);

        doThrow(new IllegalArgumentException()).when(model).desiredParkingNumberForRelease("");

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(view).showNegativeOrZeroParkingNumber();
    }

    @Test
    public void validateParkingLotNumber_isTrue() {
        when(model.getSizeOfParking()).thenReturn(20);
        Assert.assertTrue(presenter.validateParkingLotNumber(10));
        verify(view, never()).showNegativeOrZeroParkingNumber();
        verify(view, never()).showInvalidParkingNumberForRelease();
    }

    @Test
    public void validateParkingLotNumber_parkingLotToBeReleasedIsGreaterThanParkingSize_isFalse() {
        when(model.getSizeOfParking()).thenReturn(20);
        Assert.assertFalse(presenter.validateParkingLotNumber(30));
        verify(view).showInvalidParkingNumberForRelease();
        verify(view, never()).showNegativeOrZeroParkingNumber();
    }

    @Test
    public void validateParkingLotNumber_parkingLotNumberToBeReleasedIsZeroOrNegative_isFalse() {
        when(model.getSizeOfParking()).thenReturn(20);
        Assert.assertFalse(presenter.validateParkingLotNumber(0));
        verify(view).showNegativeOrZeroParkingNumber();
        verify(view, never()).showInvalidParkingNumberForRelease();
    }

    @Test
    public void validateSecurityCode_isTrue() {
        when(view.getSecurityCode()).thenReturn("ADN123");

        Assert.assertTrue(presenter.validateSecurityCode("ADN123"));
        verify(view, never()).showCodeNotComplaint();
    }

    @Test
    public void validateSecurityCode_codeIsEmpty_isFalse() {
        when(view.getSecurityCode()).thenReturn("");

        Assert.assertFalse(presenter.validateSecurityCode(""));
        verify(view).showCodeNotComplaint();
    }

    @Test
    public void validateSecurityCode_codeIsTooLarge_isFalse() {
        when(view.getSecurityCode()).thenReturn("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        Assert.assertFalse(presenter.validateSecurityCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        verify(view).showCodeNotComplaint();
    }

    @After
    public void destroy() {
        logMockedStatic.close();
    }
}