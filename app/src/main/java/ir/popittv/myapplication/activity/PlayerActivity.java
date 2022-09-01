package ir.popittv.myapplication.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ViewDialog;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.FunnyAdapter;
import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class PlayerActivity extends AppCompatActivity implements OnClickFunny {


    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    ////////////////////////////////////
    private final boolean isLocked = false;
    SharedPreferences.Editor editor;
    int kind;
    int id_channel;
    ImageView lockScreen;
    ImageButton ff, ff2, ff3, ff4;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playBackPosition = 0;
    MediaItem mediaItem;
    SimpleExoPlayer simpleExoPlayer;
    TextInputLayout et_phone;
    TextInputLayout et_code;
    String subscribe_b;
    BottomSheetDialog bottomSheetDialog2;
    BottomSheetDialog bottomSheetDialog;
    private ActivityPlayerBinding binding;
    private MainViewModel mainViewModel;
    //////////////////////////// exoPlayer Variable/////////////////////////////////////////////////////
    private SharedPreferences sharedPreferences;
    private String name_user;
    private String phone_user;
    private String code_user;
    private Long lastDate;
    ;
    private int id_user;
    private int id_vid_funny;
    private View bottomView;
    private View bottomView2;
    private PlayerView playerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private boolean b_kindlink;
    private FunnyAdapter funnyAdapter1;
    private FunnyAdapter detail_adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        phone_user = sharedPreferences.getString("phone_user", null);
        name_user = sharedPreferences.getString("name_user", null);
        id_user = sharedPreferences.getInt("id_user", 0);
        b_kindlink = sharedPreferences.getBoolean("switchNet", true);
        lastDate = sharedPreferences.getLong("lastDate", 0);
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        funnyAdapter1 = new FunnyAdapter(this, this);
        detail_adapter2 = new FunnyAdapter(this, this);
        lockScreen = findViewById(R.id.exo_lock);
        initRv();
        Log.i("TAG", "onCreate: " + b_kindlink);
        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        kind = getIntent().getIntExtra("kind", 0);
        id_channel = getIntent().getIntExtra("id_channel", 0);
        //  Toast.makeText(PlayerActivity.this, "i_channel . ." + id_channel + "id_vid . ." + id_vid_funny + "kind . " + kind, Toast.LENGTH_LONG).show();
        mainViewModel.requestFunny_single(id_vid_funny, kind);


        ff = findViewById(R.id.exo_ffwd);
        ff2 = findViewById(R.id.exo_pause);
        ff3 = findViewById(R.id.exo_rew);
        ff4 = findViewById(R.id.exo_play);

        initExo();
        //getChannel_detail();
        getFunny_single();

        //iconLockScreen();


      /*  binding.parentSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribe_b.equals(0)) {
                    binding.parentSub.setImageResource(R.drawable.red_youtube);
                    subscribe_b.equals(1);
                } else {
                    binding.parentSub.setImageResource(R.drawable.red_white_sub);
                    subscribe_b.equals(0);
                }
            }
        });*/

     /*   btn_fullScreen = binding.exoPlayer.findViewById(R.id.bt_fullscreen);
        btn_fullScreen.setOnClickListener(v -> {

        });
*/
        if (phone_user==null || lastDate==0 || lastDate==null) {
            login();

        }

   /*     binding.rvVidChannelPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvVidChannelPlayer.setAdapter(detail_adapter2);
        mainViewModel.requestChannel_detail(id_channel, kind);
        Toast.makeText(this, "id_chabneel" + id_channel, Toast.LENGTH_SHORT).show();
        mainViewModel.getChannel_detail().observe(this, new Observer<ChannelDataModel>() {
            @Override
            public void onChanged(ChannelDataModel channelDataModel) {
                //  detail_adapter2.setFunnyDataModels(channelDataModel.getVideos_channel());

                if (channelDataModel!=null) {
                    List<FunnyDataModel> funnyDataModels = new ArrayList<>((channelDataModel).getVideos_channel());

                    detail_adapter2.setData(funnyDataModels);
                }
            }
        });*/
///////////////////////
    /*    dataSourceFactory =
                new DefaultDataSourceFactory(
                        this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState!=null) {
            currentWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            playBackPosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }*/
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState!=null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

//----------------------onCreate---------------------------------------------
    }

