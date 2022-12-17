package com.example.apiwork;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("zakazis")

    Call<DataModal> createPost(@Body DataModal dataModal);
}
