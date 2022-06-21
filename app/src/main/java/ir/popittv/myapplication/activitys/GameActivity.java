package ir.popittv.myapplication.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityGameBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class GameActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGameBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        initRailActivity();
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