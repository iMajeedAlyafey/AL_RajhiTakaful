package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.title_home);
        (findViewById(R.id.home_logout)).setOnClickListener(this);
        ActivityUtility.addFragmentToActivity( getFragmentManager(),new CreateOrderMapFragment(),R.id.content_frame,"CreateOrderMapFragment"); // fragmentTestFrame in the HomeActivity layout
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_logout: // of home
                Toast.makeText( this, " Log out", Toast.LENGTH_LONG).show();
                PrefUtility.destroyToken(AlRajhiTakafulApplication.getInstance());
                finish();
                break;
        }
    }
}
