package com.example.parkingsystem.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.ActivityMenuBinding;
import com.example.parkingsystem.fragments.ReservationFragment;

public class MenuActivity extends AppCompatActivity{
    private static final String RESERVATION_FRAGMENT_TAG ="RESERVATION_FRAGMENT";

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    public void setListeners() {
        binding.buttonMenuCreateReservation.setOnClickListener(view -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_menu, new ReservationFragment(), RESERVATION_FRAGMENT_TAG);
            transaction.commit();
        });
    }
    
}
