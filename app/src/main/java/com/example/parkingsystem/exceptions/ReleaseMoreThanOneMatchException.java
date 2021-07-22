package com.example.parkingsystem.exceptions;

public class ReleaseMoreThanOneMatchException extends Exception {

    public ReleaseMoreThanOneMatchException() {
        super("Error! More than one reservation matches your search");
    }
}
