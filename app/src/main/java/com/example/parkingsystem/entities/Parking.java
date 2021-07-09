package com.example.parkingsystem.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Parking implements Parcelable {
    public static final Parcelable.Creator<Parking> CREATOR = new Parcelable.Creator<Parking>() {
        @Override
        public Parking createFromParcel(Parcel source) {
            return new Parking(source);
        }

        @Override
        public Parking[] newArray(int size) {
            return new Parking[size];
        }
    };
    private int parkingSize;
    private ArrayList<Reservation> reservationsList;

    public Parking(int parkingSize) {
        this.parkingSize = parkingSize;
        reservationsList = new ArrayList<>();
    }

    protected Parking(Parcel in) {
        this.parkingSize = in.readInt();
        this.reservationsList = new ArrayList<Reservation>();
        in.readList(this.reservationsList, Reservation.class.getClassLoader());
    }

    public int getParkingSize() {
        return parkingSize;
    }

    public void setParkingSize(int parkingSize) {
        this.parkingSize = parkingSize;
    }

    public ArrayList<Reservation> getReservationsList() {
        return reservationsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.parkingSize);
        dest.writeList(this.reservationsList);
    }

    public void readFromParcel(Parcel source) {
        this.parkingSize = source.readInt();
        this.reservationsList = new ArrayList<Reservation>();
        source.readList(this.reservationsList, Reservation.class.getClassLoader());
    }
}