package com.example.apiwork;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface APIDelete {
    @DELETE("zakazis/")
    Call<DataModal> deleteData(@Query("id")int id);
}
