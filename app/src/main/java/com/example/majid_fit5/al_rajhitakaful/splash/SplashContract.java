package com.example.majid_fit5.al_rajhitakaful.splash;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;

/**
 * Created by BASH on 12/27/2017.
 */

public interface SplashContract {
    //--------------------------------------------------------------------------------
    interface View extends BaseView{
        void startLogin();
        void startCreateOrder();
        void startShowOrder(Order currentOrder);
        void showErrorMessage(AlRajhiTakafulError alRajhiTakafulError);
    }
    //---------------------------------------------------------------------------------
    interface Presenter extends BasePresenter<SplashContract.View>{
        void checkUserLoginStatues();
        void getCurrentUser();
    }
}
