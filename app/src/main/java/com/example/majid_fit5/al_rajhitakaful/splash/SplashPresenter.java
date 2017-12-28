package com.example.majid_fit5.al_rajhitakaful.splash;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;

import java.lang.ref.WeakReference;

/**
 * Created by BASH on 12/27/2017.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private WeakReference<SplashContract.View> mSplashView;
    private DataRepository mRepository;
    private SplashContract.View mSplashViewObj;
    private int orderID;

    public SplashPresenter(@NonNull DataRepository mRepository) {
        this.mRepository = mRepository;
    }

    //------------------------------------------------------------------
    @Override
    public void onBind(@NonNull SplashContract.View view) {
        if (view != null)// check if i write it in right way
            mSplashView = new WeakReference<SplashContract.View>(view);
        mSplashViewObj = mSplashView.get();
    }

    //------------------------------------------------------------------
    @Override
    public void onDestroy() {
        if (mSplashView.get() != null)
            mSplashView.clear();
    }

    //------------------------------------------------------------------
    @Override
    public void checkUserLoginStatues() {
        if (mSplashViewObj != null) {
            if (!PrefUtility.isLogedIn()) {
                mSplashViewObj.strartLogin();
            } else if (checkOrderStatues() != 0) {
                mSplashViewObj.startShowOrder(orderID);
            } else mSplashViewObj.strartCreateOrder();
        }
    }

    @Override
    public int checkOrderStatues() {
        if (mSplashViewObj != null) {
            mRepository.getCurrentUser(new DataSource.GetCurrentUserCallBack() {
                @Override
                public void onGetCurrentUser(CurrentUserResponse currentUser) {
                    orderID = Integer.parseInt(currentUser.getOrder().getId());
                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    mSplashViewObj.showErrorMessage(error);
                }
            });

        }
        return ++orderID;
    }


}
