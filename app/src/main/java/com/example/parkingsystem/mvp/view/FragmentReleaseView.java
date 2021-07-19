package com.example.parkingsystem.mvp.view;

import androidx.fragment.app.Fragment;

import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.FragmentReleaseBinding;

public class FragmentReleaseView extends FragmentView {

    private FragmentReleaseBinding binding;

    public FragmentReleaseView(Fragment fragmentRef, FragmentReleaseBinding binding) {
        super(fragmentRef);
        this.binding = binding;
    }

    public String getSecurityCode() {
        return binding.textReleaseSecurityCode.getText().toString();
    }

    public String getLotNumberToBeReleased() {
        return binding.textReleaseParkingLot.getText().toString();
    }

    public void showParkingReleasedConfirmation() {
        showToast(getContext().getString(R.string.confirmation_parking_has_been_released));
    }

    public void showWeCannotFindYourParking() {
        showToast(getContext().getString(R.string.error_we_cannot_find_your_reservation));
    }

    public void showInvalidParkingNumberForRelease() {
        showToast(getContext().getString(R.string.error_lot_number_bigger_than_parking_size));
    }

    public void showNegativeOrZeroParkingNumber() {
        showToast(getContext().getString(R.string.error_invalid_parking_lot_number_logged));
    }

    public void showCodeNotComplaint() {
        showToast(getContext().getString(R.string.error_security_code_not_compliant));
    }

    public void showBugMessage() {
        showToast(getContext().getString(R.string.error_more_than_one_reservation_matches));
    }
}
