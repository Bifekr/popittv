package ir.popittv.myapplication.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerActivity extends AppCompatActivity  {

    SharedPreferences.Editor editor;
    TextInputLayout et_phone;
    //   private UserViewModel userViewModel;
    ImageView btn_fullScreen;
    SimpleExoPlayer exoPlayer;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playBackPosition = 0;
    boolean flag = true;
    MediaItem mediaItem;
    private ActivityPlayerBinding binding;
    private MainViewModel mainViewModel;
    private SharedPreferences sharedPreferences;
    private String name_user;
    private String phone_user;
    private String code_user;
    private String url_link;
    private int id_user;
    private int id_vid_funny;
    private View bottomView;
    private View bottomView2;


/////////////////////////////////////////////////////////////////////////////////

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private PlayerView playerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private  DataSource.Factory dataSourceFactory;

    private SimpleExoPlayer player;

    private int mResumeWindow;
    private long mResumePosition;

////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        phone_user = sharedPreferences.getString("phone_user", null);
        name_user = sharedPreferences.getString("name_user", null);
        id_user = sharedPreferences.getInt("id_user", 0);

        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        url_link = getIntent().getStringExtra("link");
        Toast.makeText(this, ""+url_link, Toast.LENGTH_SHORT).show();
        mainViewModel.requestFunny_single(id_vid_funny);


        getFunny_single();
        initExo();
     /*   btn_fullScreen = binding.exoPlayer.findViewById(R.id.bt_fullscreen);
        btn_fullScreen.setOnClickListener(v -> {

        });
*/
      //   if (phone_user==null) { login();}

///////////////////////
        dataSourceFactory =
                new DefaultDataSourceFactory(
                        this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }







      ////////////////////////////////////


    }




    ////////////////////////////


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }
//region fullScreen ExoPlayer

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();

    }


    private void closeFullscreenDialog() {

        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(playerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_fullscreen_expand));

    }


    private void initFullscreenButton() {

        PlayerControlView controlView = playerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(v -> {
            if (!mExoPlayerFullscreen)
                openFullscreenDialog();
            else
                closeFullscreenDialog();
        });

    }


    //endregion

    private void login() {
        new Handler().postDelayed(() -> {

            loginUser();


        }, 5000);
    }

    private void loginUser() {
        String check = sharedPreferences.getString("phone_user", null);
        if (check==null) {

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
            String news = sharedPreferences.getString("phone_user", null);
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

            exoPlayer = new SimpleExoPlayer.Builder(this)

                    .build();
            binding.exoplayer.setPlayer(exoPlayer);
            exoPlayer.prepare();
            exoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, int reason) {

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
                public void onPlaybackStateChanged(int state) {
                    if (state==exoPlayer.STATE_BUFFERING) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else if (state==exoPlayer.STATE_READY) {
                        binding.progressBar.setVisibility(View.GONE);

                    }

                }

                @Override
                public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

                    // play and Pause click
                    if (!playWhenReady) {
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }else {
                        binding.progressBar.setVisibility(View.GONE);
                    }

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
                public void onExperimentalOffloadSchedulingEnabledChanged(boolean offloadSchedulingEnabled) {

                }

                @Override
                public void onExperimentalSleepingForOffloadChanged(boolean sleepingForOffload) {

                }

                @Override
                public void onEvents(Player player, Player.Events events) {

                }
            });
            exoPlayer.setPlayWhenReady(playWhenReady);
            if (!exoPlayer.getPlayWhenReady()) {

                binding.progressBar.setVisibility(View.VISIBLE);
            }
            exoPlayer.seekTo(currentWindow, playBackPosition);


        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }

    private void getFunny_single() {
        binding.titleEnVideoPlayer.setText(getIntent().getStringExtra("title_en"));
        binding.titleFaVideoPlayer.setText(getIntent().getStringExtra("title_fa"));
        binding.titleEnChannelPlayer.setText(getIntent().getStringExtra("name_chann_en"));
        binding.titleFaChannelPlayer.setText(getIntent().getStringExtra("name_chann_fa"));
        binding.titleSubPlayer.setText(getIntent().getStringExtra("followers"));
        binding.titleViewPlayer.setText(getIntent().getStringExtra("view"));
        binding.titleLikePlayer.setText(getIntent().getStringExtra("like"));

        Glide.with(this).load(getIntent().getStringExtra("profile_chan"))
                .into(binding.ivProfileItemAllChan);

        mainViewModel.getFunny_single().observe(this, funnyDataModel -> {



            mediaItem = MediaItem.fromUri(funnyDataModel.getLink_480());
            exoPlayer.addMediaItem(mediaItem);

            // Toast.makeText(this, "" + funnyDataModel.getLink_480(), Toast.LENGTH_SHORT).show();
        });
    }

    private void hideSystemUi() {
        binding.exoplayer.setSystemUiVisibility(
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
        if (playerView == null) {
            playerView = findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();
        }
        if (exoPlayer==null) {
            initExo();
            hideSystemUi();
            //  mainViewModel.requestFunny_single(id_vid_funny);
            // getFunny_single();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initExo();
        //getFunny_single();
        //  mainViewModel.requestFunny_single(id_vid_funny);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }


}