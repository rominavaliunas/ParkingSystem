package com.example.parkingsystem.entities;

import java.util.Date;

public class Reservation {
    private String securityCode;
    private int parkingLot;
    private Date startDateTime;
    private Date endDateTime;

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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date start) {
        this.startDateTime = start;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date end) {
        this.endDateTime = end;
    }
}
