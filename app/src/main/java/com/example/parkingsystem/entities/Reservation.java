package com.example.parkingsystem.entities;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Reservation implements Parcelable {

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel source) {
            return new Reservation(source);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    private String securityCode;
    private int parkingLot;
    private long startDateTime;
    private long endDateTime;

    public Reservation(String code, int number, long startDateTime, long endDateTime) {
        this.securityCode = code;
        this.parkingLot = number;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    protected Reservation(Parcel in) {
        this.securityCode = in.readString();
        this.endDateTime = in.readLong();
        this.startDateTime = in.readLong();
        this.parkingLot = in.readInt();
    }

    public Reservation(String code, int parkingNumber) {
        this.parkingLot = parkingNumber;
        this.securityCode = code;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String code) {
        this.securityCode = code;
    }

    public int getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(int parkingNumber) {
        this.parkingLot = parkingNumber;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(long start) {
        this.startDateTime = start;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(long end) {
        this.endDateTime = end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.securityCode);
        dest.writeLong(this.endDateTime);
        dest.writeLong(this.startDateTime);
        dest.writeInt(this.parkingLot);
    }

    public void readFromParcel(Parcel source) {
        this.securityCode = source.readString();
        this.endDateTime = source.readLong();
        this.startDateTime = source.readLong();
        this.parkingLot = source.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return parkingLot == that.parkingLot &&
                securityCode.equals(that.securityCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(securityCode, parkingLot);
    }
}