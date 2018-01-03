package com.example.majid_fit5.al_rajhitakaful.data;

import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.*;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {


    // Majeed
    @GET("auth/new")
    Call<AlRajhiTakafulResponse> otp(@Query("phone_number") String phone_number); // here might happen an error..// check the Following GET api

    @POST("auth/")
    Call<CurrentUserResponse> login(@Body LoginRequest loginRequest);

    @DELETE("auth/")
    Call<AlRajhiTakafulResponse> logout();

    @POST("saaed_orders/")
    Call<Order> createOrder(@Body OrderRequest orderRequest);

    // Get current user
   @GET ("user/")
   Call<CurrentUserResponse> getCurrentUser();

   // Cancel Order
    @DELETE("saaed_orders/{id}")
    Call<Void> CancelOrder(@Path("id") String orderID);

    // Show Order
    @GET("saaed_orders/{id}")
    Call<Order> getOrder(@Path("id") String orderID);

    @Multipart
    @PUT("saaed_orders/{id}")
    Call<Order> uploadPhoto(@Path("id") String orderID,  @Part MultipartBody.Part image);

}
