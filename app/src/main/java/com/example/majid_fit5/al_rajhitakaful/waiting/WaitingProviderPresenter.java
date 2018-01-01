package com.example.majid_fit5.al_rajhitakaful.waiting;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BASH on 12/30/2017.
 */

public class WaitingProviderPresenter implements WaitingProvidorContract.Presenter {
    private WeakReference<WaitingProvidorContract.View> mWaitingView;
    private DataRepository mRepository;
    int enterTimer = 0;

    public WaitingProviderPresenter(@NonNull DataRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void onBind(@NonNull WaitingProvidorContract.View view) {
        if (view != null)
            mWaitingView = new WeakReference<WaitingProvidorContract.View>(view);
    }

    @Override
    public void onDestroy() {
        if (mWaitingView.get() != null)
            mWaitingView.clear();
    }

    @Override
    public void getProvider(final String orderID) {
        if (mWaitingView.get() != null) {
            mRepository.getOrder(orderID, new DataSource.GetOrderCallBack() {
                @Override
                public void onGetOrder(Order currentOrder) {
                    if (currentOrder.getProvider() != null) {
                        mWaitingView.get().onProviderAccept(currentOrder);
                    } else {
                        mWaitingView.get().startCountDownCounter();
                    }
                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    mWaitingView.get().showWaitingError(error);
                    mWaitingView.get().startCountDownCounter();
                }
            });
        }
    }


}
