package com.example.majid_fit5.al_rajhitakaful.requestdetails;

import android.content.Intent;
import android.support.annotation.NonNull;

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
        mRequestView = new WeakReference<RequestDetailsContract.View>(view);
    }

    @Override
    public void onDestroy() {
        if (mRequestView.get() != null){
            mRequestView.clear();
        }
    }
    @Override
    public void canelOrder(String orderID) {
        if (mRequestView.get() != null){
            mRepository.cancelOrderC(orderID, new DataSource.CancelOrderCallBack() {
                @Override
                public void onOrderCanceled() {
                    if (mRequestView.get() != null){
                        mRequestView.get().onCancelOrder();
                    }
                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                        if (mRequestView.get() != null){
                            mRequestView.get().displayErrorMeassage("order Can not be canceled");
                        }
                }
            });
        }
    }
}
