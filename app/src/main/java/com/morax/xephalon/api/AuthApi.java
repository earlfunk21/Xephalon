package com.morax.xephalon.api;

import com.morax.xephalon.request.AuthRequest;
import com.morax.xephalon.response.LoginResponse;
import com.morax.xephalon.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/login")
    Call<LoginResponse> login(@Body AuthRequest authRequest);

    @POST("auth/create")
    Call<RegisterResponse> register(@Body AuthRequest authRequest);
}