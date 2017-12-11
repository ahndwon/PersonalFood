package com.team11.personalfood.Post;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.squareup.otto.Produce;
import com.team11.personalfood.ListActivity;
import com.team11.personalfood.LoginActivity;
import com.team11.personalfood.Models.Chat;
import com.team11.personalfood.Models.CurrentUser;
import com.team11.personalfood.Models.JoinData;
import com.team11.personalfood.Models.LoginData;
import com.team11.personalfood.Post.Events.ErrorEvent;
import com.team11.personalfood.Post.Events.ServerEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {
    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://13.230.142.157:8080";
    private static final String CHAT_SERVER_URL = "http://13.230.142.157:8081";

    public static CurrentUser currentUser;

    private Context mContext;

    public Communicator(Context context) {
        this.mContext = context;
    }

    public void joinPost(String userId, String password, String name, String birth, String type) {
        Log.d(TAG, "joinPost executing");


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Call<ServerResponse> call = service.postJoin(new JoinData(userId, password, name, birth, type));
        Log.d(TAG, "String.valueOf(call)" + String.valueOf(call));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, " joinPost isSuccessful:" + response.isSuccessful());
                    return;
                }
                // response.isSuccessful() is true if the response code is 2xx

                Log.d(TAG, " joinPost isSuccessful:" + response.isSuccessful());

                ServerResponse mResponse = response.body();
                currentUser = new CurrentUser(mResponse.getUserID(), mResponse.getPassword(),
                        mResponse.getName(), mResponse.getBirth(), mResponse.getType());
                Log.d(TAG, " joinPost currentUser:" + response.isSuccessful());
//                Intent intent = new Intent(mContext,ListActivity.class);
//                mContext.startActivity(intent);

                Log.d(TAG, "currentUser - " + currentUser.getUserID() + currentUser.getPassword()
                        + currentUser.getName() + currentUser.getBirth() + currentUser.getType());
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG, "PostSuccess");


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });


    }

    public void loginPost(String userId, String password) {
        Log.d(TAG, "loginPost executing");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.postLogin(new LoginData(userId, password));
        Log.d(TAG, String.valueOf(call));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "loginPost isSuccessful:" + response.isSuccessful());

                    return;
                }
                // response.isSuccessful() is true if the response code is 2xx
                Log.d(TAG, "response" + response);
                Log.d(TAG, "loginPost isSuccessful:" + response.isSuccessful());

                ServerResponse mResponse = response.body();
                currentUser = new CurrentUser(mResponse.getUserID(), mResponse.getPassword(),
                        mResponse.getName(), mResponse.getBirth(), mResponse.getType());

//                Intent intent = new Intent(mContext,ListActivity.class);
//                mContext.startActivity(intent);

                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.d(TAG, "isSuccessful:" + response.isSuccessful());
                Log.e(TAG, "POstSuccess");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });


    }
}
