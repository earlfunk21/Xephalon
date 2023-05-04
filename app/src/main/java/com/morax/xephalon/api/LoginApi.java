package com.morax.xephalon.api;

import com.morax.xephalon.request.LoginRequest;
import com.morax.xephalon.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}