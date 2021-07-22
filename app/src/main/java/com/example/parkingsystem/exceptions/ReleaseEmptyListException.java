package com.example.parkingsystem.exceptions;

public class ReleaseEmptyListException extends Exception {

    public ReleaseEmptyListException() {
        super("No reservation has been created yet");
    }
}