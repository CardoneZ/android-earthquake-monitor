package com.sdi.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;

public class ApiClient {

    public interface EqService {
        @GET("all_hour.geojson")
        Call<EarthquakeJSONResponse> getEarthquakes();
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    private EqService service;

    private static final ApiClient apiClient = new ApiClient();

    public static ApiClient getInstance(){
        return apiClient;
    }

    private ApiClient(){

    }

    public EqService getService(){
        if(service==null){
            service = retrofit.create(EqService.class);
        }
        return service;
    }

}
