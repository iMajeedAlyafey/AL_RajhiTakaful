package com.example.majid_fit5.al_rajhitakaful;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.txt_error_msg)).setText(getIntent().getStringExtra("ErrorMsg"));
        // coding the splash now..
    }

}
