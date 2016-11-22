package com.apple.contactsretrofitapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://api.androidhive.info/";
    private static final String TAG = "RetroFitContactAPI";
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    List<UserPojo.ContactsBean> Users;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
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

        MyApiEndPointInterface service = retrofitRef.create(MyApiEndPointInterface.class);
        Call<UserPojo> call = service.getHiveDetails();

        call.enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, retrofit2.Response<UserPojo> response) {
                Log.d(TAG,"OnResponse: "+ response.code());

                if(response.isSuccessful()){
                    Users = new ArrayList<UserPojo.ContactsBean>();


                    UserPojo result = response.body();
                    Users = result.getContacts();


                    mAdapter = new UserAdapter(Users);


                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {

            }
        });


    }
}
