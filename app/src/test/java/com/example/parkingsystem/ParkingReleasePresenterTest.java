package com.example.parkingsystem;

import android.util.Log;

import com.example.parkingsystem.mvp.model.ReleaseModel;
import com.example.parkingsystem.mvp.presenter.ReleasePresenter;
import com.example.parkingsystem.mvp.view.FragmentReleaseView;

import org.junit.Before;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class ParkingReleasePresenterTest {

    private ReleasePresenter presenter;
    private ReleaseModel model;
    private FragmentReleaseView view;
    private MockedStatic<Log> logMockedStatic;

    @Before
    public void setup() {
        model = mock(ReleaseModel.class);
        view = mock(FragmentReleaseView.class);
        presenter = new ReleasePresenter(view, model);
        logMockedStatic = mockStatic(Log.class);
    }


}