package com.example.parkingsystem.mvp.view;
import android.content.Context;
import androidx.fragment.app.Fragment;
import com.example.parkingsystem.R;
import com.example.parkingsystem.databinding.FragmentReleaseBinding;
public class FragmentReleaseView extends FragmentView{

    private FragmentReleaseBinding binding;
    private Context context;

    public FragmentReleaseView(Fragment fragmentRef) {
        super(fragmentRef);
    }

    public String getSecurityCode() {
        return binding.textReleaseSecurityCode.getText().toString();
    }

    public String getParkingLotNumberEntered() {
        return binding.textReleaseParkingLot.getText().toString();
    }

    public void showParkingReleasedConfirmation(){
        if (context != null) {
            showToast(context.getString(R.string.confirmation_parking_has_been_released));
        }
    }

    public void showWeCannotFindYourParking(){
        if (context!= null){
            showToast(context.getString(R.string.error_we_cannot_find_your_reservation));
        }
    }

    public void showInvalidParkingNumberForRelease(){
        if (context!= null){
            showToast(context.getString(R.string.error_we_do_not_have_a_parking_lot_with_zero));
        }
    }
}
