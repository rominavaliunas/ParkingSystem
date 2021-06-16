package com.example.parkingsystem.mvp.model;

import java.util.ArrayList;

public class Parking {

    private int parkingSize;

    private ArrayList<ParkingLot> parkingLots;

    public Parking (){
        this.parkingLots = new ArrayList<>();
    }

    public int getParkingSize (){
        return this.parkingSize;
    }

    public void setParkingSize (int size){
        this.parkingSize = size;
    }


}
