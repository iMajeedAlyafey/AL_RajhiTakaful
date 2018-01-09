package com.example.majid_fit5.al_rajhitakaful.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.MainActivity;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.createorder.HomeActivity;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.login.LoginActivity;
import com.example.majid_fit5.al_rajhitakaful.requestdetails.RequestDetailsActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;
import com.example.majid_fit5.al_rajhitakaful.waiting.WaitingProviderActivity;
import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by BASH on 12/27/2017.
 */

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    private SplashContract.Presenter mSlashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlashPresenter = new SplashPresenter(Injection.provideDataRepository());
        mSlashPresenter.onBind(SplashActivity.this);
        mSlashPresenter.checkUserLoginStatues();

        // Test the firebase
        FirebaseCrash.report(new Exception("This is just for testing the firebase"));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * open login activity
     */
    @Override
    public void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * open HomeActivity, "open Create Order"
     */
    @Override
    public void startCreateOrder() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * check the current order of the user, if the order accepted by provider or no, then open the corresponding activity.
     * @param currentOrder current oder for the user from the server
     */
    @Override
    public void startShowOrder(Order currentOrder) {
        Intent intent;
        if (currentOrder.getProvider() == null) {
            intent = new Intent(this, WaitingProviderActivity.class);
        } else {
            intent = new Intent(this, RequestDetailsActivity.class);
        }
        intent.putExtra(Constants.CURRENT_ORDER, currentOrder);
        startActivity(intent);
        finish();
    }

    /**
     * display error message if there is a problem when getting the current order from the server
     * @param error object contain error message and code
     */
    @Override
    public void showErrorMessage(AlRajhiTakafulError error) {
        if(error.getCode()==503|| error.getCode()==408) { // Network connection error = NO internet connection.
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ErrorMsg", error.getMessage());
            startActivity(intent);
            finish();
        }else Toast.makeText(this, error.getMessage()+":#####"+error.getCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSlashPresenter.onDestroy();
    }
}
