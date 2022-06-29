package ir.popittv.myapplication.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.utils.LoginDialogFragment;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private MainViewModel viewModel;

    private int id_vid_funny;

    SimpleExoPlayer exoPlayer;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playBackPosition = 0;
    MediaItem mediaItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        viewModel.requestFunny_single(id_vid_funny);

        getFunny_single();
        initExo();

        login();



    }

    private void login() {
        new Handler().postDelayed(() -> {

            new LoginDialogFragment()
                    .show(getSupportFragmentManager(),LoginDialogFragment.TAG);

            //Toast.makeText(this, "runnable", Toast.LENGTH_SHORT).show();
            
        },5000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initExo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer!=null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playBackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (exoPlayer==null) {
            initExo();
            hideSystemUi();
        }
    }

    private void hideSystemUi() {
        binding.exoPlayer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    private void initExo() {
        try {

            exoPlayer = new SimpleExoPlayer.Builder(this).build();
            binding.exoPlayer.setPlayer(exoPlayer);
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playBackPosition);



        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }

    private void getFunny_single() {
        viewModel.getFunny_single().observe(this, funnyDataModel -> {
            binding.titleEnVideoPlayer.setText(funnyDataModel.getTitle_en());
            binding.titleFaVideoPlayer.setText(funnyDataModel.getTitle_fa());
            binding.titleEnChannelPlayer.setText(funnyDataModel.getName_chan_en());
            binding.titleFaChannelPlayer.setText(funnyDataModel.getName_chan_fa());
            binding.titleSubPlayer.setText(funnyDataModel.getFollowers());
            binding.titleViewPlayer.setText(funnyDataModel.getView());
            binding.titleLikePlayer.setText(funnyDataModel.getLiky());

            Glide.with(this).load(funnyDataModel.getProfile_chann())
                    .into(binding.ivProfileItemAllChan);

            mediaItem = MediaItem.fromUri(funnyDataModel.getLink_480());
            exoPlayer.addMediaItem(mediaItem);

           // Toast.makeText(this, "" + funnyDataModel.getLink_480(), Toast.LENGTH_SHORT).show();
        });
    }


}