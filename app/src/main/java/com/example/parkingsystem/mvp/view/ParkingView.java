package com.example.parkingsystem.mvp.view;

import android.app.Activity;
import android.util.Log;
import com.example.parkingsystem.databinding.ActivityMainBinding;

public class ParkingView extends ActivityView {

    private final ActivityMainBinding binding;

    public ParkingView(Activity activity, ActivityMainBinding binding){
        super(activity);
        this.binding = binding;
    }

    public int getSizeSubmitted(){
        int size=0;
        try{
            size = Integer.parseInt(binding.quantityParkingLots.getText().toString());
        }catch (NumberFormatException ex){
            Log.e(ParkingView.class.getSimpleName(), ex.toString());
        }
        return size;
    }
}
