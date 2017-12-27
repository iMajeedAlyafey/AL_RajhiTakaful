package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionContract {


    interface View extends BaseView{
        void onValidPhoneNumber();
        void onInvalidPhoneNumber();
        void onSubmitError(AlRajhiTakafulError error);
        void onSubmitAndGetOTPSuccess();
    }

    interface Presenter extends BasePresenter<View>{
        void validatePhoneNumber(String phoneNumber);
        void submitAndGetOTP(String phoneNumber);
    }
}
