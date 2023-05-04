package com.morax.xephalon;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.morax.xephalon.api.LoginApi;
import com.morax.xephalon.request.LoginRequest;
import com.morax.xephalon.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.morax.xephalon", appContext.getPackageName());
    }

    @Test
    public void testLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.101:6901/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginApi loginApi = retrofit.create(LoginApi.class);

        String username = "morax";
        String password = "123123";

        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<LoginResponse> call = loginApi.login(loginRequest);

        try {
            Response<LoginResponse> response = call.execute();
            LoginResponse loginResponse = response.body();
            String jwt = loginResponse.getToken();
            assertNotNull(jwt);
        } catch (Exception e) {
            fail("Failed to login: " + e.getMessage());
        }
    }
}