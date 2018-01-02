package com.example.majid_fit5.al_rajhitakaful.data;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */


import android.net.Uri;

import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;

public interface DataSource {
    interface BaseCallBack {
                void onFailure(AlRajhiTakafulError error);
            }
    //------------------------------------------------------
    interface OTPCallback extends BaseCallBack{
        void onOTPResponse(AlRajhiTakafulResponse response);
    }
    void OtpCall(String phoneNumber,OTPCallback callback);
    //------------------------------------------------------

    interface LoginCallback extends BaseCallBack{
        void onLoginResponse(CurrentUserResponse currentUser);
    }
    void login(LoginRequest loginRequest, LoginCallback callback);
    //--------------------------------------------------------------

    interface LogoutCallback extends BaseCallBack{
        void onLogoutResponse(AlRajhiTakafulResponse response);
    }
    void logout(LogoutCallback logoutCallback);
    //-------------------------------------------------------------

    interface CreateOrderCallback extends BaseCallBack{
        void onCreateOrderResponse(Order currentOrder);
    }
    void createOrder(OrderRequest request,CreateOrderCallback callback);
    //----------------------------------------------------------------


    //------------------------------------------------------
    interface GetCurrentUserCallBack extends BaseCallBack  {
        void onGetCurrentUser(CurrentUserResponse currentUser);
    }
    //Real implement for this method will found in RemoteDataSource Class, because all blog data located in the server
    void getCurrentUser(GetCurrentUserCallBack callCack);
    //-------------------------------------------------------------------------------------
    interface CancelOrderCallBack extends BaseCallBack{
        void onOrderCanceled();
    }
    void cancelOrderC(String orderID, CancelOrderCallBack callBack);
    //--------------------------------------------------------------------------------------
    interface GetOrderCallBack extends BaseCallBack{
        void onGetOrder(Order currentOrder);
    }
    void getOrder(String orderID, GetOrderCallBack callBack);
    //------------------------------------------------------------------------------------------
    interface UploadPhoto extends BaseCallBack{
        void onUploadPhoto(Order currentOrder);
    }
    void uploadPhoto(String orderID, Uri filePath, UploadPhoto callback);
    //-------------------------------------------------------------------------------------------

}