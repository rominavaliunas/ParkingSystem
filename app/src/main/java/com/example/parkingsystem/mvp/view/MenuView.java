package com.example.parkingsystem.mvp.view;

import android.app.Activity;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.ActivityMainBinding;
import com.example.parkingsystem.databinding.ActivityMenuBinding;

public class MenuView extends ActivityView {

    private final ActivityMenuBinding binding;

    public MenuView(Activity activity, ActivityMenuBinding binding) {
        super(activity);
        this.binding = binding;
    }

    public String getSecurityCode() {
        String code = "";
        return code;
    }

    public void showReservationCreated() {
        if (getContext() != null) {
            showToast(getContext().getString(R.string.reservation_confirmation));
        }
    }

}
