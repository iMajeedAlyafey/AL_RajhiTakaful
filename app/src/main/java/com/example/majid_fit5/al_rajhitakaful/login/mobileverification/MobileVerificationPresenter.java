package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/30/2017.
 */

public class MobileVerificationPresenter implements MobileVerificationContract.Presenter {
    private DataRepository mDataRepository;
    private WeakReference<MobileVerificationContract.View> mView;

    public MobileVerificationPresenter(DataRepository mDataRepository){
        this.mDataRepository=mDataRepository;
    }

    @Override
    public void onBind(@NonNull MobileVerificationContract.View view) {
        mView= new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if(mView.get()!=null)
        mView.clear();
    }

    @Override
    public void sendVerificationCode(String code, String phoneNumber) {
        if(mView.get()!=null){
            LoginRequest loginRequest= new LoginRequest(phoneNumber,code);
            mDataRepository.login(loginRequest, new DataSource.LoginCallback() {
                @Override
                public void onLoginResponse(CurrentUserResponse currentUser) {
                    if(mView.get()!=null)
                    mView.get().onCodeVerificationSuccess(currentUser); // 100% this will be executed if there is 201 response.
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null)
                    mView.get().onCodeVerificationFailure(error);
                }
            });
        }
    }

    @Override
    public void resendAndGetOTP(final String phoneNumber) {
        if(mView.get()!=null){
            mDataRepository.OtpCall(phoneNumber, new DataSource.OTPCallback() {
                @Override
                public void onOTPResponse(AlRajhiTakafulResponse response) {
                   if(mView.get()!=null)
                       mView.get().onGetOTPSuccsess(phoneNumber);
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null)
                    mView.get().onGetOTPFailure(error);
                }
            });
        }
    }

    @Override
    public void saveUserInPreference(CurrentUserResponse userResponse) {
        PrefUtility.saveUserAuthToken(AlRajhiTakafulApplication.getInstance(),userResponse.getUser().getAuthToken());
    }



    public AlRajhiTakafulError getError(String msg,String errorCode){
        AlRajhiTakafulError error = new AlRajhiTakafulError();
        error.setMessage(msg);
        return error;
    }
}
