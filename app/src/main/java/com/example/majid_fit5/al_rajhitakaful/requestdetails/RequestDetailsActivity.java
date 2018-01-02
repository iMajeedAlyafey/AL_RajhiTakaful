package com.example.majid_fit5.al_rajhitakaful.requestdetails;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;

/**
 * Created by BASH on 12/31/2017.
 */

public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsContract.View, View.OnClickListener {
    private RequestDetailsContract.Presenter mPresenter;
    private Toolbar mToolBar;
    private TextView mToolbarTitle;
    private TextView mTxvProviderName, mTxvCarType, mTxvEta;
    private Button mBtnCallProvider, mBtnContactUs, mBtnCancel;
    private Order mCurrentOrder;
    private String phoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("In Route");
        init();

    }

    private void init() {
        mPresenter = new RequestDetailsPresenter(Injection.provideDataRepository());
        mPresenter.onBind(RequestDetailsActivity.this);
        mCurrentOrder = (Order) getIntent().getExtras().getParcelable(Constants.CURRENT_ORDER);
        mTxvProviderName = findViewById(R.id.txv_provider_name);
        mTxvCarType = findViewById(R.id.txv_car_type);
        mTxvEta = findViewById(R.id.txv_eta);
        mTxvProviderName.setText(mCurrentOrder.getProvider().getName());
        mTxvCarType.setText(mCurrentOrder.getProvider().getVehicle());
        mTxvEta.setText(mCurrentOrder.getProvider().getEta().toString());
        mBtnCallProvider = findViewById(R.id.btn_call_provider);
        mBtnContactUs = findViewById(R.id.btn_contact_us);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnCallProvider.setOnClickListener(this);
        mBtnContactUs.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_call_provider:
                phoneNumber = mCurrentOrder.getProvider().getPhoneNumber();
                makePhoneCall();
                break;
            case R.id.btn_contact_us:
                phoneNumber = "1100";
                makePhoneCall();
                break;
            case R.id.btn_cancel:
                cancelOrder(mCurrentOrder.getId());

        }
    }

    public void makePhoneCall() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
        } else {
            callPhone(phoneNumber);
        }
    }

    public void callPhone(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);

    }

    public void cancelOrder(final String orderID) {
        new AlertDialog.Builder(this).setTitle("Cancel Order")
                .setMessage(getResources().getString(R.string.confirm_Mesaage))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.canelOrder(orderID);
                        //user select ok and the system should cancel the order
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                }).create().show();
        ;
    }

    @Override
    public void onCancelOrder() {
        //should go to Home Activity
        displayErrorMeassage("order Canceled");
    }

    @Override
    public void displayErrorMeassage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(phoneNumber);

                } else {
                    displayErrorMeassage("please give call permission");

                }
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
