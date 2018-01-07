package com.example.majid_fit5.al_rajhitakaful.waiting;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.requestdetails.RequestDetailsActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by BASH on 12/28/2017.
 */

public class WaitingProviderActivity extends AppCompatActivity implements WaitingProvidorContract.View {
    private WaitingProvidorContract.Presenter mWaitingPresenter;
    private CountDownTimer mCountDownTimer;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Order mCurrentOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_provider);
        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText(getString(R.string.waiting_toolbar_title));
        init();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void init() {
        mWaitingPresenter = new WaitingProviderPresenter(Injection.provideDataRepository());
        mWaitingPresenter.onBind(WaitingProviderActivity.this);
        //custom progress bar from library
        mCurrentOrder = (Order) getIntent().getExtras().getParcelable(Constants.CURRENT_ORDER);

        //countDown object to count 15 second
        mCountDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                mWaitingPresenter.getProvider(mCurrentOrder.getId());

            }
        };
        Snackbar.make(findViewById(R.id.lay_waiting_provider), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_sent_successfully), Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCountDownCounter();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * open provider details activity
     *
     * @param currentOrder Order object to display the order information
     */
    @Override
    public void onProviderAccept(Order currentOrder) {
        Intent intent = new Intent(this, RequestDetailsActivity.class);
        intent.putExtra(Constants.CURRENT_ORDER, currentOrder);
        startActivity(intent);
        finish();
    }

    /**
     * used when we want to count 15 second
     */
    @Override
    public void startCountDownCounter() {
        mCountDownTimer.start();
    }

    /**
     * display error message if error occur while getting Current order from the server
     *
     * @param error
     */
    @Override
    public void showWaitingError(AlRajhiTakafulError error) {
        Snackbar.make(findViewById(R.id.lay_waiting_provider), error.getMessage() + " : " + error.getCode(), Snackbar.LENGTH_LONG).show();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWaitingPresenter.onDestroy();
    }
}
