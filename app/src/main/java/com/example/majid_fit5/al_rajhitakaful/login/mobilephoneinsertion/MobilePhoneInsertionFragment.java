package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionFragment extends BaseFragment implements MobilePhoneInsertionContract.View, View.OnClickListener {
    private View rootView;
    MobilePhoneInsertionContract.Presenter mPresenter;
    private EditText mEdtPhoneNumber;
    private Button mBtnLogin;


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
        init();

        return rootView;
    }

    private void init() {
        // checkPermissions() again
        mEdtPhoneNumber= rootView.findViewById(R.id.edt_mobile_input);
        mBtnLogin= rootView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onValidPhoneNumber(String phoneNumber) {
        Toast.makeText(rootView.getContext(),"Phone number is OK and ready to send otp",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidPhoneNumber(String errorMessage) {
        Toast.makeText(rootView.getContext(),errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitError(AlRajhiTakafulError error) {

    }

    @Override
    public void onSubmitAndGetOTPSuccess() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
               // Snackbar.make(rootView, "BIG ERROR", Snackbar.LENGTH_INDEFINITE).show();
               // Toast.makeText(rootView.getContext(),"Evaluate Phone Number",Toast.LENGTH_SHORT).show();
                mPresenter.validatePhoneNumber(mEdtPhoneNumber.getText().toString());
                break;
        }
    }
}
