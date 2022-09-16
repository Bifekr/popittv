package ir.popittv.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Objects;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.GameAdapter;
import ir.popittv.myapplication.databinding.ActivityGameBinding;
import ir.popittv.myapplication.viewmodel.GameViewModel;


public class GameActivity extends AppCompatActivity {

    private final int KIND = 5;
    private GameViewModel gameViewModel;
    private ActivityGameBinding binding;

    //RecyclerView
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        gameAdapter = new GameAdapter(this);

        initRailActivity();
        initRv();
        request();
        getGame();
    }

    private void getGame() {
        gameViewModel.getGame().observe(this, gameDataModels -> {
            if (gameDataModels!=null) {
                gameAdapter.setData(gameDataModels);


            } else {

            }
        });

    }

    private void request() {
        gameViewModel.request_game();
    }

    private void initRv() {
        binding.rvGame.setLayoutManager(new GridLayoutManager
                (this, 2, GridLayoutManager.VERTICAL, false));
        binding.rvGame.setAdapter(gameAdapter);
    }

    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(item -> {
            int item2 = item.getItemId();
            if (item2==R.id.funny) {
                startActivity(new Intent(GameActivity.this, MainActivity.class));
            } else if (item2==R.id.learning) {
                startActivity(new Intent(GameActivity.this, StudyActivity.class));
            } else if (item2==R.id.reality) {
                startActivity(new Intent(GameActivity.this, RealityActivity.class));
            } else if (item2==R.id.farsi) {
                startActivity(new Intent(GameActivity.this, FarsiActivity.class));
            }
            return true;
        });

        Objects.requireNonNull(binding.navRail.getHeaderView()).findViewById(R.id.fab_add).setOnClickListener(v -> {
            startActivity(new Intent(GameActivity.this, UserActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        request();
    }
}