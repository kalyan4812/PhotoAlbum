package com.saikalyandaroju.photolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.saikalyandaroju.photolist.R;
import com.squareup.picasso.Picasso;

public class LargePhotoActivity extends AppCompatActivity {
    public static final String PHOTO_URL = "photo_url";
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_photo);
        photo = findViewById(R.id.largephoto);
        if (getIntent() != null && getIntent().getStringExtra(PHOTO_URL) != null) {
            Picasso.get().load(getIntent().getStringExtra(PHOTO_URL)).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).
                    into(photo);
        }
    }
}
