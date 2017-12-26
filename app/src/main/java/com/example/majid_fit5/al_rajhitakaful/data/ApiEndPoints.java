package com.example.majid_fit5.al_rajhitakaful.data;



import com.example.majid_fit5.al_rajhitakaful.data.models.order.CurrentOrder;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.*;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulResponse.AlRajhiTakafulResponse;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {


    // Majeed
    @GET("api/alrajhi_takaful/auth/new?phone_number={phone_number}")
    Call<AlRajhiTakafulResponse> otp(@Path("phone_number") OTPRequest phone_number); // here might happen an error..// check the Following GET api

    @POST("api/alrajhi_takaful/auth/")
    Call<CurrentUser> login(@Body LoginRequest loginRequest);

    @DELETE("api/alrajhi_takaful/auth/")
    Call<AlRajhiTakafulResponse> logout();

    @POST("api/alrajhi_takaful/saaed_orders/")
    Call<CurrentOrder> createOrder(@Body OrderRequest orderRequest);



   @GET ("api/alrajhi_takaful/user/")
   Call<CurrentUser> getCurrentUser();
// delete Gist
    @DELETE("api/alrajhi_takaful/saaed_orders/{id}")
    Call<ResponseBody> CancelOrder(@Path("id") String id);
}
