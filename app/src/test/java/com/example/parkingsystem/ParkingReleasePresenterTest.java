package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReleasePresenter;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

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
    private Reservation reservation;
    private String securityCode = "";
    private int parkingNumber = 0;
    private ReservationModel reservationModel;

    @Before
    public void setup() {
        reservationModel = mock(ReservationModel.class);
        model = mock(ReleaseModel.class);
        view = mock(FragmentReleaseView.class);
        presenter = new ReleasePresenter(view, model);
        reservation = new Reservation(securityCode, parkingNumber);
        logMockedStatic = mockStatic(Log.class);
    }

    @Test
    public void onReleaseReservationButtonPressed_validData_ParkingReleased() {
        ReleasePresenter spyPresenter = spy(presenter);

        securityCode = "ABC";
        parkingNumber = 2;

        Reservation reservationAdded = new Reservation(securityCode, parkingNumber);
        reservationModel.addReservationToParking(reservationAdded);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);

        doReturn(true).when(spyPresenter).validateSecurityCode("ABC");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(2);

        when(model.releaseParking(new Reservation("ABC", 2))).thenReturn(true);

        //TODO Error for asking to change it to AssertFalse
        Assert.assertTrue(presenter.onReleaseReservationButtonPressed());

        verify(view).showParkingReleasedConfirmation();
    }

    @Test
    public void onReleaseReservationButtonPressed_invalidData_reservationDoesNotExist() {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("1R");
        when(view.getLotNumberToBeReleased()).thenReturn("86");
        when(model.desiredParkingNumberForRelease("86")).thenReturn(86);

        doReturn(true).when(spyPresenter).validateSecurityCode("1R");
        doReturn(true).when(spyPresenter).validateParkingLotNumber(86);

        when(model.releaseParking(new Reservation("1R", 86))).thenReturn(false);

        Assert.assertFalse(presenter.onReleaseReservationButtonPressed());

        verify(view).showWeCannotFindYourParking();
    }

    @Test
    public void onReleaseReservationButtonPressed_invalidParkingNumberToBeReleased_catchException() {
        when(model.desiredParkingNumberForRelease(view.getLotNumberToBeReleased())).thenReturn(0);

        doThrow(new IllegalArgumentException()).when(model).desiredParkingNumberForRelease("0");

        presenter.onReleaseReservationButtonPressed();
        verify(view).showNegativeOrZeroParkingNumber();
    }

    @Test
    public void onReleaseReservationButtonPressed_invalidData_moreThanOneParkingMatchesTheDescription() {
        ReleasePresenter spyPresenter = spy(presenter);

        securityCode = "ABC";
        parkingNumber = 2;

        Reservation reservationAdded = new Reservation(securityCode, parkingNumber);
        reservationModel.addReservationToParking(reservationAdded);

        Reservation secondReservationWithSameData = new Reservation(securityCode, parkingNumber);
        reservationModel.addReservationToParking(secondReservationWithSameData);

        when(view.getSecurityCode()).thenReturn(securityCode);
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(parkingNumber);

        doReturn(true).when(spyPresenter).validateSecurityCode(securityCode);
        doReturn(true).when(spyPresenter).validateParkingLotNumber(parkingNumber);

        when(model.releaseParking(new Reservation(securityCode, parkingNumber))).thenReturn(false);

        //TODO Error for asking to change it to AssertFalse
        Assert.assertFalse(presenter.onReleaseReservationButtonPressed());

        verify(view).showBugMessage();
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

    //TODO Why is it not taking the zero or -1?
    @Test
    public void validateParkingLotNumber_parkingLotNumberToBeReleasedIsZeroOrNegative_isFalse() {
        when(model.getSizeOfParking()).thenReturn(20);
        Assert.assertFalse(presenter.validateParkingLotNumber(-1));
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