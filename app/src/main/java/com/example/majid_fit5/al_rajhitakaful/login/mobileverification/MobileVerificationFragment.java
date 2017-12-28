package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/28/2017.
 */

public class MobileVerificationFragment extends BaseFragment {

    private View mRootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login_verification,container,false);
       // init();
        return mRootView;
    }
}
