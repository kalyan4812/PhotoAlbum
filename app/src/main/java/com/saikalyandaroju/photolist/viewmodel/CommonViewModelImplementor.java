package com.saikalyandaroju.photolist.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;
import com.saikalyandaroju.photolist.data.repository.MyRepositoryImplementor;

import java.util.List;

public class CommonViewModelImplementor extends ViewModel implements CommonViewModel {
    private MyRepositoryImplementor myRepositoryImplementor;
    private LiveData<List<Albums>> albums;
    private LiveData<List<AlbumPhotos>> albumphotos;

    public CommonViewModelImplementor() {
        myRepositoryImplementor = MyRepositoryImplementor.getrepositoryinstance();
        //albums = new MutableLiveData<>();
        //albums = myRepositoryImplementor.getAlbums();
    }


    @Override
    public LiveData<List<Albums>> getAlbums() {

        albums = myRepositoryImplementor.getAlbums();

        return albums;

    }


    @Override
    public LiveData<List<AlbumPhotos>> getPhotos(int id) {

        albumphotos = myRepositoryImplementor.getPhotos(id);

        return albumphotos;
    }

    @Override
    public LiveData<List<Albums>> getAlbums(int id) {
        albums = myRepositoryImplementor.getAlbums(id);
        return albums;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void refreshData() {
        albums = myRepositoryImplementor.getAlbums();
    }
}
