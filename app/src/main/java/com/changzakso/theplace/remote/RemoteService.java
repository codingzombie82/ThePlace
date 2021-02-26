package com.changzakso.theplace.remote;

import com.changzakso.theplace.items.PlaceItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemoteService {
    String BASE_URL = "https://apiserver20210224192335.azurewebsites.net";
    String IMAGE_URL = BASE_URL + "/files/";

    @GET("/api/Place")
    Call<ArrayList<PlaceItem>> getListInfo();
}
