package ir.popittv.myapplication.activitys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;

import ir.popittv.myapplication.R;
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

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private MainViewModel mainViewModel;
 //   private UserViewModel userViewModel;


    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String name_user;
    private String phone_user;
    private String code_user;
    private int id_user;
    private int id_vid_funny;

   private View bottomView;
    private View bottomView2;
    TextInputLayout et_phone;

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

        sharedPreferences=getSharedPreferences("user_info",MODE_PRIVATE);

        phone_user  = sharedPreferences.getString("phone_user", null);
        name_user = sharedPreferences.getString("name_user", null);
        id_user = sharedPreferences.getInt("id_user",0);

        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        mainViewModel.requestFunny_single(id_vid_funny);




        getFunny_single();
        initExo();


        if (phone_user==null) {
            login();
        }






    }



    private void login() {
        new Handler().postDelayed(() -> {

         loginUser();





        },5000);
    }

    private void loginUser() {
        String check = sharedPreferences.getString("phone_user", "");
        if (check.equals("")) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
            bottomSheetDialog.setContentView(bottomView);
            BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from((View) bottomView.getParent());
            sheetBehavior.setPeekHeight(

                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics())
            );


            bottomSheetDialog.show();
            et_phone = bottomView.findViewById(R.id.et_phone_userProfile);
            Button btn_send = bottomView.findViewById(R.id.send_customLogin);
            ProgressBar progressBar = bottomView.findViewById(R.id.progress_dialog);


            btn_send.setOnClickListener(V -> {

                if (et_phone.getEditText().getText().toString().equals("")) {
                    et_phone.setError("شماره را وارد کنید");

                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    phone_user = et_phone.getEditText().getText().toString();
                    editor = sharedPreferences.edit();
                    editor.putString("phone_user", phone_user);
                    editor.apply();
                    Service.getApiClient().userLogin(phone_user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            bottomSheetDialog.dismiss();

                            BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(PlayerActivity.this);
                            bottomView2 = getLayoutInflater().inflate(R.layout.coustom_dialog_code, null);
                            bottomSheetDialog2.setContentView(bottomView2);
                            bottomSheetDialog2.show();

                            TextInputLayout et_code = bottomView2.findViewById(R.id.et_code_userProfile);
                            Button btn_code = bottomView2.findViewById(R.id.sendCode_customLogin);
                            btn_code.setOnClickListener(v1 -> {

                                if (et_code.getEditText().getText().toString().equals("")) {
                                    et_code.setError("کد را وارد کنید");

                                } else {
                                    code_user = et_code.getEditText().getText().toString();
                                    Service.getApiClient().getUser(phone_user, code_user).enqueue(new Callback<UserDataModel>() {
                                        @Override
                                        public void onResponse(Call<UserDataModel> call1, @NonNull Response<UserDataModel> response1) {
                                            UserDataModel userDataModel = response1.body();
                                            if (response1.isSuccessful()) {
                                                assert userDataModel!=null;
                                                id_user = userDataModel.getUser_id();
                                                name_user = userDataModel.getName();
                                                editor = sharedPreferences.edit();
                                                editor.putString("name_user", name_user);
                                                editor.putInt("id_user", id_user);
                                                editor.apply();
                                                bottomSheetDialog2.dismiss();

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<UserDataModel> call1, Throwable t) {
                                            Toast.makeText(PlayerActivity.this, "wrong", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(PlayerActivity.this, "send again", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            });
        } else {
            String news = sharedPreferences.getString("phone_user", "");
            Toast.makeText(this, "قبلا وارد شده اید" + news, Toast.LENGTH_SHORT).show();
        }

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





    private void initExo() {
        try {

            exoPlayer = new  SimpleExoPlayer.Builder(this)

                    .build();
            binding.exoPlayer.setPlayer(exoPlayer);
            exoPlayer.prepare();
exoPlayer.addListener(new Player.EventListener() {
    @Override
    public void onTimelineChanged(Timeline timeline, int reason) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onStaticMetadataChanged(List<Metadata> metadataList) {

    }

    @Override
    public void onIsLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlaybackStateChanged(int state) {

    }

    @Override
    public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onExperimentalOffloadSchedulingEnabledChanged(boolean offloadSchedulingEnabled) {

    }

    @Override
    public void onExperimentalSleepingForOffloadChanged(boolean sleepingForOffload) {

    }

    @Override
    public void onEvents(Player player, Player.Events events) {

    }
});
            exoPlayer.addVideoListener(new VideoListener() {
                @Override
                public void onSurfaceSizeChanged(int width, int height) {

                }
            });
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

    private void hideSystemUi() {
        binding.exoPlayer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (exoPlayer==null) {
            initExo();
            hideSystemUi();
        }
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



}