package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.login.mobileverification.MobileVerificationFragment;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;
import com.hbb20.CountryCodePicker;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionFragment extends BaseFragment implements MobilePhoneInsertionContract.View, View.OnClickListener,CountryCodePicker.OnCountryChangeListener {
    private View mRootView;
    MobilePhoneInsertionContract.Presenter mPresenter;
    private EditText mEdtPhoneNumber;
    private Button mBtnLogin;
    private ProgressDialog mProgressDialog;
    private MobileVerificationFragment mMobileVerificationFragment;
    private CountryCodePicker mCCP;
    private PhoneNumberUtil mPhoneUtil;
    private String hintPhoneExample;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MobilePhoneInsertionPresenter(Injection.provideDataRepository());
        mPresenter.onBind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login_insertion,container,false);
        init();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return mRootView;
    }
    private void init() {
        mPhoneUtil = PhoneNumberUtil.createInstance(AlRajhiTakafulApplication.getInstance().getApplicationContext());
        mCCP = mRootView.findViewById(R.id.ccp);
        mEdtPhoneNumber= mRootView.findViewById(R.id.edt_mobile_input);
        mCCP.setOnCountryChangeListener(this); // for handling the examples of the countries.
        mBtnLogin= mRootView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        onCountrySelected();
    }

    @Override
    public void showLoading() {
        if(mProgressDialog==null){
            mProgressDialog=ProgressDialog.show(getActivity(),"","",false,false);
            mProgressDialog.setProgressDrawable(AlRajhiTakafulApplication.getInstance().getDrawable(R.drawable.custom_progressbar));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
        }else{
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public void onValidPhoneNumber(String phoneNumber) {
        showLoading();
        mPresenter.submitAndGetOTP(phoneNumber);
    }

    @Override
    public void onInvalidPhoneNumber(String errorMessage) {
        Toast.makeText(mRootView.getContext(),errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubmitAndGetOTPError(AlRajhiTakafulError error) {
        hideLoading();
        Toast.makeText(mRootView.getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubmitAndGetOTPSuccess(String phoneNumber) {
        hideLoading();
        Bundle bundle =new Bundle();
        bundle.putString("phoneNumber",phoneNumber);
        mMobileVerificationFragment= new MobileVerificationFragment();
        mMobileVerificationFragment.setArguments(bundle);
        ActivityUtility.addFragmentToActivity(getFragmentManager(),mMobileVerificationFragment,R.id.content_frame,"MobileVerificationFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                String phoneNumber = mEdtPhoneNumber.getText().toString();
                if (!phoneNumber.equals("") && phoneNumber!=null && phoneNumber.length()>2){
                    validatePhoneNumber(mEdtPhoneNumber.getText().toString(),mCCP.getSelectedCountryNameCode());
                }else {
                    onInvalidPhoneNumber(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_invalid));
                }
                break;
        }
    }

    private void validatePhoneNumber(String number, String countryNameCode) {
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = mPhoneUtil.parse(number, countryNameCode);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        PhoneNumberUtil.PhoneNumberType phoneNumberType = mPhoneUtil.getNumberType(phoneNumber);
        if (mPhoneUtil.isValidNumber(phoneNumber) && phoneNumberType == PhoneNumberUtil.PhoneNumberType.MOBILE) {
            onValidPhoneNumber(mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164).substring(1));
        }else {
            onInvalidPhoneNumber(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_invalid));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onCountrySelected() {
        mEdtPhoneNumber.setText("");
        hintPhoneExample=""+mPhoneUtil.getExampleNumberForType(mCCP.getSelectedCountryNameCode(), PhoneNumberUtil.PhoneNumberType.MOBILE).getNationalNumber();
        Phonenumber.PhoneNumber mPhoneNumber=null ;
        try {
            mPhoneNumber = mPhoneUtil.parse(hintPhoneExample,mCCP.getSelectedCountryNameCode());
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        mEdtPhoneNumber.setHint(mPhoneUtil.format(mPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        mEdtPhoneNumber.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(hintPhoneExample.length()+1)
        });
    }
}
