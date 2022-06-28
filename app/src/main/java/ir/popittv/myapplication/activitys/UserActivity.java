package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
       // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(binding.getRoot());

        initRailActivity();
    }

    @SuppressLint("NonConstantResourceId")
    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Funny:
                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                    break;
                case R.id.Reality:
                    startActivity(new Intent(UserActivity.this, RealityActivity.class));
                    break;
                case R.id.Learning:
                    startActivity(new Intent(UserActivity.this, StudyActivity.class));
                    break;
                case R.id.Farsi:
                    startActivity(new Intent(UserActivity.this, FarsiActivity.class));
                    break;
                case R.id.Games:
                    startActivity(new Intent(UserActivity.this, GameActivity.class));
                    break;
            }
            return true;
        });

        binding.navRail.getHeaderView().findViewById(R.id.fab_add).setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, MainActivity.class));
        });

             /*  binding.navRail.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Funny:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.Reality:
                    startActivity(new Intent(MainActivity.this, RealityActivity.class));
                    break;
                case R.id.Learning:
                    startActivity(new Intent(MainActivity.this, StudyActivity.class));
                    break;
                case R.id.Farsi:
                    startActivity(new Intent(MainActivity.this, FarsiActivity.class));
                    break;
                case R.id.Games:
                    startActivity(new Intent(MainActivity.this, GameActivity.class));
                    break;


            }
        });*/
    }
}