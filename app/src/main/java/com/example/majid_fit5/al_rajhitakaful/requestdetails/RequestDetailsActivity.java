package com.example.majid_fit5.al_rajhitakaful.requestdetails;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.createorder.HomeActivity;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.utility.AlertDialogUtility;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;
/**
 * Created by BASH on 12/31/2017.
 */

public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsContract.View, View.OnClickListener {
    private RequestDetailsContract.Presenter mPresenter;
    private Toolbar mToolBar;
    private TextView mToolbarTitle;
    private Order mCurrentOrder;
    private String phoneNumber;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText(getString(R.string.in_route));
        init();

    }
    private void init() {
        mPresenter = new RequestDetailsPresenter(Injection.provideDataRepository());
        mPresenter.onBind(RequestDetailsActivity.this);
        mCurrentOrder = (Order) getIntent().getExtras().getParcelable(Constants.CURRENT_ORDER);
        ((TextView)findViewById(R.id.txv_provider_name)).setText(mCurrentOrder.getProvider().getName());
        ((TextView)findViewById(R.id.txv_car_type)).setText(mCurrentOrder.getProvider().getVehicle());
        ((TextView)findViewById(R.id.txv_eta)).setText(mCurrentOrder.getProvider().getEta().toString());
        findViewById(R.id.btn_call_provider).setOnClickListener(this);
        findViewById(R.id.btn_contact_us).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_call_provider:
                phoneNumber = mCurrentOrder.getProvider().getPhoneNumber();
                checkPhonePermission();
                break;
            case R.id.btn_contact_us:
                phoneNumber = "1100";
                checkPhonePermission();
                break;
            case R.id.btn_cancel:
                cancelOrder(mCurrentOrder.getId());
        }
    }

    /**
     * check the permission to make call, if allowed make the call if denied it will ask for permission
     */
    public void checkPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
        } else {
            callPhone(phoneNumber);
        }
    }
    /**
     * make the phone call
     * @param phoneNumber phone number
     */
    public void callPhone(String phoneNumber) {
        if (phoneNumber != null){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
        else {
            Snackbar.make(findViewById(R.id.lay_request_details),getString(R.string.invalid_phone_number),Snackbar.LENGTH_LONG).show();

        }

    }
    /**
     * cancel the current order after display confirmation message
     * @param orderID order id to be canceled
     */
    public void cancelOrder(final String orderID) {

        new AlertDialogUtility // my custom dialog..
                (this,
                        AlRajhiTakafulApplication.getInstance().getString(R.string.cancel_Order),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.order_confirm_msg),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.ok),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                showLoading();
                                mPresenter.cancelOrder(orderID);

                            }
                        },new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
    }

    /**
     * redirect the user to the main activity,"create order activity"
     */
    @Override
    public void onCancelOrderSuccess() {
        hideLoading();
        //should go to Home Activity
        Toast.makeText(this,getString(R.string.caneled_succ),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();    }

    @Override
    public void onCancelOrderFailure(String message) {
        hideLoading();
        Snackbar.make(findViewById(R.id.lay_request_details),message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(phoneNumber);

                } else {
                    Snackbar.make(findViewById(R.id.lay_request_details),AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_permission_denied),Snackbar.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void showLoading() {
        if(mProgressDialog==null){
            mProgressDialog= ProgressDialog.show(this,"","",false,false);
            mProgressDialog.setProgressDrawable(AlRajhiTakafulApplication.getInstance().getDrawable(R.drawable.custom_progressbar));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
        }else{
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        mProgressDialog.cancel();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
