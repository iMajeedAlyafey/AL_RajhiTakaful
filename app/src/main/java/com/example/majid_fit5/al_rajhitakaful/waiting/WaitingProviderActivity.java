package com.example.majid_fit5.al_rajhitakaful.waiting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.provider.Provider;
import com.example.majid_fit5.al_rajhitakaful.requestdetails.RequestDetailsActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;

/**
 * Created by BASH on 12/28/2017.
 */

public class WaitingProviderActivity extends AppCompatActivity implements WaitingProvidorContract.View {
    private WaitingProvidorContract.Presenter mWaitingPresenter;
    private CountDownTimer mCountDownTimer;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Order mCurrentOrder;
    private int counter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_provider);
        mToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("Waiting For Provider");

        init();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void init() {
        mCurrentOrder = (Order) getIntent().getExtras().getParcelable(Constants.CURRENT_ORDER);
        counter = 0;
        mWaitingPresenter = new WaitingProviderPresenter(Injection.provideDataRepository());
        mWaitingPresenter.onBind(WaitingProviderActivity.this);
        Toast.makeText(this, "order number " + mCurrentOrder.getId(), Toast.LENGTH_LONG).show();
        mCountDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mWaitingPresenter.getProvider("D7FE45");

            }
        };
        startCountDownCounter();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onProviderAccept(Order currentOrder) {
        Intent intent = new Intent(this, RequestDetailsActivity.class);
        intent.putExtra(Constants.CURRENT_ORDER,currentOrder);
        startActivity(intent);
        finish();
    }

    @Override
    public void startCountDownCounter() {

        Toast.makeText(this, "round number " + counter, Toast.LENGTH_LONG).show();

        if (counter >0){
            //----------Remoooooooooooove test data
            Provider provider = new Provider();
            provider.setName("Abdulmajeed Ahmed");
            provider.setVehicle("NISSAN");
            provider.setEta(17);
            provider.setPhoneNumber("0541909490");
            mCurrentOrder.setProvider(provider);
            onProviderAccept(mCurrentOrder);
        }else {
            counter++;
            mCountDownTimer.start();
        }
    }

    @Override
    public void showWaitingError(AlRajhiTakafulError error) {
        Toast.makeText(this, error.getMessage() + " : " + error.getCode(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
