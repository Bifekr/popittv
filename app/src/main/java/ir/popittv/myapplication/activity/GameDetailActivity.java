package ir.popittv.myapplication.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import ir.popittv.myapplication.adapter.PosterAdapter;
import ir.popittv.myapplication.databinding.ActivityGamedetailBinding;
import ir.popittv.myapplication.viewmodel.GameViewModel;

public class GameDetailActivity extends AppCompatActivity {

    //--------Variable-----------

    private int id_game;
    private String title_en;
    private String title_fa;
    private String icon;
    private String banner_game;
    private String trailer;
    private String apk;
    private String desc;
    private String star;
    private String download;
    private ActivityGamedetailBinding binding;
    private GameViewModel gameViewModel;

    private PosterAdapter posterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamedetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        posterAdapter = new PosterAdapter(this);

        initRv();
        infoIntentGame();
        initDataIntoViews();
        request();
        getPoster();
    }

    private void getPoster() {
        gameViewModel.getPoster().observe(this, posterDataModels -> {
            if (posterDataModels!=null) {
                posterAdapter.setData(posterDataModels);
                Toast.makeText(this, "" + posterDataModels.get(0).getPoster(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "id_game" + id_game, Toast.LENGTH_SHORT).show();
    }

    private void infoIntentGame() {

        id_game = getIntent().getIntExtra("id_game", 0);
        title_en = getIntent().getStringExtra("title_en");
        title_fa = getIntent().getStringExtra("title_fa");
        icon = getIntent().getStringExtra("icon");
        banner_game = getIntent().getStringExtra("banner_game");
        trailer = getIntent().getStringExtra("trailer");
        apk = getIntent().getStringExtra("apk");
        desc = getIntent().getStringExtra("descrip");
        star = getIntent().getStringExtra("star");
        download = getIntent().getStringExtra("download");
    }
}