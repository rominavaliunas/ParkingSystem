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

    public int getSizeSubmitted(){
        int size=0;
        try{
            size = Integer.parseInt(binding.quantityParkingLots.getText().toString());
        }catch (NumberFormatException ex){
            Log.e(ParkingView.class.getSimpleName(), ex.toString());
        }
        return size;
    }

    public void showToast(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
    }

    public void showParkingSize(int size){
        showToast(getContext().getString(R.string.message_parking_size, String.valueOf(size)));
    }
}
