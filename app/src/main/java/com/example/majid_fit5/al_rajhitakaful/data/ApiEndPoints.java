package com.example.majid_fit5.al_rajhitakaful.data;



import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {

   /* //Retrofit call to get list of blogs.
    @GET
    Call<List<Blog>> getBlogs(@Url String url);

    //Using Retrofit to get Blog Details
    @GET
    Call<Blog> getBlogDetails(@Url String url);*/
   @GET ("api/alrajhi_takaful/user/")
   Call<CurrentUser> getCurrentUser();

}
