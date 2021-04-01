
package com.saikalyandaroju.photolist.data.RemoteSource.Retrofit;


import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoApiService {
    @GET("albums")
    Observable<List<Albums>> getAlbums();

    @GET("albums")
    Observable<List<Albums>> getAlbums(@Query("userId") Integer id);

    @GET("photos")
    Observable<List<AlbumPhotos>> getPhotos(@Query("albumId") Integer id);


}
