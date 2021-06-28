package com.example.parkingsystem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.FragmentReservationBinding;

public class ReservationFragment extends Fragment {

    private FragmentReservationBinding binding;


    public ReservationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View viewFragment = inflater.inflate(R.layout.fragment_reservation, container, false);
        return viewFragment;
    }

}
