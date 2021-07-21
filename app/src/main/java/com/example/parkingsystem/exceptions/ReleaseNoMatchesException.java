package com.example.parkingsystem.exceptions;

public class ReleaseNoMatchesException extends Exception {

    public ReleaseNoMatchesException() {
        super("No reservation matches your search");
    }
}
