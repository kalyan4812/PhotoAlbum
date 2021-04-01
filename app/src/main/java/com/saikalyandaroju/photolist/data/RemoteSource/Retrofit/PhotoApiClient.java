package com.saikalyandaroju.photolist.data.RemoteSource.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.saikalyandaroju.photolist.data.RemoteSource.Retrofit.APIEndPoints.BASE_URL;

public class PhotoApiClient {


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PhotoApiService requestApi = retrofit.create(PhotoApiService.class);

    public static PhotoApiService getRequestApi() {
        return requestApi;
    }
}
