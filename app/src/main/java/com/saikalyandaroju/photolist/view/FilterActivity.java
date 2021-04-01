package com.saikalyandaroju.photolist.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.saikalyandaroju.photolist.R;
import com.saikalyandaroju.photolist.viewmodel.CommonViewModelImplementor;

public class FilterActivity extends AppCompatActivity {

    NumberPicker numberPicker;
    LinearLayout removefilter, addfilter, filterlayot;
    ImageView add, remove;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String USER_ID = "userID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initPreferences();
        initViews();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterlayot.setVisibility(View.VISIBLE);
                removefilter.setVisibility(View.VISIBLE);
                addfilter.setVisibility(View.GONE);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addfilter.setVisibility(View.VISIBLE);
                filterlayot.setVisibility(View.GONE);
                removefilter.setVisibility(View.GONE);
                numberPicker.setVisibility(View.GONE);
                editor.putInt(USER_ID, 0).apply();

            }
        });
    }

    private void initPreferences() {
        sharedPreferences = getSharedPreferences("userIdData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    private void initViews() {
        setTitle("Filter Albums");
        numberPicker = findViewById(R.id.number_picker_priority);
        removefilter = findViewById(R.id.remove_linear);
        addfilter = findViewById(R.id.add_linear);
        filterlayot = findViewById(R.id.filterlayout);
        add = findViewById(R.id.addfilter);
        remove = findViewById(R.id.clearfilter);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        if (sharedPreferences.getInt(USER_ID, 0) > 0) {
            addfilter.setVisibility(View.GONE);
            removefilter.setVisibility(View.VISIBLE);
            filterlayot.setVisibility(View.VISIBLE);
            numberPicker.setValue(sharedPreferences.getInt(USER_ID, 1));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.album_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_filter:
                saveFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveFilter() {
        if (numberPicker.getVisibility() == View.VISIBLE) {
            int userId = numberPicker.getValue();


            editor.putInt(USER_ID, userId).apply();


            Intent intent = new Intent();
            intent.putExtra("userId", userId);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

}
