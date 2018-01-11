package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.support.annotation.NonNull;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
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

   public MobilePhoneInsertionPresenter(DataRepository mDataRepository){
       this.mDataRepository=mDataRepository;
   }

    @Override
    public void onBind(@NonNull MobilePhoneInsertionContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy() {
        if(mView.get()!=null){
            mView.clear();
            Injection.deleteProvidedDataRepository();
        }
    }

    @Override
    public void submitAndGetOTP(final String phoneNumber) {
        if(mView.get()!=null){
          mDataRepository.OtpCall(phoneNumber, new DataSource.OTPCallback() {
                @Override
                public void onOTPResponse(AlRajhiTakafulResponse response) {
                    if(mView.get()!=null){ // there is a response;
                        mView.get().onSubmitAndGetOTPSuccess(phoneNumber);
                    }
                }
                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if(mView.get()!=null){
                        mView.get().onSubmitAndGetOTPError(error);
                    }

                }
            });
        }
    }

}
