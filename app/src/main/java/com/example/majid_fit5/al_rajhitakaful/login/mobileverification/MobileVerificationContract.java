package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import com.example.majid_fit5.al_rajhitakaful.base.BasePresenter;
import com.example.majid_fit5.al_rajhitakaful.base.BaseView;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/30/2017.
 */

public interface MobileVerificationContract {

    interface View extends BaseView { // no need for extending BaseView since i don not need its functions ( show and hide loading).
        void onCodeVerificationSuccess(CurrentUserResponse userResponse);
        void onCodeVerificationFailure(AlRajhiTakafulError error);
        void onGetOTPSuccess(String phoneNumber); // if response.code == 201 -- > OK else 404
        void onGetOTPFailure(AlRajhiTakafulError errorCode);

    }

    interface Presenter extends BasePresenter<MobileVerificationContract.View>{
        void sendVerificationCode(String code, String phoneNumber);
        void resendAndGetOTP(String phoneNumber);
        void saveUserInPreference(CurrentUserResponse userResponse);
    }
}
