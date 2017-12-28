package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;
import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.utility.ValidationsUtility;

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
            if(ValidationsUtility.isEmpty(phoneNumber)){
                mView.get().onInvalidPhoneNumber(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_required)); // Resources.getSystem().getString(R.string.msg_phone_required
            } //else if (ValidationsUtility.isValidPhoneNumberLength(phoneNumber));
            else{
                //if + checking phone start with 0

                // now it is valid
                mView.get().onValidPhoneNumber(phoneNumber);
            }

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
