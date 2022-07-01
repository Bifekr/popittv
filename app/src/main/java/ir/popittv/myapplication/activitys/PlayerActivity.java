package ir.popittv.myapplication.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.IOException;

import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.utils.LoginDialogFragment;
import ir.popittv.myapplication.utils.OnClickLoginDialog;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import ir.popittv.myapplication.viewmodel.UserViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerActivity extends AppCompatActivity implements OnClickLoginDialog {

    private ActivityPlayerBinding binding;
    private MainViewModel mainViewModel;
 //   private UserViewModel userViewModel;

    private int id_vid_funny;
    private String user_phone;

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
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
       // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);



        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        mainViewModel.requestFunny_single(id_vid_funny);

        getFunny_single();
        initExo();

        login();



    }

    private void login() {
        new Handler().postDelayed(() -> {

            new LoginDialogFragment(this)
                    .show(getSupportFragmentManager(),LoginDialogFragment.TAG);





        },5000);
    }

    @Override
    public void phoneClic(String phone) {
        user_phone=phone;



Toast.makeText(this, "runnable"+phone, Toast.LENGTH_SHORT).show();
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
        mainViewModel.getFunny_single().observe(this, funnyDataModel -> {
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