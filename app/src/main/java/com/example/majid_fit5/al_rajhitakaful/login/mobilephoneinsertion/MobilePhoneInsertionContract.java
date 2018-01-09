package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public interface MobilePhoneInsertionContract {


    // every action in presenter have 2 corresponding action in view.. OnSuccess + onFailure
    interface View extends BaseView{
        void onValidPhoneNumber(String phoneNumber);
        void onInvalidPhoneNumber(String errorMessage);
        void onSubmitAndGetOTPError(AlRajhiTakafulError error);
        void onSubmitAndGetOTPSuccess(String phoneNumber); // the phoneNumber is used in other fragment.
    }

    interface Presenter extends BasePresenter<View>{
        //no need, remooove it
        void validatePhoneNumber(String phoneNumber);
        void submitAndGetOTP(String phoneNumber);
    }
}
