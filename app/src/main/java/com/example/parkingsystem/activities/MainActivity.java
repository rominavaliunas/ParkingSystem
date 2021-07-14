package com.example.parkingsystem.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingsystem.databinding.ActivityMainBinding;
import com.example.parkingsystem.mvp.model.ParkingModel;
import com.example.parkingsystem.mvp.presenter.ParkingSizePresenter;
import com.example.parkingsystem.mvp.view.ParkingView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ParkingSizePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new ParkingSizePresenter(new ParkingModel(), new ParkingView(this, binding));

        setListeners();
    }

    private void setListeners() {
        binding.buttonParkingSizeSubmitParkingLots.setOnClickListener(view ->
                presenter.onParkingSizeCreationButtonPressed());
    }
}