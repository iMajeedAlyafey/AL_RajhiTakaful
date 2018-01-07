package com.example.majid_fit5.al_rajhitakaful.waiting;

import android.support.annotation.NonNull;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import java.lang.ref.WeakReference;

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
            mWaitingView = new WeakReference<WaitingProvidorContract.View>(view);
    }

    @Override
    public void onDestroy() {
        if (mWaitingView.get() != null){
            mWaitingView.clear();
            Injection.deleteProvidedDataRepository();
        }
    }

    /**
     * get the current order and check if a Provider accept
     * @param orderID the order id that we want to check provider acceptance
     */
    @Override
    public void getProvider(String orderID) {
        if (mWaitingView.get() != null) {
            mRepository.getOrder(orderID, new DataSource.GetOrderCallBack() {
                @Override
                public void onGetOrder(Order currentOrder) {

                        if ( currentOrder.getProvider() != null) {
                            mWaitingView.get().onProviderAccept(currentOrder);
                        } else {
                            mWaitingView.get().startCountDownCounter();
                        }


                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mWaitingView.get()!=null){
                        mWaitingView.get().showWaitingError(new AlRajhiTakafulError(999,"Provider Error, waiting provider"));
                        mWaitingView.get().startCountDownCounter();
                    }

                }
            });
        }
    }


}
