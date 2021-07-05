package com.example.parkingsystem.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.ActivityMenuBinding;
import com.example.parkingsystem.fragments.ReservationFragment;
import com.example.parkingsystem.mvp.presenter.MenuPresenter;

import static com.example.parkingsystem.fragments.ReservationFragment.PARKING_KEY;

public class MenuActivity extends AppCompatActivity {
    public static final String PARKING_SIZE = "SIZE";
    public static final String RESERVATION = "RESERVATION";
    private static final String RESERVATION_FRAGMENT_TAG = "RESERVATION_FRAGMENT";

    private ActivityMenuBinding binding;
    private int parkingSize;
    private MenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        parkingSize = getIntent().getIntExtra(PARKING_SIZE, 0);

        // ToDo get reservation object
        getReservation();

        presenter = new MenuPresenter(parkingSize);

        setListeners();
    }

    private void getReservation() {

    }

    public void setListeners() {
        binding.buttonMenuCreateReservation.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARKING_KEY, presenter.getParking());
            ReservationFragment reservationFragment = new ReservationFragment();
            reservationFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_menu, reservationFragment, RESERVATION_FRAGMENT_TAG);
            transaction.commit();
        });
    }

}
