package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;

import java.lang.ref.WeakReference;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionPresenter implements MobilePhoneInsertionContract.Presenter {
   private DataRepository mDataRepository;
   private WeakReference<MobilePhoneInsertionContract.View> mView;


   public MobilePhoneInsertionPresenter(DataRepository mDataRepository){
       this.mDataRepository=mDataRepository;
   }

    @Override
    public void onBind(@NonNull MobilePhoneInsertionContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if(mView.get()!=null)
        mView.clear();

    }

    @Override
    public void validatePhoneNumber(String phoneNumber) {
        if(mView.get()!=null){

        }
    }

    @Override
    public void submitAndGetOTP(String phoneNumber) {
        if(mView.get()!=null){
            mView.get().showLoading();
        }
    }

    public AlRajhiTakafulError getError(String msg){
        AlRajhiTakafulError error = new AlRajhiTakafulError();
        error.setMessage(msg);
        return error;
    }
}
