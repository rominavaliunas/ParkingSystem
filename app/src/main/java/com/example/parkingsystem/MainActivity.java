package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.parkingsystem.databinding.ActivityMainBinding;
import com.example.parkingsystem.mvp.model.Parking;
import com.example.parkingsystem.mvp.presenter.ParkingPresenter;
import com.example.parkingsystem.mvp.view.ParkingView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ParkingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new ParkingPresenter(new Parking(), new ParkingView(this, binding));

        setListeners();
    }

    private void setListeners() {
        binding.submitParkingLots.setOnClickListener(view -> presenter.onParkingSizeCreationButtonPressed());
    }

}