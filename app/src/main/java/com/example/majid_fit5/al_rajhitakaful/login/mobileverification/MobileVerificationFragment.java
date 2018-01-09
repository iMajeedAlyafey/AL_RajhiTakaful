package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.createorder.HomeActivity;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.requestdetails.RequestDetailsActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;
import com.example.majid_fit5.al_rajhitakaful.utility.ValidationsUtility;
import com.example.majid_fit5.al_rajhitakaful.waiting.WaitingProviderActivity;

import java.text.SimpleDateFormat;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/28/2017.
 */

public class MobileVerificationFragment extends BaseFragment implements MobileVerificationContract.View,View.OnClickListener{
    private View mRootView;
    private TextView mTxtCountDownTimer,mTxtPhoneNumber,mTxtResned;
    private EditText mEdtVerificationCode;
    private LinearLayout mLayoutResendMessage;
    private Button mBtnSendCode;
    private String mPhoneNumber;
    private CountDownTimer mTimer;
    private MobileVerificationContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private String mVerificationCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= new MobileVerificationPresenter(Injection.provideDataRepository());
        mPresenter.onBind(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_login_verification,container,false);
        init();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return mRootView;
    }

    // to access action bar of the activity.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init() {
        mTxtPhoneNumber= mRootView.findViewById(R.id.txt_phone_number);
        mEdtVerificationCode= mRootView.findViewById(R.id.edt_verification_code);
        mBtnSendCode= mRootView.findViewById(R.id.btn_send_code);
        mBtnSendCode.setOnClickListener(this); // setting listener
        mTxtCountDownTimer= mRootView.findViewById(R.id.txt_count_down_timer);
        mLayoutResendMessage=mRootView.findViewById(R.id.layout_resend_msg);
        mTxtResned=mRootView.findViewById(R.id.txt_resend);
        mTxtResned.setOnClickListener(this); // setting listener
        createStartCountDownTimer(); // counter for showing period of waiting.
    }
    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null) // after initialization.
            mPhoneNumber=getArguments().getString("phoneNumber");
            mTxtPhoneNumber.setText(mPhoneNumber);
        mTimer.start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.txt_resend:
               mLayoutResendMessage.setVisibility(View.INVISIBLE);
               mTxtCountDownTimer.setVisibility(View.VISIBLE);
               mTimer.start();
               mPresenter.resendAndGetOTP(mPhoneNumber); // no need for dialog bo since there is timer.
               break;
            case R.id.btn_send_code:
               mVerificationCode=mEdtVerificationCode.getText().toString();
                if(!ValidationsUtility.isEmpty(mVerificationCode) && mVerificationCode.length() == 4) {
                    showLoading();
                    mPresenter.sendVerificationCode(mVerificationCode, mPhoneNumber);
                }else
                Toast.makeText(mRootView.getContext(),AlRajhiTakafulApplication.getInstance().getString(R.string.msg_code_invalid),Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void createStartCountDownTimer() {
        mTimer=new CountDownTimer(45000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                mTxtCountDownTimer.setText(new SimpleDateFormat("mm:ss").format(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                mTxtCountDownTimer.setVisibility(View.INVISIBLE);
                mLayoutResendMessage.setVisibility(View.VISIBLE);
            }
        };
    }
    /**
     * This function redirect the user to home screen. However, it checks for situation of "Unauthorized Access" to the app.
     * This code is already coded in splash, but we have to do it again here to cover this case, the "Unauthorized Access" case.
     * @param userResponse
     */
    @Override
    public void onCodeVerificationSuccess(CurrentUserResponse userResponse) {
        hideLoading();
        mPresenter.saveUserInPreference(userResponse);
        Intent intent;
        if (userResponse.getCurrentOrder() != null ) {// the user has order.
            if (userResponse.getCurrentOrder().getProvider() !=null) // if there is a provider assigned to user's order, redirect the user to the Request activity.
                intent = new Intent(getActivity(), RequestDetailsActivity.class);
            else // here the user made a request but sill NOT assigned to any provider.
                intent = new Intent(getActivity(), WaitingProviderActivity.class);

        } else  // the user has no orders, redirect him to home activity.
            intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(Constants.CURRENT_ORDER, userResponse.getCurrentOrder());
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCodeVerificationFailure(AlRajhiTakafulError error) {
        hideLoading();
        switch (error.getCode()){
            case 400:
                Toast.makeText(mRootView.getContext(),AlRajhiTakafulApplication.getInstance().getString(R.string.msg_code_invalid),Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(mRootView.getContext(),AlRajhiTakafulApplication.getInstance().getString(R.string.error_503),Toast.LENGTH_LONG).show(); // here to show "Something Wrong Happens" ..
                break;
        }
        Log.e("VerificationFailure", error.getMessage()+"::Code::"+error.getCode());
    }

    @Override
    public void onGetOTPSuccess(String phoneNumber) {
       // Toast.makeText(mRootView.getContext(),"We have sent again to " + phoneNumber,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetOTPFailure(AlRajhiTakafulError error) {
      //  Toast.makeText(mRootView.getContext(),"Erro " + this.mPhoneNumber,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if(mProgressDialog==null){
            mProgressDialog=ProgressDialog.show(getActivity(),"","",false,false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setProgressDrawable(AlRajhiTakafulApplication.getInstance().getDrawable(R.drawable.custom_progressbar));

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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
