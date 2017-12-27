package com.example.majid_fit5.al_rajhitakaful.login;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityUtility.addFragmentToActivity( getFragmentManager(),new MobilePhoneInsertionFragment(),R.id.content_frame);

    }

}
