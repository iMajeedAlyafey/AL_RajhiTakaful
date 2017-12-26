package com.example.majid_fit5.al_rajhitakaful.data;



import retrofit2.Call;
import retrofit2.http.Body;
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



}
