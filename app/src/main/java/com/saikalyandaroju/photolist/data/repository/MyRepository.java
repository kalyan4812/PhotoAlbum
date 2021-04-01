package com.saikalyandaroju.photolist.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;

import java.util.List;

public interface MyRepository {

    MutableLiveData<List<Albums>> getAlbums();
    MutableLiveData<List<Albums>> getAlbums(int id);

    MutableLiveData<List<AlbumPhotos>> getPhotos(int id);
}
