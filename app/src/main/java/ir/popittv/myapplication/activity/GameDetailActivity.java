package ir.popittv.myapplication.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ir.popittv.myapplication.databinding.ActivityGamedetailBinding;

public class GameDetailActivity extends AppCompatActivity {

    //--------Variable-----------

    private  int id_game ;
    private  String title_en ;
    private  String title_fa ;
    private String icon ;
    private  String banner_game ;
    private  String trailer ;
    private  String apk ;
    private  String desc;
    private  String star ;
    private  String download ;
    private ActivityGamedetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGamedetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        infoIntentGame();
        initDataIntoViews();
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
        Toast.makeText(this, "id_game"+id_game, Toast.LENGTH_SHORT).show();
    }

    private void infoIntentGame() {

        id_game = getIntent().getIntExtra("id_game",0);
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