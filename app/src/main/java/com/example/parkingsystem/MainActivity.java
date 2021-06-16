package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new ParkingPresenter(new Parking(), new ParkingView(this, binding));

        setListeners();
    }

    private void setListeners() {
        showToast();
        binding.submitParkingLots.setOnClickListener(view -> presenter.onParkingSizeCreationButtonPressed());
    }

    public void showToast(){
        Button btn = binding.submitParkingLots;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Parking created",Toast.LENGTH_LONG).show();
            }
        });
    }

}