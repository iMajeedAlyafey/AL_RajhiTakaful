package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;

public class MobileVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification_activity);
        Bundle bundle= getIntent().getExtras();
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.title_verify_account);

        //ActivityUtility.addFragmentToActivity( getFragmentManager(),new MobileVerificationFragment(),R.id.content_frame);
        Toast.makeText(getApplicationContext(), bundle.getString("phoneNumber"), Toast.LENGTH_SHORT).show();


    }
}
