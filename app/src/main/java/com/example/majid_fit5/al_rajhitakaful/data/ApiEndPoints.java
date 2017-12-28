package com.example.majid_fit5.al_rajhitakaful.data;



import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.*;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {


    // Majeed
    @GET("api/alrajhi_takaful/auth/new?phone_number={phone_number}")
    Call<AlRajhiTakafulResponse> otp(@Path("phone_number") OTPRequest phone_number); // here might happen an error..// check the Following GET api

    @POST("api/alrajhi_takaful/auth/")
    Call<CurrentUserResponse> login(@Body LoginRequest loginRequest);

    @DELETE("api/alrajhi_takaful/auth/")
    Call<AlRajhiTakafulResponse> logout();

    @POST("api/alrajhi_takaful/saaed_orders/")
    Call<Order> createOrder(@Body OrderRequest orderRequest);



    // Get current user
   @GET ("api/alrajhi_takaful/user/")
   Call<CurrentUserResponse> getCurrentUser();

   // Cancel Order
    @DELETE("api/alrajhi_takaful/saaed_orders/{id}")
    Call<Void> CancelOrder(@Path("id") String orderID);

    // Show Order
    @GET("api/alrajhi_takaful/saaed_orders/{id}")
    Call<Order> getOrder(@Path("id") String orderID);
}
