package com.example.majid_fit5.al_rajhitakaful.requestdetails;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

/**
 * Created by BASH on 12/31/2017.
 */

public interface RequestDetailsContract {
    interface View extends BaseView{
        void onCancelOrder();
        void displayErrorMeassage(AlRajhiTakafulError error);//should be deleted
    }
    interface Presenter extends BasePresenter<RequestDetailsContract.View>{
        void canelOrder(String orderID);
    }
}
