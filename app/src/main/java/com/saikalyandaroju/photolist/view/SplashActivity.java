package com.saikalyandaroju.photolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.airbnb.lottie.LottieAnimationView;
import com.saikalyandaroju.photolist.R;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    public static final int SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lottieAnimationView = findViewById(R.id.photoanimation);
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
        new CustomHandler().postDelayed(new CustomRunnable(), SCREEN);

    }

    public static class CustomHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    private class CustomRunnable implements Runnable {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
