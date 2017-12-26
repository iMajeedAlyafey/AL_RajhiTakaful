package com.example.majid_fit5.al_rajhitakaful.data;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */


import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

public interface DataSource {
    /*
            interface BaseCallBack {
                void onFailure(TakafulError error);
            }

                    // any call which implements this interface, will call retrofit.
                    interface GetBlogsCallBack extends BaseCallBack{
                        void onGetBlogs(List<Blog> blogs);
                    }
                    //Real implement for this method will found in RemoteDataSource Class, because all blog data located in the server
                    void getBlogs(String url, GetBlogsCallBack callBack);
                    }
                  */
    //---------------------------------------------------------------------------------------------------------------------
    // any call which implements this interface, will call retrofit.
    interface GetCurrentUserCallCack  {
        void onGetCurrentUser(CurrentUser currentUser);
    }
    //Real implement for this method will found in RemoteDataSource Class, because all blog data located in the server
    void getCurrentUser(GetCurrentUserCallCack callCack);
    //--------------------------------------------------------------------------------------------------------------------
}