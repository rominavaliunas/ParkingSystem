package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.presenter.ParkingSizePresenter;
import com.example.parkingsystem.mvp.view.ParkingView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParkingSizeTest {

    private ParkingSizePresenter presenter;
    private ParkingModel model;
    private ParkingView view;
    private MockedStatic<Log> logMockedStatic;

    @Before
    public void setup() {
        model = mock(ParkingModel.class);
        view = mock(ParkingView.class);
        presenter = new ParkingSizePresenter(model, view);
        logMockedStatic = mockStatic(Log.class);
    }

    @Test
    public void onParkingSizeCreationButtonPressed_validSize_parkingCreated() {
        //Given
        when(view.getSizeSubmitted()).thenReturn("15");
        when(model.getParkingSize()).thenReturn(15);
        //When
        presenter.onParkingSizeCreationButtonPressed();
        //Then
        verify(model).setParkingSize("15");
        verify(view).showParkingSize(15);
    }

    @Test
    public void onParkingSizeCreationButtonPressed_invalidSize_catchException() {
        //Given
        when(view.getSizeSubmitted()).thenReturn("999999999999999999");
        doThrow(new NumberFormatException()).when(model).setParkingSize("999999999999999999");
        //When
        presenter.onParkingSizeCreationButtonPressed();
        //Then
        verify(view).showInvalidSizeError();
    }

    @Test
    public void onParkingSizeCreationButtonPressed_invalidSize_noParkingWithZero() {
        //Given
        when(view.getSizeSubmitted()).thenReturn("0");
        doThrow(new IllegalArgumentException()).when(model).setParkingSize("0");
        //When
        presenter.onParkingSizeCreationButtonPressed();
        //Then
        verify(view).showInvalidNumber();
    }

    @After
    public void destroy() {
        logMockedStatic.close();
    }
}