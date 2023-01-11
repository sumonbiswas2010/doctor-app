package com.example.doctoronline;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/users/login/")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/users/")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/api/users/doc/")
    Call<DocResult> executeDoc(@Body HashMap<String, String> map);

    @POST("/api/users/docID/")
    Call<DocData> executeDocData(@Body HashMap<String, String> map);

}
