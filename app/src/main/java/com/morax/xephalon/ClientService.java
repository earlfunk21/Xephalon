package com.morax.xephalon;

import com.morax.xephalon.api.AuthApi;
import com.morax.xephalon.request.AuthRequest;

import kotlin.contracts.Returns;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {
    private static final String baseUrl = "http://192.168.0.101:6901/api/";

    public static AuthApi getAuthService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AuthApi.class);
    }
}
