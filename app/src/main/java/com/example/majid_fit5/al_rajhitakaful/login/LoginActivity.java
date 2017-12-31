package com.example.majid_fit5.al_rajhitakaful.login;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;

import android.util.Log;
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
        ActivityUtility.addFragmentToActivity( getFragmentManager(),new MobilePhoneInsertionFragment(),R.id.content_frame);
        //checkPermissions();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed(); // For dealing with the back arrow.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == REQUEST_CODE) {
                if (grantResults.length < 0)
                    for (int i = 0; i < permissions.length; i++) {
                        switch (permissions[i]) {
                            case android.Manifest.permission.RECEIVE_SMS:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "RECEIVE_SMS is granted");
                                }
                                break;
                            case android.Manifest.permission.READ_SMS:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "READ_SMS is granted");

                                }
                                break;
                            case android.Manifest.permission.ACCESS_FINE_LOCATION:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "sms granted");

                                }
                                break;
                            case android.Manifest.permission.ACCESS_COARSE_LOCATION:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "location granted");

                                }
                                break;
                            case android.Manifest.permission.CAMERA:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "CAMERA granted");

                                }
                                break;
                            case android.Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "WRITE_EXTERNAL_STORAGE");

                                }
                                break;
                            case android.Manifest.permission.READ_EXTERNAL_STORAGE:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                    Log.e("msg", "READ_EXTERNAL_STORAGE");

                                }
                                break;
                        }
                    }
            }
        }
}


