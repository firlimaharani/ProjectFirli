package com.example.projectfirli;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectzulaeha.R;


public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView; // Deklarasi VideoView sebagai variabel global
    private int originalOrientation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Get the video URI from the intent
        String videoPath = getIntent().getStringExtra("VIDEO_PATH");
        Uri uri = Uri.parse(videoPath);

        // Set up VideoView
        videoView = findViewById(R.id.videoView); // Inisialisasi variabel videoView
        videoView.setVideoURI(uri);

        // Set up MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Start playing the video
        videoView.start();

        // Get the original orientation
        originalOrientation = getResources().getConfiguration().orientation;

        // Adjust video layout based on the original orientation
        adjustVideoLayout(originalOrientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Detect orientation changes and adjust VideoView layout
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation != originalOrientation) {
            adjustVideoLayout(currentOrientation);
            originalOrientation = currentOrientation;
        }
    }

    private void adjustVideoLayout(int orientation) {
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            // Landscape mode
            setFullscreen(true);
        } else {
            // Portrait or other orientations
            setFullscreen(false);
        }
    }

    private void setFullscreen(boolean fullscreen) {
        if (fullscreen) {
            // Hide action bar
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            // Set VideoView layout parameters for fullscreen
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            videoView.setLayoutParams(params);

            // Hide navigation bar and status bar
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            // Show action bar
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }

            // Set VideoView layout parameters for normal mode
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            videoView.setLayoutParams(params);

            // Show navigation bar and status bar
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            // Tambahkan logika untuk kembali ke halaman sebelumnya atau finish activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}