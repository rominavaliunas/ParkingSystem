package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.entities.Reservation;
import com.example.parkingsystem.exceptions.ReleaseEmptyListException;
import com.example.parkingsystem.exceptions.ReleaseMoreThanOneMatchException;
import com.example.parkingsystem.exceptions.ReleaseNoMatchesException;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.presenter.ReleasePresenter;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
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
    public void onReleaseReservationButtonPressed_validData_ParkingReleased() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);
        when(model.validateParkingLotNumber(2, model.getSizeOfParking())).thenReturn(true);
        when(model.validateSecurityCode("ABC")).thenReturn(true);
        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(1);

        Assert.assertTrue(spyPresenter.onReleaseReservationButtonPressed());

        verify(model).releaseParking(new Reservation("ABC", 2));
        verify(view).showParkingReleasedConfirmation();
    }

    @Test
    public void onReleaseReservationButtonPressed_noMatches_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);
        when(model.validateParkingLotNumber(2, model.getSizeOfParking())).thenReturn(true);
        when(model.validateSecurityCode("ABC")).thenReturn(true);
        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(0);

        doThrow(new ReleaseNoMatchesException()).when(model).releaseParking(new Reservation("ABC", 2));

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(model).releaseParking(new Reservation("ABC", 2));
        verify(view).showWeCannotFindYourParking();
    }

    @Test
    public void onReleaseReservationButtonPressed_reservationHasMoreThanOneMatch_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);
        when(model.validateSecurityCode("ABC")).thenReturn(true);
        when(model.validateParkingLotNumber(2, model.getSizeOfParking())).thenReturn(true);
        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(3);

        doThrow(new ReleaseMoreThanOneMatchException()).when(model).releaseParking(new Reservation("ABC", 2));

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(model).releaseParking(new Reservation("ABC", 2));
        verify(view).showBugMessage();
    }

    @Test
    public void onReleaseReservationButtonPressed_noReservationInPlaceEmptyList_catchException() throws ReleaseMoreThanOneMatchException, ReleaseNoMatchesException, ReleaseEmptyListException {
        ReleasePresenter spyPresenter = spy(presenter);

        when(view.getSecurityCode()).thenReturn("ABC");
        when(view.getLotNumberToBeReleased()).thenReturn("2");
        when(model.desiredParkingNumberForRelease("2")).thenReturn(2);
        when(model.validateSecurityCode("ABC")).thenReturn(true);
        when(model.validateParkingLotNumber(2, model.getSizeOfParking())).thenReturn(true);
        when(model.numberOfMatchesOfTheReservation(any(Reservation.class))).thenReturn(0);

        doThrow(new ReleaseEmptyListException()).when(model).releaseParking(new Reservation("ABC", 2));

        Assert.assertFalse(spyPresenter.onReleaseReservationButtonPressed());

        verify(model).releaseParking(new Reservation("ABC", 2));
        verify(view).showNoReservationInPlaceYet();
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

    @After
    public void destroy() {
        logMockedStatic.close();
    }
}