package com.example.majid_fit5.al_rajhitakaful.login;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {
    private final int REQUEST_CODE=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.title_sign);
        ActivityUtility.addFragmentToActivity( getFragmentManager(),new MobilePhoneInsertionFragment(),R.id.content_frame,"MobilePhoneInsertionFragment");
        checkPermissions();
    }
     // I used the toolbar in this activity since there is no action bar button required to use presenter or api service. Ex: no log out button used to exist and clear the preference and call api service.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // For dealing with the back arrow.
                ActivityUtility.addFragmentToActivity(getFragmentManager(),new MobilePhoneInsertionFragment(),R.id.content_frame,"MobileInsertionFragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermissions() {
        if ( Build.VERSION.SDK_INT >= 23){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
            }, REQUEST_CODE);}
        }
    }

}


