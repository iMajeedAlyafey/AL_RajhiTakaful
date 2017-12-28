package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;
import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.ValidationsUtility;

import java.lang.ref.WeakReference;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionPresenter implements MobilePhoneInsertionContract.Presenter {
   private DataRepository mDataRepository;
   private WeakReference<MobilePhoneInsertionContract.View> mView;
   private MobilePhoneInsertionContract.View mViewObject;

   public MobilePhoneInsertionPresenter(DataRepository mDataRepository){
       this.mDataRepository=mDataRepository;
   }

    @Override
    public void onBind(@NonNull MobilePhoneInsertionContract.View view) {
        mView = new WeakReference<>(view);
        mViewObject=mView.get();
    }

    @Override
    public void onDestroy() {
        if(mViewObject!=null)
        mView.clear();

    }

    @Override
    public void validatePhoneNumber(String phoneNumber) {
        if(mViewObject!=null){
            if(ValidationsUtility.isEmpty(phoneNumber)){mViewObject.onInvalidPhoneNumber(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_required));
            } else if (!ValidationsUtility.isValidPhoneNumberLength(phoneNumber)){
                mViewObject.onInvalidPhoneNumber(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_invalid));
            }
            else{ // it is OK
                if(phoneNumber.startsWith("0"))
                    phoneNumber= phoneNumber.substring(1,phoneNumber.length());  // the beginning index, inclusive. ||  the ending index, exclusive.

                mViewObject.onValidPhoneNumber("966"+phoneNumber);
            }

        }
    }

    @Override
    public void submitAndGetOTP(final String phoneNumber) {
        if(mViewObject!=null){
            mViewObject.showLoading();


            mViewObject.onSubmitAndGetOTPSuccess(phoneNumber);


            // Bellow is OK
          /*  // calling the repository
            mDataRepository.OtpCall(phoneNumber, new DataSource.OTPCallback() {
                @Override
                public void onOTPResponse(AlRajhiTakafulResponse response) {
                    if(mViewObject!=null){ // there is a response;
                        mViewObject.hideLoading();
                        mViewObject.onSubmitAndGetOTPSuccess(phoneNumber);
                    }
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mViewObject!=null){
                        mViewObject.onSubmitAndGetOTPError(error);
                    }

                }
            });*/
        }
    }

    public AlRajhiTakafulError getError(String msg){
        AlRajhiTakafulError error = new AlRajhiTakafulError();
        error.setMessage(msg);
        return error;
    }
}
