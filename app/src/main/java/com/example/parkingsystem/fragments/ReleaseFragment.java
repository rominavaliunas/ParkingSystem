package com.example.parkingsystem.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parkingsystem.databinding.FragmentReleaseBinding;
import com.example.parkingsystem.entities.Parking;
import com.example.parkingsystem.entities.Validator;
import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.presenter.ReleasePresenter;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

public class ReleaseFragment extends Fragment {

    public interface ReleaseDelegate {
        void onReleaseButtonPressed(Parking parking);
    }

    public static final String PARKING_ARGUMENT = "PARKING";
    private FragmentReleaseBinding binding;
    private ReleasePresenter presenter;
    private ReleaseDelegate delegate;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ReleaseDelegate) {
            delegate = (ReleaseDelegate) context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReleaseBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            Parking parking = getArguments().getParcelable(PARKING_ARGUMENT);
            presenter = new ReleasePresenter(new FragmentReleaseView(this, binding), new ReleaseModel(parking, new Validator()));
            setListeners();
        }

        return binding.getRoot();
    }

    public void setListeners() {
        binding.buttonReleaseSubmit.setOnClickListener(view -> {
            if (presenter.onReleaseReservationButtonPressed()) {
                delegate.onReleaseButtonPressed(presenter.getReservationsOnTheParking());
            }
        });
    }
}