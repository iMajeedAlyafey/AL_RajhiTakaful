package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;

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

    }

    @Override
    public void resendAndGetOTP(final String phoneNumber) {
        if(mView.get()!=null){
            mDataRepository.OtpCall(phoneNumber, new DataSource.OTPCallback() {
                @Override
                public void onOTPResponse(AlRajhiTakafulResponse response) {
                   // if(response.getCode()==201)  // OK
                       mView.get().onGetOTPSuccsess(phoneNumber);
                    /*else
                        mView.get().onGetOTPFailure(getError());*/
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    mView.get().onGetOTPFailure(error);
                }
            });
        }
    }

    @Override
    public void saveUserInPreference(CurrentUserResponse userResponse) {
        // save to pref
    }

    public AlRajhiTakafulError getError(String msg,String errorCode){
        AlRajhiTakafulError error = new AlRajhiTakafulError();
        error.setMessage(msg);
        return error;
    }
}
