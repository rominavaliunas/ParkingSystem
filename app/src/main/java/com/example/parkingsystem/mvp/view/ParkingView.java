package com.example.parkingsystem.mvp.view;

import android.app.Activity;
import android.content.Intent;

import com.example.parkingsystem.R;
import com.example.parkingsystem.activities.MenuActivity;
import com.example.parkingsystem.databinding.ActivityMainBinding;

public class ParkingView extends ActivityView {

    private final ActivityMainBinding binding;

    public ParkingView(Activity activity, ActivityMainBinding binding) {
        super(activity);
        this.binding = binding;
    }

    public String getSizeSubmitted() {
        String size = binding.textParkingSizeQuantityLots.getText().toString();
        return size;
    }

    public void showParkingSize(int size) {
        if (getContext() != null) {
            showToast(getContext().getString(R.string.parking_size_msg_quantity, String.valueOf(size)));
        }
    }

    public void showInvalidSizeError() {
        if (getContext() != null) {
            showToast(getContext().getString(R.string.error_number_format_exception));
        }
    }

    public void showInvalidNumber() {
        if (getContext() != null) {
            showToast(getContext().getString(R.string.error_invalid_number_logged));
        }
    }

    public void navigateToMenu(){
        getContext().startActivity(new Intent(getContext(), MenuActivity.class));
    }

}
