package com.example.parkingsystem.mvp.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class FragmentView {
    private final WeakReference<Fragment> fragmentWeakReference;

    public FragmentView(Fragment fragmentRef) {
        this.fragmentWeakReference = new WeakReference<>(fragmentRef);
    }

    @Nullable
    public Activity getActivity() {
        if (getFragment() != null) {
            return getFragment().getActivity();
        }
        return null;
    }

    @Nullable
    public Fragment getFragment() {
        return fragmentWeakReference.get();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.getFragmentManager();
        }
        return null;
    }

    @Nullable
    public void showToast(String text) {
        if (getContext() != null) {
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        }
    }
}