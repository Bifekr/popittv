package ir.popittv.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

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
        binding=ActivityGameBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        gameViewModel = new  ViewModelProvider(this).get(GameViewModel.class);
        gameAdapter = new GameAdapter(this);

        initRailActivity();
        initRv();
        request();
        getGame();
    }

    private void getGame() {
        gameViewModel.getGame().observe(this,gameDataModels -> {
            if (gameDataModels!=null){
            gameAdapter.setData(gameDataModels);
                Toast.makeText(this,""+gameDataModels.get(0).getBanner_game(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"not success",Toast.LENGTH_LONG).show();
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
        binding.navRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.Funny:
                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(GameActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(GameActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(GameActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(GameActivity.this, GameActivity.class));
                        break;
                }
                return true;
            }
        });
        binding.navRail.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Funny:
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(GameActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(GameActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(GameActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(GameActivity.this, GameActivity.class));
                        break;


                }
            }
        });
    }
}