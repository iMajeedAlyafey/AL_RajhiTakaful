package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.support.annotation.NonNull;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;

import java.lang.ref.WeakReference;

/**
 * Created by Eng. Abdulmajid Alyafey on 1/2/2018.
 */

public class CreateOrderPresenter implements CreateOrderContract.Presenter {
    private DataRepository mDataRepository;
    private WeakReference<CreateOrderContract.View> mView;

    public CreateOrderPresenter(DataRepository mDataRepository){
        this.mDataRepository=mDataRepository;
    }

    @Override
    public void onBind(@NonNull CreateOrderContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if(mView.get()!=null)
            mView.clear();
        Injection.deleteProvidedDataRepository();
    }

    @Override
    public void createOrder(OrderRequest orderRequest) {
        if(mView.get()!=null){
            mDataRepository.createOrder(orderRequest, new DataSource.CreateOrderCallback() {
                @Override
                public void onCreateOrderResponse(Order currentOrder) {
                    if(mView.get()!=null)
                    mView.get().onCreateOrderSuccess(currentOrder);
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null)
                    mView.get().onCreateOrderFailure(error);
                }
            });
        }
    }

    @Override
    public void uploadPhoto(String orderID, String filePath) {
        if(mView.get()!=null){
            mDataRepository.uploadPhoto(orderID, filePath, new DataSource.UploadPhoto() {
                @Override
                public void onUploadPhoto(Order currentOrder) {
                    if(mView.get()!=null)
                    mView.get().onUploadPhotoSuccess(currentOrder);
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null)
                        mView.get().onUploadPhotoFailure(error);
                }
            });
        }


    }

    @Override
    public void logOut() {
        if(mView.get()!=null){
            mDataRepository.logout(new DataSource.LogoutCallback() {
                @Override
                public void onLogoutResponse(AlRajhiTakafulResponse response) {
                    if(mView.get()!=null)
                        mView.get().OnLogOutSuccess(response);

                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null)
                        mView.get().OnLogOutFailure(error);                }
            });
        }
    }


}
