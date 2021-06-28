package com.example.parkingsystem.mvp.presenter;

import android.util.Log;

import com.example.parkingsystem.fragments.ReservationFragment;
import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.view.MenuView;

public class ReservationPresenter {

    private ParkingModel reservation;
    private MenuView menuView;

    public ReservationPresenter(ParkingModel model, MenuView view) {
        this.reservation = model;
        this.menuView = view;
    }
}
