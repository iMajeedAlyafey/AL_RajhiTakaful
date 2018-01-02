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

/**
 * Created by BASH on 12/27/2017.
 */

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    private SplashContract.Presenter mSlashPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlashPresenter =new SplashPresenter(Injection.provideDataRepository());
        mSlashPresenter.onBind(SplashActivity.this);
        mSlashPresenter.checkUserLoginStatues();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startCreateOrder() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startShowOrder(Order currentOrder) {
        if (currentOrder.getProvider() == null){
            Intent intent = new Intent(this, WaitingProviderActivity.class);
            intent.putExtra(Constants.CURRENT_ORDER,currentOrder);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, RequestDetailsActivity.class);
            intent.putExtra(Constants.CURRENT_ORDER,currentOrder);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showErrorMessage(AlRajhiTakafulError alRajhiTakafulError) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this,alRajhiTakafulError.getMessage()+" : "+alRajhiTakafulError.getCode(),Toast.LENGTH_LONG).show();
    }


}
