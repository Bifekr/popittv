package ir.popittv.myapplication.activity;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.graphics.drawable.ColorDrawable;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ViewDialog;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.FunnyAdapter;
import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.utils.AppExecuter;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Handler handler;
    private ActivityPlayerBinding binding;
    private MainViewModel mainViewModel;
    //////////////////////////// exoPlayer Variable/////////////////////////////////////////////////////
    private SharedPreferences sharedPreferences;
    private String name_user;
    private String phone_user;
    private Long lastDate;
    private int id_user;
    private int id_vid_funny;
    private PlayerView playerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private  boolean boo_mark=false;
    private  boolean boo_like=false;
    private  boolean boo_whatchLater=false;
    private boolean b_kindlink;
    AlertDialog.Builder builder;
    private FunnyAdapter single_adapter;
    private ChannelDetail_adapter viChennrlAdapter1;
    private FunnyAdapter best_Adapter;
    private FunnyAdapter new_bestAdapter;
    private ChannelDetail_adapter all_adapter;
    Runnable runnable;


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
        handler = new Handler();
        single_adapter = new FunnyAdapter(this, this);
        viChennrlAdapter1 = new ChannelDetail_adapter(this, this);
        best_Adapter =new FunnyAdapter(this,this);
        new_bestAdapter = new FunnyAdapter(this,this);
        all_adapter = new ChannelDetail_adapter(this,this);

        lockScreen = findViewById(R.id.exo_lock);
        initRv();
        Log.i("TAG", "onCreate: " + b_kindlink);
        id_vid_funny = getIntent().getIntExtra("id_vid_funny", 0);
        kind = getIntent().getIntExtra("kind", 0);
        id_channel = getIntent().getIntExtra("id_channel", 0);


        mainViewModel.requestFunny_single(id_vid_funny, kind);


        ff = findViewById(R.id.exo_ffwd);
        ff2 = findViewById(R.id.exo_pause);
        ff3 = findViewById(R.id.exo_rew);
        ff4 = findViewById(R.id.exo_play);

        initExo();
        mainViewModel.requestFunny_subMenu(0, kind);

        getFunny_single();
        getVid_channel();
        getBest();
       getNew_best();
        getAllVid();

     /*   if (phone_user==null || lastDate==0 || lastDate==null) {
            login();

        }*/
        binding.parentSub.setOnClickListener(v -> {


            if (id_user>0 ) {
                Service.getApiClient().insertUserSub(id_user,id_channel).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if(view.getTag()==null){
                            //first time set color to green
                            view.setTag("green");
                            view.setBackgroundResource(R.color.green);
                            binding.parentSub.setBackgroundResource(R.drawable.shape_youtube_desable);
                            Toast.makeText(PlayerActivity.this, "اضافه شد", Toast.LENGTH_SHORT).show();
                        }else if(view.getTag().toString().equals("green")){
                            //green color already set change to grey
                            view.setBackgroundResource(R.color.gray);
                            binding.parentSub.setBackgroundResource(R.drawable.shape_active_youtube);
                            Toast.makeText(PlayerActivity.this, "حذف شد", Toast.LENGTH_SHORT).show();
                            view.setTag(null);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        binding.parentSub.setBackgroundResource(R.drawable.shape_active_youtube);
                    }
                });


            }else {
                Toast.makeText(PlayerActivity.this, "برای فالو کرن ، ابتدا وارد شوید", Toast.LENGTH_SHORT).show();
            }
        });
        binding.like.setOnClickListener(v -> {
            if (!boo_like) {
               binding.like.setBackgroundResource(R.drawable.shape_tag2);
                Service.getApiClient().insertUserLike(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                boo_mark=true;

            }else {

               binding.like.setBackgroundResource(R.drawable.shape_tag4);
                Service.getApiClient().insertUserLike(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                boo_mark=false;
            }
        });
        binding.icBookmark.setOnClickListener(v->{
            if (!boo_mark) {
            binding.icBookmark.setBackgroundResource(R.drawable.shape_tag2);
            Service.getApiClient().insertUserLike(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

            boo_mark=true;

        }else {

            binding.icBookmark.setBackgroundResource(R.drawable.shape_tag4);
            Service.getApiClient().insertUserSave(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            boo_mark=false;
        }

        });
        binding.icWatchLater.setOnClickListener(v->{
            if (!boo_mark) {
                binding.icWatchLater.setBackgroundResource(R.drawable.shape_tag2);
                Service.getApiClient().insertUserLater(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                boo_mark=true;

            }else {

                binding.icWatchLater.setBackgroundResource(R.drawable.shape_tag4);
                Service.getApiClient().insertUserLater(id_user, id_vid_funny, kind).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                boo_mark=false;
            }
        });

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState!=null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

//----------------------onCreate---------------------------------------------
    }


    private void initRv() {



        /*binding.rvVidChannelPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvVidChannelPlayer.setAdapter(single_adapter);*/

        binding.rvVidChannelPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvVidChannelPlayer.setAdapter(viChennrlAdapter1);

        binding.rvBestPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false));
        binding.rvBestPlayer.setAdapter(best_Adapter);

        binding.rvNewBestPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false));
        binding.rvNewBestPlayer.setAdapter(new_bestAdapter);
    /*    binding.rvBestPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvBestPlayer.setAdapter(best_Adapter);

        binding.rvNewBestPlayer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvNewBestPlayer.setAdapter(new_bestAdapter);*/

        binding.rvAllVidPlayer.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL
                , false));
        binding.rvAllVidPlayer.setAdapter(all_adapter);

    }

    private void getVid_channel() {
        Service.getApiClient().getVid_Channel(id_channel, kind).enqueue(new Callback<List<FunnyDataModel>>() {
            @Override
            public void onResponse(Call<List<FunnyDataModel>> call, Response<List<FunnyDataModel>> response) {
                if (response.isSuccessful())
                    viChennrlAdapter1.setFunnyDataModels(response.body());
            }

            @Override
            public void onFailure(Call<List<FunnyDataModel>> call, Throwable t) {

            }
        });
    }

    private void getBest() {
        Service.getApiClient().getBest(0).enqueue(new Callback<List<FunnyDataModel>>() {
            @Override
            public void onResponse(Call<List<FunnyDataModel>> call, Response<List<FunnyDataModel>> response) {
                if (response.isSuccessful()) {
                    best_Adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FunnyDataModel>> call, Throwable t) {

            }
        });
    }

    private void getNew_best() {
        Service.getApiClient().getNew_Best(0).enqueue(new Callback<List<FunnyDataModel>>() {
            @Override
            public void onResponse(Call<List<FunnyDataModel>> call, Response<List<FunnyDataModel>> response) {
                if (response.isSuccessful()) {
                    new_bestAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FunnyDataModel>> call, Throwable t) {

            }
        });

    }

    private void getAllVid() {

        mainViewModel.getFunny_subMenu().observe(PlayerActivity.this, funnyDataModels -> {
            if (funnyDataModels!=null) {

            /*    Collections.sort(funnyDataModels, new Comparator<FunnyDataModel>() { //sorted by last id

                    @Override
                    public int compare(FunnyDataModel o1, FunnyDataModel o2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return o1.getId_funny() > o2.getId_funny() ? -1 : (o1.getId_funny() < o2.getId_funny() ) ? 1 : 0;
                    }
                });*/

                all_adapter.setFunnyDataModels(funnyDataModels);
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
                id_channel = funnyDataModel.getId_channel();
                id_vid_funny = funnyDataModel.getId_funny();
                kind = funnyDataModel.getKind();
               /* Glide.with(this).load(funnyDataModel.getPoster())
                        .into(binding.posterPlayer);*/

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

    private void login() {

       runnable=new Runnable() {
            @Override
            public void run() {
                checkExpireUser();
                simpleExoPlayer.stop();
            }
        };
        handler.postDelayed(runnable, 50000);

    }

    private void checkExpireUser() {
        if (lastDate==0) {
            final Dialog dialog = new Dialog(PlayerActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialogbox_otp);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView text = (TextView) dialog.findViewById(R.id.txt_file_path);
            text.setText(R.string.exite);

            Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    // activity.startActivity(new Intent(activity, MainActivity.class));
                    onBackPressed();
                   // finish();

                    dialog.dismiss();
                }
            });

            Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
            dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PlayerActivity.this, UserActivity.class));
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    //region  ExoPlayer
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

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
                    Toast.makeText(PlayerActivity.this, "onPlay", Toast.LENGTH_SHORT).show();
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
    //endregion


    @Override
    public void onBackPressed() {
        super.onBackPressed();

      handler.removeCallbacks(runnable);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (playerView==null) {
            playerView = findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();


        }

        Toast.makeText(PlayerActivity.this, "onResume", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PlayerActivity.this, "onPause", Toast.LENGTH_SHORT).show();
        if (mFullScreenDialog!=null)
            mFullScreenDialog.dismiss();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (playerView==null) {
            playerView = findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();

        } else {
            initExo();

            simpleExoPlayer.setMediaItem(mediaItem);
            simpleExoPlayer.seekTo(mResumePosition);
            simpleExoPlayer.getPlayWhenReady();

            Toast.makeText(PlayerActivity.this, "onRestart", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClickSave(int id_vid) {
        Service.getApiClient().insertUserSave(id_user, id_vid, kind).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickSee(int id_vid) {
        Service.getApiClient().insertUserSee(id_user, id_vid, kind).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickLike(int id_vid) {
        Service.getApiClient().insertUserLike(id_user, id_vid, kind).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickLater(int id_vid) {
        Service.getApiClient().insertUserLater(id_user, id_vid, kind).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickSub(int id_channel) {

    }

    @Override
    public void onClickPlayer(int id_vid_funny, int id_channel, int kind2) {

        Toast.makeText(PlayerActivity.this, "" + id_vid_funny, Toast.LENGTH_SHORT).show();
        kind=kind2;

        mainViewModel.requestFunny_single(id_vid_funny, kind2);
        binding.containerMain.fullScroll(ScrollView.FOCUS_UP);
        getFunny_single();
        getVid_channel();
        getBest();
        getNew_best();
    }
}