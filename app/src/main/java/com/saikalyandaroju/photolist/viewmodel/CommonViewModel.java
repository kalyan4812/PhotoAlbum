package com.saikalyandaroju.photolist.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;

import java.util.List;

public interface CommonViewModel extends LifecycleObserver {

    LiveData<List<Albums>> getAlbums();
    LiveData<List<Albums>> getAlbums(int id);
    LiveData<List<AlbumPhotos>> getPhotos(int id);
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void refreshData();
}
