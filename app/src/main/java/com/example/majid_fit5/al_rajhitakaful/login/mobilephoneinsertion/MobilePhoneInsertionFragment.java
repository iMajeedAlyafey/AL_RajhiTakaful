package com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.majid_fit5.al_rajhitakaful.login.mobileverification.MobileVerificationActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.ActivityUtility;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class MobilePhoneInsertionFragment extends BaseFragment implements MobilePhoneInsertionContract.View, View.OnClickListener {
    private View rootView;
    MobilePhoneInsertionContract.Presenter mPresenter;
    private EditText mEdtPhoneNumber;
    private Button mBtnLogin;
    private ProgressDialog mProgressDialog;

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
        if(mProgressDialog==null){
            mProgressDialog=ProgressDialog.show(getActivity(),"","",false,false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
        }else{
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onValidPhoneNumber(String phoneNumber) {
        //Toast.makeText(rootView.getContext(),"Phone number "+phoneNumber+" is OK and ready to send otp",Toast.LENGTH_LONG).show();
        mPresenter.submitAndGetOTP(phoneNumber);
    }

    @Override
    public void onInvalidPhoneNumber(String errorMessage) {
        Toast.makeText(rootView.getContext(),errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubmitAndGetOTPError(AlRajhiTakafulError error) {
        //Toast.makeText(rootView.getContext(),"Error msg: "+error.getMessage()+"||| Error code: "+error.getCode() ,Toast.LENGTH_LONG).show();
        Toast.makeText(rootView.getContext(), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_phone_number_invalid),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSubmitAndGetOTPSuccess(String phoneNumber) {
        Bundle bundle =new Bundle();
        bundle.putString("phoneNumber",phoneNumber);

       // ActivityUtility.addFragmentToActivity( getFragmentManager(),new Mobile,R.id.content_frame);

        ActivityUtility.goToActivity(this.getActivity(),MobileVerificationActivity.class,bundle);
        getActivity().finish();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy(); // to destroy this view.
    }
}
