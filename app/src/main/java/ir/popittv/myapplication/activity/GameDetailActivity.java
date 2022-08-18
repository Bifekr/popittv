package ir.popittv.myapplication.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import ir.popittv.myapplication.adapter.PosterAdapter;
import ir.popittv.myapplication.databinding.ActivityGamedetailBinding;
import ir.popittv.myapplication.viewmodel.GameViewModel;

public class GameDetailActivity extends AppCompatActivity {
    private ActivityGamedetailBinding binding;
    private GameViewModel gameViewModel;
    //--------Variable-----------

    private int id_game;
    private String title_en;
    private String title_fa;
    private String icon;
    private String banner_game;
    private String trailer;
    private String apk;
    private Uri uri;
    private String desc;
    private String star;
    private String download;

    private Timer timer;

    private PosterAdapter posterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamedetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        posterAdapter = new PosterAdapter(this);
        timer=new Timer();

        infoIntentGame();
        instalApk();

        initRv();
        initDataIntoViews();
        request();
        getPoster();

    }

    private void instalApk() {
        binding.btnInstall.setOnClickListener(v -> {

            binding.progressDown.setMax(100);
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
            }
            binding.progressDown.setVisibility(View.VISIBLE);
            DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request=new DownloadManager.Request(uri);
            request.setTitle("در حال دانلود...");
            request.setDescription("لطفا منتظر بمانید....");
            final String uri_down=apk.substring(apk.lastIndexOf("/"));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
            request.setDestinationInExternalPublicDir("/GamePB",uri_down);
            Long id=downloadManager.enqueue(request);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    DownloadManager.Query query=new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor cursor=downloadManager.query(query);
                    if (cursor.moveToFirst()){
                        long downByte=cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        long totalByte = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        final int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                        int percent =(int) ((downByte*100)/totalByte);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.progressDown.setProgress(percent);
                                switch (status){
                                    case DownloadManager.STATUS_SUCCESSFUL:
                                        Toast.makeText(GameDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        timer.purge();
                                        timer.cancel();
                                        break;
                                    case DownloadManager.STATUS_FAILED:
                                        Toast.makeText(GameDetailActivity.this, "faild", Toast.LENGTH_SHORT).show();
                                        break;


                                }
                            }
                        });

                    }

                }
            },0,3000);
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1001 && grantResults.length > 0) {
            if (grantResults[0] >= 0) {
                createFilePath();

            } else {
                Toast.makeText(this, "تنها با انتخاب گزینه -> اجازه دادن <- امکان دانلود فیلم و بازی ها فعال خواهد بود", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getPoster() {
        gameViewModel.getPoster().observe(this, posterDataModels -> {
            if (posterDataModels!=null) {
                posterAdapter.setData(posterDataModels);

            }
        });
    }

    private void initRv() {
        binding.rvGamePoster.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.rvGamePoster.setAdapter(posterAdapter);
    }

    private void request() {
        gameViewModel.request_poster(id_game);
    }

    private void initDataIntoViews() {
        binding.tvLikeItemVidDef.setText(download);
        binding.tvViewItemVidDef.setText(star);
        binding.titleFaVideoItemVideoThumb.setText(title_fa);
        binding.titleEnVideoItemVideoThumb.setText(title_en);
        binding.gameDesc.setText(desc);
        Glide.with(this).load(banner_game)
                .into(binding.ivPosterItemVideo);
        Glide.with(this).load(icon)
                .into(binding.ProfileChannelVideoThumb);
        //Toast.makeText(this, "id_game" + id_game, Toast.LENGTH_SHORT).show();
    }

    private void infoIntentGame() {

        id_game = getIntent().getIntExtra("id_game", 0);
        title_en = getIntent().getStringExtra("title_en");
        title_fa = getIntent().getStringExtra("title_fa");
        icon = getIntent().getStringExtra("icon");
        banner_game = getIntent().getStringExtra("banner_game");
        trailer = getIntent().getStringExtra("trailer");
        apk = getIntent().getStringExtra("apk");
        uri=Uri.parse(apk);
        desc = getIntent().getStringExtra("descrip");
        star = getIntent().getStringExtra("star");
        download = getIntent().getStringExtra("download");
    }

    public void createFilePath() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/GamePB");
      /*  if (!file.exists()){
            file.mkdir();
        }*/
        file.mkdirs();
    }
}