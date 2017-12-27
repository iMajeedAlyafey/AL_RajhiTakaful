package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionFragment extends BaseFragment implements MobilePhoneInsertionContract.View {
    private View rootView;
    MobilePhoneInsertionContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MobilePhoneInsertionPresenter(Injection.provideDataRepository());
        mPresenter.onBind(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_login_insertion,container,false);
        // init

        return rootView;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onValidPhoneNumber() {

    }

    @Override
    public void onInvalidPhoneNumber() {

    }

    @Override
    public void onSubmitError(AlRajhiTakafulError error) {

    }

    @Override
    public void onSubmitAndGetOTPSuccess() {

    }
}
