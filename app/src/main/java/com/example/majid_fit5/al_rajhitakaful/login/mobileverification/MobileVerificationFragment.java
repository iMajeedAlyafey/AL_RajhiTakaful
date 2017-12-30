package com.example.majid_fit5.al_rajhitakaful.login.mobileverification;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import java.text.SimpleDateFormat;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/28/2017.
 */

public class MobileVerificationFragment extends BaseFragment implements MobileVerificationContract.View,View.OnClickListener{
    private View mRootView;
    private TextView mTxtCountDownTimer;
    private TextView mTxtPhoneNumber;
    private TextView mTxtResned;
    private EditText mEdtVerificationCode;
    private LinearLayout mLayoutResendMessage;
    private Button mBtnSendCode;
    private String mPhoneNumber;
    private CountDownTimer mTimer;
    private MobileVerificationContract.Presenter mPresenter;

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
        return mRootView;
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
        createStartCountDownTimer();
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
               mPresenter.resendAndGetOTP(mPhoneNumber);
               break;

            case R.id.btn_send_code:
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
    @Override
    public void onVerificationCodeSuccess(CurrentUserResponse userResponse) {

    }

    @Override
    public void onVerificationCodeFailure(AlRajhiTakafulError error) {

    }

    @Override
    public void onGetOTPSuccsess(String phoneNumber) {
        mTimer.start();
        Toast.makeText(mRootView.getContext(),"We have sent again to " + phoneNumber,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetOTPFailure(AlRajhiTakafulError error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
