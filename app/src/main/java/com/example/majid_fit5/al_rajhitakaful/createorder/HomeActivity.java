package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.title_home);
        ActivityUtility.addFragmentToActivity( getFragmentManager(),new CreateOrderMapFragment(),R.id.content_frame,"CreateOrderMapFragment"); // fragmentTestFrame in the HomeActivity layout
    }
}
