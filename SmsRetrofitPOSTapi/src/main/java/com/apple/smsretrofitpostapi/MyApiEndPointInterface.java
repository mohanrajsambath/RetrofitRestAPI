package com.apple.smsretrofitpostapi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by gokulrajkumar on 05/11/16.
 */

public interface MyApiEndPointInterface {

   /* @GET("contacts/")
    Call<UserPojo> getHiveDetails();*/

    @FormUrlEncoded
    @POST("authorize/")
    Call<SmsLogin> getUser(@Field("username") String username,
                       @Field("password") String password);

    //GetProfile Details
     @GET("getprofile")
    Call<SmsProfilePojo> getProfileDetails(@Header("Authorizations") String myTokenValue);

}
