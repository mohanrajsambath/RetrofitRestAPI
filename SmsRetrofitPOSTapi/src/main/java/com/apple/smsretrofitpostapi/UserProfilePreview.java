package com.apple.smsretrofitpostapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;*/

/**
 * Created by apple1 on 21/11/16.
 */

public class UserProfilePreview extends AppCompatActivity  implements View.OnClickListener{
    public  final String BASE_URL = "https://demo.youthhub.co.nz/api/v1/";
    public  final String IMAGE_URL = "https://demo.youthhub.co.nz/";
    private static final String TAG = "RetroFitSmsPostAPI";
    boolean  mStatus;
    private TextView txtVw_UserName,txtVw_Token,txtVw_fName,txtVw_lName,txtVw_mPath,txtVw_tPath,txtVw_cPath,txtVw_profpicname,txtVw_coverpicname;
    private Button btn_getValues;
    private ImageView imgVw_ProfilePicImage,imgVw_CoverImage;
    Retrofit retrofitRef;
    private Integer mCount;
    private SmsProfilePojo.Data mData;
    private String mToken="", mUserName="",mId,mFirstName="",mLastName="",mProfilePic="",mCoverPic="",mPathMedium="",mPathThumb="",mPathCover="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mToken= null;
                mUserName= null;
            } else {
                mToken= extras.getString("TOKEN");
                mUserName= extras.getString("USERNAME");
            }
        } else {
            mToken= (String) savedInstanceState.getSerializable("TOKEN");
            mUserName= (String) savedInstanceState.getSerializable("USERNAME");
        }

        getConfigViews();

    }

    private void getConfigViews() {

        //txtVw_UserName,txtVw_Token,txtVw_fName,txtVw_lName,txtVw_mPath,txtVw_tPath,txtVw_cPath,txtVw_profpicname,txtVw_coverpicname

        txtVw_UserName=(TextView)findViewById(R.id.txtVw_UserName);
        txtVw_Token=(TextView)findViewById(R.id.txtVw_Token);
        txtVw_fName=(TextView)findViewById(R.id.txtVw_fName);
        txtVw_lName=(TextView)findViewById(R.id.txtVw_lName);
        txtVw_mPath=(TextView)findViewById(R.id.txtVw_mPath);
        txtVw_tPath=(TextView)findViewById(R.id.txtVw_tPath);
        txtVw_cPath=(TextView)findViewById(R.id.txtVw_cPath);
        txtVw_profpicname=(TextView)findViewById(R.id.txtVw_profpicname);
        txtVw_coverpicname=(TextView)findViewById(R.id.txtVw_coverpicname);

        btn_getValues=(Button)findViewById(R.id.btn_getValues);
        imgVw_ProfilePicImage=(ImageView)findViewById(R.id.imgVw_ProfilePicImage);
        imgVw_CoverImage=(ImageView)findViewById(R.id.imgVw_CoverImage);




        txtVw_UserName.setText(mUserName);
        txtVw_Token.setText(mToken);
        btn_getValues.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_getValues:
                getApiCAll();

            break;

        }
    }

    private void getApiCAll() {


        /*OkHttpClient okClient = new OkHttpClient.Builder() .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .header("Authorization", "Bearer "+mToken).build();
                return chain.proceed(request);
            }
        }).build();*/

        OkHttpClient okClient = new OkHttpClient.Builder() .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .addHeader("Accept","Application/JSON").build();
                return chain.proceed(request);
            }
        }).build();

         retrofitRef = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final MyApiEndPointInterface service = retrofitRef.create(MyApiEndPointInterface.class);
        String myTokenStr = "Bearer "+mToken;
        Call<SmsProfilePojo> call = service.getProfileDetails(myTokenStr);
        call.enqueue(new Callback<SmsProfilePojo>() {
            @Override
            public void onResponse(Call<SmsProfilePojo> call, retrofit2.Response<SmsProfilePojo> response) {
                Log.d(TAG,"OnResponse: "+ response.code());

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();

                    SmsProfilePojo result = response.body();
                    mStatus = result.getStatus();
                    mCount = result.getCount();
                    mData = result.getData();
                    mId = mData.getId();
                    mFirstName = mData.getFirstName();
                    mLastName = mData.getLastName();
                    mProfilePic = mData.getProfilePic();
                    mCoverPic = mData.getCoverPic();
                    mPathMedium = result.getMediumPath();
                    mPathThumb = result.getThumbnailPath();
                    mPathCover = result.getCoverPath();
                    mToken = result.getToken();

                    String myProfImageUrl= IMAGE_URL+mPathMedium+mProfilePic;

                    Glide.with(UserProfilePreview.this)
                            .load(myProfImageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .into(imgVw_ProfilePicImage);

                    String myCoverImageUrl= IMAGE_URL+mPathCover+mCoverPic;

                    Glide.with(UserProfilePreview.this)
                            .load(myCoverImageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .into(imgVw_CoverImage);
                    //txtVw_UserName,txtVw_Token,txtVw_fName,txtVw_lName,txtVw_mPath,txtVw_tPath,txtVw_cPath,txtVw_profpicname,txtVw_coverpicname
                    txtVw_fName.setText(mFirstName);
                    txtVw_lName.setText(mLastName);
                    txtVw_mPath.setText(mPathMedium);
                    txtVw_tPath.setText(mPathThumb);
                    txtVw_cPath.setText(mPathCover);
                    txtVw_profpicname.setText(mProfilePic);
                    txtVw_coverpicname.setText(mCoverPic);
                    txtVw_Token.setText(mToken);

                   /* {
                        "status": true,
                            "count": 1,
                            "data": {
                        "id": "6022",
                                "first_name": "Michelle",
                                "last_name": "Smith",
                                "profile_pic": "profess_sw_engg.jpg",
                                "cover_pic": "IMG_20161017_183306.jpg"
                    },
                        "medium_path": "uploads/profile/medium/",
                            "thumbnail_path": "uploads/profile/thumbnail/",
                            "cover_path": "uploads/cover_photo/",
                            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjYwMjIiLCJ1c2VybmFtZSI6ImlvYW5hc2FsYS1zZXRpdSJ9LCJpYXQiOjE0Nzk3MjcwNjEsImp0aSI6IldWQlZlRGRLZW10R2N6QnFSV2RrTWxZeWRVbz0iLCJpc3MiOiJodHRwOlwvXC9kZW1vLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE0Nzk3MjcwNjEsImV4cCI6MTQ4MDMzMTg2MX0.9l4PukS-8Ickg6gsJUKX3SIs6OECNy_eFRzuF7maXN0"
                    }*/

                }else{
                    Toast.makeText(getApplicationContext(),"Fail in  Response",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SmsProfilePojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
