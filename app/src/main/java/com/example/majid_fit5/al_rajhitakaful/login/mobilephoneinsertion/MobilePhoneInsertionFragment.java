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
//        mCCP.registerCarrierNumberEditText(mEdtPhoneNumber); // Attach edit text to CCP.
        mCCP.setOnCountryChangeListener(this); // for handling the examples of the countries.
        mBtnLogin= mRootView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        //--set the hint of the text box and the length
        onCountrySelected();

//        phoneUtil= PhoneNumberUtil.createInstance(AlRajhiTakafulApplication.getInstance());
//        ccp = mRootView.findViewById(R.id.ccp);
//        //set The hint for the first time
//        mEdtPhoneNumber.setHint(""+phoneUtil.getExampleNumberForType(ccp.getSelectedCountryNameCode(), PhoneNumberUtil.PhoneNumberType.MOBILE).getNationalNumber());
//        //Listener to the country picker when it is changed
//        ccp.setOnCountryChangeListener(this);
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
//        Snackbar.make(mRootView,"Please Enter valid Phone Number",Snackbar.LENGTH_LONG).show();
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
                    onInvalidPhoneNumber("Please Enter valid Phone Number");
                }
//                if(mCCP.isValidFullNumber()){ // is 100% not empty and valid.
//                    try { // try and catch is must.
//                        mPhoneNumber = mPhoneUtil.parse(mEdtPhoneNumber.getText().toString(),mCCP.getSelectedCountryNameCode());
//                    }catch (NumberParseException e) {
//                        Toast.makeText(mRootView.getContext(),AlRajhiTakafulApplication.getInstance().getString(R.string.msg_something_went_wrong),Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//                    onValidPhoneNumber(mPhoneUtil.format(mPhoneNumber,PhoneNumberUtil.PhoneNumberFormat.E164).substring(1)); //substring(1) delete +
//                }else{
//                    Toast.makeText(mRootView.getContext(),AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_invalid),Toast.LENGTH_LONG).show();
//                }


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
        boolean isValid = mPhoneUtil.isValidNumber(phoneNumber);
        if (isValid && phoneNumberType == PhoneNumberUtil.PhoneNumberType.MOBILE) {
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
