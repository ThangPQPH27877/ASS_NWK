package com.example.asm1.Api;

import com.example.asm1.Model.ProductModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    // ip may ao connect localhost: 10.0.2.2
    // genymontion: 10.0.3.2
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("listCar")
    Call<List<ProductModel>> getProduct();

    @POST("addCars")
    Call<List<ProductModel>> addCar(@Body ProductModel productModel);

    @PUT("cars/{id}")
    Call<List<ProductModel>> updateCar(@Path("id") String id, @Body ProductModel productModel);

    @DELETE("/cars/{id}")
    Call<List<ProductModel>> deleteCars(@Path("id") String id);

}
