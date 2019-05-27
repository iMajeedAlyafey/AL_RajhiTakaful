package com.example.majid_fit5.al_rajhitakaful.requestdetails;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

import java.lang.ref.WeakReference;

/**
 * Created by BASH on 12/31/2017.
 */

public class RequestDetailsPresenter implements RequestDetailsContract.Presenter {
    private DataRepository mRepository;
    private WeakReference<RequestDetailsContract.View> mRequestView;

    public RequestDetailsPresenter(DataRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void onBind(@NonNull RequestDetailsContract.View view) {
        mRequestView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if (mRequestView.get() != null) {
            mRequestView.clear();
            Injection.deleteProvidedDataRepository();
        }
    }

    /**
     * cancel the current order
     *
     * @param orderID the oder id that should be canceled
     */
    @Override
    public void cancelOrder(String orderID) {
        if (mRequestView.get() != null) {
            mRepository.cancelOrderC(orderID, new DataSource.CancelOrderCallBack() {
                @Override
                public void onOrderCanceled() {
                    if (mRequestView.get() != null) {
                        mRequestView.get().onCancelOrderSuccess();
                    }
                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if (mRequestView.get() != null) {
                        mRequestView.get().onCancelOrderFailure("order Can not be canceled");
                    }
                }
            });
        }
    }
}
