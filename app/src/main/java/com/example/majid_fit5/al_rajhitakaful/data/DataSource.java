package com.example.majid_fit5.al_rajhitakaful.data;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulError.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulResponse.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.CurrentOrder;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

public interface DataSource {

    interface BaseCallBack { // this will be extendable by other interfaces.
        void onFailure(AlRajhiTakafulError error);
    }

    //------------------------------------------------------
    interface OTPCallback extends BaseCallBack{
        void onOTPResponse(AlRajhiTakafulResponse response);
    }
    void OtpCall(OTPRequest request,OTPCallback callback);
    //------------------------------------------------------

    interface LoginCallback extends BaseCallBack{
        void onLoginResponse(CurrentUser currentUser);
    }
    void login(LoginRequest loginRequest, LoginCallback callback);
    //--------------------------------------------------------------

    interface LogoutCallback extends BaseCallBack{
        void onLogoutResponse(AlRajhiTakafulResponse response);
    }
    void logout(LogoutCallback logoutCallback);
    //-------------------------------------------------------------

    interface CreateOrderCallback extends BaseCallBack{
        void onCreateOrderResponse(CurrentOrder currentOrder);
    }
    void createOrder(OrderRequest request,CreateOrderCallback callback);
    //----------------------------------------------------------------


    //------------------------------------------------------
    interface GetCurrentUserCallback extends BaseCallBack  {
        void onGetCurrentUser(CurrentUser currentUser);
    }
    void getCurrentUser(GetCurrentUserCallback callCack);
}