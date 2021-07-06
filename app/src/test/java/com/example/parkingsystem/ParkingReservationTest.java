package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReservationPresenter;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ParkingReservationTest {

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
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        //Given
        when(view.getSecurityCode()).thenReturn("sd12");
        when(view.getParkingLotNumberEntered()).thenReturn("2");
        when(view.getStartDateTime()).thenReturn(startDate);
        //When no importa en el orden en que va
        presenter.onReservationCreationButtonPressed();
        //Then
    }

    //ToDo Validate every method for validation of Data

}
