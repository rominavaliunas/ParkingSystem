package com.example.parkingsystem.mvp.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.ActivityMainBinding;

public class ParkingView extends ActivityView {

    private final ActivityMainBinding binding;

    public ParkingView(Activity activity, ActivityMainBinding binding){
        super(activity);
        this.binding = binding;
    }

    public String getSizeSubmitted(){
        String size="";
            size = binding.quantityParkingLots.getText().toString();
        return size;
    }

    public void showToast(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
    }

    public void showParkingSize(int size){
        showToast(getContext().getString(R.string.message_parking_size, String.valueOf(size)));
    }

    public void showInvalidSizeError(){
        showToast(getContext().getString(R.string.error_message_number_format_exception));
    }
}
