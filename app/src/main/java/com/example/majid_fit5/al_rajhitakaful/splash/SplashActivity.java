package com.example.majid_fit5.al_rajhitakaful.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.MainActivity;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

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
    public void strartLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void strartCreateOrder() {

    }

    @Override
    public void startShowOrder(int orderID) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorMessage(AlRajhiTakafulError alRajhiTakafulError) {
        Toast.makeText(this,alRajhiTakafulError.getMessage()+" : "+alRajhiTakafulError.getCode(),Toast.LENGTH_LONG);
    }


}
