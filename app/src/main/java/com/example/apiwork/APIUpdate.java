package com.example.apiwork;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIUpdate {
    @PUT("zakazis/")
    Call<DataModal> updateData(@Query("id")int id, @Body DataModal dataModal);

}
