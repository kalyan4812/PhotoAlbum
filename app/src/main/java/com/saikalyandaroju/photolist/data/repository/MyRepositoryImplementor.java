package com.saikalyandaroju.photolist.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.saikalyandaroju.photolist.data.RemoteSource.Retrofit.PhotoApiClient;
import com.saikalyandaroju.photolist.data.adapter.AlbumAdapter;
import com.saikalyandaroju.photolist.data.adapter.PhotoAdapter;
import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.data.model.Albums;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyRepositoryImplementor implements MyRepository {
    private static MyRepositoryImplementor myRepositoryImplementor;

    private MutableLiveData<List<Albums>> mutablealbums;
    private MutableLiveData<List<AlbumPhotos>> mutablephotos;
    private CompositeDisposable compositeDisposable;


    public static MyRepositoryImplementor getrepositoryinstance() {
        if (myRepositoryImplementor == null) {
            myRepositoryImplementor = new MyRepositoryImplementor();
        }
        return myRepositoryImplementor;
    }

    private MyRepositoryImplementor() {
        compositeDisposable = new CompositeDisposable();


    }


    private void makeAlbumApiCall() {
        PhotoApiClient.getRequestApi().getAlbums().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Albums>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Albums> albums) {

                        mutablealbums.postValue(albums);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                        mutablealbums = null;

                    }
                });
    }


    @Override
    public MutableLiveData<List<Albums>> getAlbums(int id) {
        if (mutablealbums == null) {
            mutablealbums = new MutableLiveData<>();
            makeAlbumApiCallForUser(id);
        }
        return mutablealbums;
    }

    private void makeAlbumApiCallForUser(int id) {
        PhotoApiClient.getRequestApi().getAlbums(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Albums>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Albums> albums) {
                        mutablealbums.postValue(albums);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                        mutablealbums = null;
                    }
                });
    }

    private void makePhotoApiCall(int id) {
        PhotoApiClient.getRequestApi().getPhotos(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AlbumPhotos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<AlbumPhotos> albumPhotos) {

                        mutablephotos.postValue(albumPhotos);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                        mutablephotos = null;
                    }
                });
    }


    @Override
    public MutableLiveData<List<Albums>> getAlbums() {
        if (mutablealbums == null) {
            mutablealbums = new MutableLiveData<>();
            makeAlbumApiCall();
        }
        return mutablealbums;
    }

    @Override
    public MutableLiveData<List<AlbumPhotos>> getPhotos(int id) {
        if (mutablephotos == null) {
            mutablephotos = new MutableLiveData<>();
            makePhotoApiCall(id);
        }
        return mutablephotos;
    }

}
