package com.saikalyandaroju.photolist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saikalyandaroju.photolist.R;
import com.saikalyandaroju.photolist.data.adapter.AlbumAdapter;
import com.saikalyandaroju.photolist.data.model.Albums;
import com.saikalyandaroju.photolist.viewmodel.CommonViewModelImplementor;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String ALBUM_ID = "albumId";
    public static final String USER_ID = "userID";
    public static final int REQUEST_ID = 100;
    RecyclerView recyclerView;
    CommonViewModelImplementor commonViewModelImplementor;
    AlbumAdapter albumAdapter;
    FloatingActionButton floatingActionButton;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPreferences();
        initViews();
        initRecyclerView();
        commonViewModelImplementor = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).
                get(CommonViewModelImplementor.class);
        loadAlbums();

        albumAdapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Albums albums) {
                Log.i(TAG, albums.getId() + "");
                Intent i = new Intent(MainActivity.this, PhotosActivity.class);
                i.putExtra(ALBUM_ID, albums.getId());
                startActivity(i);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FilterActivity.class);
                startActivityForResult(i, REQUEST_ID);
            }
        });
    }

    private void initPreferences() {
        sharedPreferences = getSharedPreferences("userIdData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getInt(USER_ID, 0);
    }

    private void initViews() {
        setTitle("Albums");
        floatingActionButton = findViewById(R.id.filter);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void loadAlbums() {

        if (userID > 0) {

            commonViewModelImplementor.getAlbums(userID).observe(this, new Observer<List<Albums>>() {
                @Override
                public void onChanged(List<Albums> albums) {
                    albumAdapter.setAlbums(albums);
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            });


        } else {
            commonViewModelImplementor.getAlbums().observe(this, new Observer<List<Albums>>() {
                @Override
                public void onChanged(List<Albums> albums) {
                    albumAdapter.setAlbums(albums);
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        albumAdapter = new AlbumAdapter();
        recyclerView.setAdapter(albumAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID && resultCode == RESULT_OK && data != null) {
            int userId = data.getIntExtra("userId", 0);
            userID = sharedPreferences.getInt(USER_ID, 0);
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            loadAlbums();
        }
        else if(requestCode==REQUEST_ID && data==null){
            userID = sharedPreferences.getInt(USER_ID, 0);
            loadAlbums();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonViewModelImplementor.getAlbums().removeObservers(this);
        commonViewModelImplementor.getAlbums(userID).removeObservers(this);
        getLifecycle().removeObserver(commonViewModelImplementor);
    }
}
