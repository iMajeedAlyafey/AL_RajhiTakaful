package com.example.majid_fit5.al_rajhitakaful.splash;

import android.support.annotation.NonNull;

import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;

import java.lang.ref.WeakReference;

/**
 * Created by BASH on 12/27/2017.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private WeakReference<SplashContract.View> mSplashView;
    private DataRepository mRepository;
    private String orderID;

    public SplashPresenter(@NonNull DataRepository mRepository) {
        this.mRepository = mRepository;
    }

    //------------------------------------------------------------------
    @Override
    public void onBind(@NonNull SplashContract.View view) {
        if (view != null)// check if i write it in right way
            mSplashView = new WeakReference<SplashContract.View>(view);

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
        if (mSplashView.get() != null) {
            if (PrefUtility.isLogedIn()) {
                mSplashView.get().startLogin();
            }
            else {
                getCurrentUser();
            }
        }
    }
    //------------------------------------------------------------------
    @Override
    public void getCurrentUser() {
        if (mSplashView.get() != null) {
            mRepository.getCurrentUser(new DataSource.GetCurrentUserCallBack() {
                @Override
                public void onGetCurrentUser(CurrentUserResponse currentUser) {
                    if (mSplashView.get() != null) {
                        if (currentUser.getOrder() == null) {
                            mSplashView.get().startCreateOrder();
                        } else {
                            mSplashView.get().startShowOrder(currentUser.getOrder());
                        }
                    }
                }

                @Override
                public void onFailure(AlRajhiTakafulError error) {
                    if (mSplashView.get() != null) {
                        mSplashView.get().showErrorMessage(error);
                    }

                }
            });
        }
    }
}
