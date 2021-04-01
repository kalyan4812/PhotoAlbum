package com.saikalyandaroju.photolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.saikalyandaroju.photolist.R;
import com.saikalyandaroju.photolist.data.adapter.PhotoAdapter;
import com.saikalyandaroju.photolist.data.model.AlbumPhotos;
import com.saikalyandaroju.photolist.viewmodel.CommonViewModelImplementor;

import java.util.List;

public class PhotosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static final String ALBUM_ID = "albumId";
    public static final String PHOTO_URL = "photo_url";
    RecyclerView recyclerView;
    private CommonViewModelImplementor commonViewModelImplementor;
    private PhotoAdapter photoAdapter;
    int albumId;
    private ShimmerFrameLayout shimmerFrameLayout;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        initViews();
        initRecyclerView();
        commonViewModelImplementor = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).
                get(CommonViewModelImplementor.class);

        if (getIntent() != null && getIntent().getIntExtra(ALBUM_ID, 0) > 0) {
            albumId = getIntent().getIntExtra(ALBUM_ID, 0);
            loadPhotos(albumId);
        }
        photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AlbumPhotos albumPhotos) {
                Intent intent=new Intent(PhotosActivity.this,LargePhotoActivity.class);
                intent.putExtra(PHOTO_URL,albumPhotos.getThumbnailUrl());
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        setTitle("Photos");
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();
    }

    private void loadPhotos(int albumId) {
        if (commonViewModelImplementor.getPhotos(albumId) != null) {
            commonViewModelImplementor.getPhotos(albumId)
                    .observe(this, new Observer<List<AlbumPhotos>>() {
                        @Override
                        public void onChanged(List<AlbumPhotos> albumPhotos) {

                            photoAdapter.setAlbumPhotos(albumPhotos);
                            photoAdapter.notifyDataSetChanged();
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            item.setVisible(true);
                        }
                    });
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        item = menu.findItem(R.id.search);
        item.setVisible(false);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) PhotosActivity.this);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonViewModelImplementor.getPhotos(albumId).removeObservers(this);
        getLifecycle().removeObserver(commonViewModelImplementor);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        photoAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            photoAdapter.getFilter().filter("");
        } else {
            photoAdapter.getFilter().filter(newText);
        }
        return false;
    }


}