/*    private void iconLockScreen() {
        lockScreen.setOnClickListener(v -> {
            if (!isLocked) {
                lockScreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock));
            } else {
                lockScreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock_open));
            }
            isLocked = !isLocked;
            lockActivity(isLocked);
        });
    }

    private void lockActivity(boolean isLocked) {

        if (isLocked) {
            ff.setVisibility(View.INVISIBLE);
            ff2.setVisibility(View.INVISIBLE);
            ff3.setVisibility(View.INVISIBLE);
            ff4.setVisibility(View.INVISIBLE);

        } else {
            ff.setVisibility(View.VISIBLE);
            ff2.setVisibility(View.VISIBLE);
            ff3.setVisibility(View.VISIBLE);
            ff4.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isLocked) {

            if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
                lockScreen.performClick();
            } else {
                super.onBackPressed();
            }
        }
    }*/

    private void initRv() {


        binding.rvMenuTagPlayer.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL
                , false));
       // binding.rvMenuTagPlayer.setAdapter(detail_adapter2);
        binding.rvVidChannelPlayer.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rvVidChannelPlayer.setAdapter(funnyAdapter1);
    }



     private void getChannel_detail() {
        mainViewModel.getChannel_detail().observe(this, channelDataModel -> {

            if (channelDataModel!=null) {
                funnyAdapter1.setData(channelDataModel.getVideos_channel());

                Glide.with(this).load(channelDataModel.getBanner_chann())
                        .into(binding.ivBannerItemChannelAll);
                id_vid_funny = channelDataModel.getId_funny();
                id_channel = channelDataModel.getId_channel();
                kind = channelDataModel.getKind();
          Glide.with(this).load(channelDataModel.getProfile_chann())
                    .into(binding.ivProfileItemAllChan);
         binding.titleSubPlayer.setText(channelDataModel.getFollowers());
          //  binding.titleAgePlayer.setText(channelDataModel.getAge_name());
            binding.titleFaChannelPlayer.setText(channelDataModel.getName_chan_fa().trim());
            binding.titleEnChannelPlayer.setText(channelDataModel.getName_chan_en().trim());

            }
        });
    }

    private void getFunny_single() {
        mainViewModel.getFunny_single().observe(this, funnyDataModel -> {
            if (funnyDataModel!=null) {
                binding.titleEnVideoPlayer.setText(funnyDataModel.getTitle_en());
                binding.titleFaVideoPlayer.setText(funnyDataModel.getTitle_fa());
                binding.titleEnChannelPlayer.setText(funnyDataModel.getName_chan_en());
                binding.titleFaChannelPlayer.setText(funnyDataModel.getName_chan_fa());
                binding.titleSubPlayer.setText(funnyDataModel.getFollowers());
                int like = Integer.parseInt(funnyDataModel.getLiky());
                int view = Integer.parseInt(funnyDataModel.getView());

                binding.tvViewItemVidDef.setText(prettyCount(view));
                binding.tvLikeItemVidDef.setText(prettyCount(like));
                int id_channel_2 = funnyDataModel.getId_channel();
                Glide.with(this).load(funnyDataModel.getPoster())
                        .into(binding.posterPlayer);

                Glide.with(this).load(funnyDataModel.getProfile_chann())
                        .into(binding.ivProfileItemAllChan);


                if (b_kindlink) {
                    String video_link = funnyDataModel.getLink_480();
                    Uri uri = Uri.parse(video_link);
                    mediaItem = MediaItem.fromUri(uri);

                } else {
                    String video_link = funnyDataModel.getLink_720();
                    Uri uri = Uri.parse(video_link);
                    mediaItem = MediaItem.fromUri(uri);


                }

                simpleExoPlayer.setMediaItem(mediaItem);
                simpleExoPlayer.play();
            }
        });
    }

    public String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

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
        FrameLayout mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(v -> {
            if (!mExoPlayerFullscreen)
                openFullscreenDialog();
            else
                closeFullscreenDialog();
        });

    }

    private void initExo() {
        try {

            simpleExoPlayer = new SimpleExoPlayer.Builder(this)


                    .build();
            boolean haveResumePosition = mResumeWindow!=C.INDEX_UNSET;
            binding.exoplayer.setPlayer(simpleExoPlayer);
            binding.exoplayer.setKeepScreenOn(true);
            if (haveResumePosition) {
                Log.i("DEBUG", " haveResumePosition ");
                simpleExoPlayer.seekTo(mResumeWindow, mResumePosition);
            }
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playBackPosition);
            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state==simpleExoPlayer.STATE_BUFFERING) {

                        binding.progressBar.setVisibility(View.VISIBLE);

                    } else if (state==simpleExoPlayer.STATE_READY) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
                    // play and Pause click

                }


            });

            if (!simpleExoPlayer.getPlayWhenReady()) {

                binding.progressBar.setVisibility(View.VISIBLE);
            }


            simpleExoPlayer.prepare();

        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }

    //endregion

    private void login() {
        if (phone_user==null || lastDate==0 || lastDate==null) {
            new Handler().postDelayed(this::checkExpireUser, 115000);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


    private void checkExpireUser() {
        if (lastDate==0) {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(PlayerActivity.this, "!! کد دسترسی یافت نشد !! ");
        }
  /*          AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(PlayerActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle("عدم دسترسی به محتوا")
                    .setMessage("کد دسترسی معتبری یافت نشد")
                    .setIcon(R.drawable.ic_parents_monny)
                    .setPositiveButton("دریافت کد دسترسی", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {  // مددیریت پرداخت پول و دریافت اشتراک برای کاربر
                            Toast.makeText(PlayerActivity.this, "YES", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PlayerActivity.this,UserActivity.class));

                        }
                    }).setNegativeButton("بعدا یادوری کن", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {  // مدیریت عدم خواستن دریافت اشتراک
                    Toast.makeText(PlayerActivity.this, "NO", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PlayerActivity.this,MainActivity.class));
                }
            });
            builder.show();

        }*/
    }


    private void releasePlayer() {
        if (simpleExoPlayer!=null) {
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            playBackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();

            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
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
        if (playerView==null) {
            playerView = findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();
        }
        initExo();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) playerView.getParent()).removeView(playerView);
            mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView!=null && simpleExoPlayer!=null) {
            mResumeWindow = simpleExoPlayer.getCurrentWindowIndex();
            mResumePosition = Math.max(0, simpleExoPlayer.getContentPosition());

            simpleExoPlayer.release();
        }

        if (mFullScreenDialog!=null)
            mFullScreenDialog.dismiss();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();

    }


    @Override
    public void onClickSave(int id_vid) {

    }

    @Override
    public void onClickSee(int id_vid) {

    }

    @Override
    public void onClickLike(int id_vid) {

    }

    @Override
    public void onClickLater(int id_vid) {

    }

    @Override
    public void onClickSub(int id_channel) {

    }

    @Override
    public void onClickPlayer(int id_vid_funny, int id_channel, int kind) {

        Toast.makeText(PlayerActivity.this, "" + id_vid_funny, Toast.LENGTH_SHORT).show();

        mainViewModel.requestFunny_single(id_vid_funny, kind);
        mainViewModel.requestChannel_detail(id_channel, kind);
        mainViewModel.requestFunny_subMenu(0, kind);
        getChannel_detail();

        getFunny_single();
    }
}