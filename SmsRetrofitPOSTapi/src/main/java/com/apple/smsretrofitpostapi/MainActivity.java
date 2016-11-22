package com.apple.smsretrofitpostapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

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

public class MainActivity extends AppCompatActivity {

    //https://demo.youthhub.co.nz/api/v1/authorize
    //username:ioanasala-setiu
    //password:Trd@sm20

    /**
     * {
     "status": true,
     "id": "6022",
     "profile_pic": "profess_sw_engg.jpg",
     "cover_pic": "IMG_20161017_183306.jpg",
     "medium_path": "uploads/profile/medium/",
     "thumbnail_path": "uploads/profile/thumbnail/",
     "cover_path": "uploads/cover_photo/",
     "user_name": "Michelle Smith",
     "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjYwMjIiLCJ1c2VybmFtZSI6ImlvYW5hc2FsYS1zZXRpdSJ9LCJpYXQiOjE0Nzk3MTA5NTQsImp0aSI6ImJuTmxiMEp5ZEVNd1ZHNWpSVzVYWmpKcVpXTT0iLCJpc3MiOiJodHRwOlwvXC9kZW1vLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE0Nzk3MTA5NTQsImV4cCI6MTQ4MDMxNTc1NH0.nKjasiQNEfxtc0wURqhs82vcSLE_VkZ7wevCzakiYoM"
     }*/
    private EditText edT_username,edT_password;
    private Button btn_Cancel,btn_Login;
    private TextView txtVw_user_name;
    private ImageView imgView_ProfilePic;
    private RelativeLayout rLayout_PreviewUser;

    public  final String BASE_URL = "https://demo.youthhub.co.nz/api/v1/";
    public  final String IMAGE_URL = "https://demo.youthhub.co.nz/";
    public  final String TAG = "RetroFitSmsPostAPI";
    private String mUserNm="",mProfilePic="",mPathMedium="",mToken="";
    private boolean isApiExecuted=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edT_username =(EditText)findViewById(R.id.edT_username);
        edT_password =(EditText)findViewById(R.id.edT_password);
        btn_Cancel =(Button)findViewById(R.id.btn_Cancel);
        btn_Login =(Button)findViewById(R.id.btn_Login);
        txtVw_user_name =(TextView)findViewById(R.id.txtVw_user_name);
        imgView_ProfilePic =(ImageView)findViewById(R.id.imgView_ProfilePic);
        rLayout_PreviewUser =(RelativeLayout)findViewById(R.id.rLayout_PreviewUser);
        /*Glide.with(this)
                                    .load("http://nuuneoi.com/uploads/source/playstore/cover.jpg")
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivImgGlide);*/

        OkHttpClient okClient = new OkHttpClient.Builder() .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request().newBuilder()
                                .addHeader("Accept","Application/JSON").build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofitRef = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MyApiEndPointInterface service = retrofitRef.create(MyApiEndPointInterface.class);
         //Call<SmsLogin> call;

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username =edT_username.getText().toString().trim();
                String password =edT_password.getText().toString().trim();


                Call<SmsLogin> call = service.getUser(username,password);

                call.enqueue(new Callback<SmsLogin>() {
                    @Override
                    public void onResponse(Call<SmsLogin> call, retrofit2.Response<SmsLogin> response) {
                        Log.d(TAG,"OnResponse: "+ response.code());

                        if(response.isSuccessful()){
                            isApiExecuted = true;
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();

                            SmsLogin result = response.body();

                             mUserNm = result.getUserName();
                             mPathMedium = result.getMediumPath();
                             mProfilePic = result.getProfilePic();
                             mToken = result.getToken();

                            System.out.println("Result UserName:"+mUserNm);
                            System.out.println("Result PathMedium:"+mPathMedium);
                            System.out.println("Result ProfilePic:"+mProfilePic);
                            txtVw_user_name.setText(mUserNm);
                            String myImageUrl= IMAGE_URL+mPathMedium+mProfilePic;
//.load("http://nuuneoi.com/uploads/source/playstore/cover.jpg")
                            Glide.with(MainActivity.this)
                                    .load(myImageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgView_ProfilePic);

                            /*Picasso.with(MainActivity.this)
                                    .load(myImageUrl)
                                    .placeholder(R.drawable.ic_person_black_24dp) // optional
                                    .error(R.drawable.ic_close_black_24dp)         // optional
                                    .into(imgView_ProfilePic);*/


                           /* Users = new ArrayList<UserPojo.ContactsBean>();


                            UserPojo result = response.body();
                            Users = result.getContacts();


                            mAdapter = new UserAdapter(Users);


                            mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);*/


                        }
                    }

                    @Override
                    public void onFailure(Call<SmsLogin> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        rLayout_PreviewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(MainActivity.this,UserProfilePreview.class);
                io.putExtra("USERNAME",mUserNm);
                io.putExtra("TOKEN",mToken);
                startActivity(io);


            }
        });








    }


}
