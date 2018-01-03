package com.example.majid_fit5.al_rajhitakaful.waiting;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
/**
 * Created by BASH on 12/30/2017.
 */

interface WaitingProvidorContract  {
    interface View extends BaseView{
        void onProviderAccept(Order currentOrder);
        void startCountDownCounter();
        void showWaitingError(AlRajhiTakafulError error);

    }
    interface Presenter extends BasePresenter<WaitingProvidorContract.View>{
        void getProvider(String orderID);

    }
}
