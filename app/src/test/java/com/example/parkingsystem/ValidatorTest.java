package com.example.parkingsystem;

import com.example.parkingsystem.entities.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void setup() {
        validator = new Validator();
    }

    @Test
    public void validateSecurityCode_isTrue() {
        Assert.assertTrue(validator.validateSecurityCode("ABC"));
    }

    @Test
    public void validateSecurityCode_codeIsEmpty_isFalse() {
        Assert.assertFalse(validator.validateSecurityCode(""));
    }

    @Test
    public void validateSecurityCode_codeIsLargerThanExpected_isFalse() {
        Assert.assertFalse(validator.validateSecurityCode("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    @Test
    public void validateParkingLotNumber_isTrue() {
        Assert.assertTrue(validator.validateParkingLotNumber(2, 10));
    }

    @Test
    public void validateParkingLotNumber_parkingNumberEqualsParkingSize_isTrue() {
        Assert.assertTrue(validator.validateParkingLotNumber(10, 10));
    }

    @Test
    public void validateParkingLotNumber_parkingNumberGreaterThanParkingSize_isFalse() {
        Assert.assertFalse(validator.validateParkingLotNumber(10, 2));
    }
}
