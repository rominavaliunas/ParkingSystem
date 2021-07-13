package com.example.parkingsystem.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parkingsystem.databinding.FragmentReservationBinding;
import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.mvp.model.ReservationModel;
import com.example.parkingsystem.mvp.presenter.ReservationPresenter;
import com.example.parkingsystem.mvp.view.FragmentReservationView;

public class ReservationFragment extends Fragment {

    public static final String PARKING_KEY = "PARKING";
    private FragmentReservationBinding binding;
    private ReservationPresenter presenter;

    public ReservationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationBinding.inflate(inflater, container, false);
        binding.startInputDateAndTime.setInputType(InputType.TYPE_NULL);
        binding.endInputDateAndTime.setInputType(InputType.TYPE_NULL);
        Parking parking = getArguments().getParcelable(PARKING_KEY);
        presenter = new ReservationPresenter(new ReservationModel(parking), new FragmentReservationView(this, binding));
        setListeners();

        return binding.getRoot();
    }

    public void setListeners() {
        binding.startInputDateAndTime.setOnClickListener(view -> presenter.selectStartDateAndTime());
        binding.endInputDateAndTime.setOnClickListener(view -> presenter.selectEndDateAndTime());
        binding.buttonReservationSubmit.setOnClickListener(view -> {
            presenter.onReservationCreationButtonPressed();
        });
    }
}