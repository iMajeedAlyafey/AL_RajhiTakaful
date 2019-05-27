package com.example.majid_fit5.al_rajhitakaful.createorder;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;

/**
 * Created by Eng. Abdulmajid Alyafey on 1/2/2018.
 */

public interface CreateOrderContract {

    interface View extends BaseView{
        void onCreateOrderSuccess(Order order);
        void onCreateOrderFailure(AlRajhiTakafulError error);
        void onUploadPhotoSuccess(Order order);
        void onUploadPhotoFailure(AlRajhiTakafulError error);
        void OnLogOutSuccess(AlRajhiTakafulResponse response);
        void OnLogOutFailure(AlRajhiTakafulError error);
    }

    interface Presenter extends BasePresenter<CreateOrderContract.View>{
        void createOrder(OrderRequest orderRequest);
        void uploadPhoto(String orderID, String filePath);
        void logOut();
    }
}
