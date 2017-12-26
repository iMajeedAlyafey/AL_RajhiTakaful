package com.example.majid_fit5.al_rajhitakaful.data;



import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulResponse.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {

    @GET("api/alrajhi_takaful/auth/new?phone_number={phone_number}")
    Call<AlRajhiTakafulResponse> otp(@Path("phone_number") String phone_number);


    /*Using Retrofit to get Blog Details
    @GET
    Call<Blog> getBlogDetails(@Url String url);*/
   @GET ("api/alrajhi_takaful/user/")
   Call<CurrentUser> getCurrentUser();
// delete Gist
    @DELETE("api/alrajhi_takaful/saaed_orders/{id}")
    Call<ResponseBody> CancelOrder(@Path("id") String id);
}
