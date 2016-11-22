package com.apple.contactsretrofitapi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gokulrajkumar on 05/11/16.
 */

public interface MyApiEndPointInterface {

    @GET("contacts/")
    Call<UserPojo> getHiveDetails();

    /*@FormUrlEncoded
    @POST("/TRAITS_Webapi/WSProvide/SendUsersForReg")
    Call<UserPojo> getUser(@Field("username") String username,
                       @Field("password") String password);*/

}
