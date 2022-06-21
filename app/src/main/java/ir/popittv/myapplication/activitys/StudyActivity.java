package ir.popittv.myapplication.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityStudyBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class StudyActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityStudyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=  ActivityStudyBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        viewModel=new ViewModelProvider(this).get(MainViewModel.class);


        initRailActivity();
    }
    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.Funny:
                        startActivity(new Intent(StudyActivity.this, MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(StudyActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(StudyActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(StudyActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(StudyActivity.this, GameActivity.class));
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
                        startActivity(new Intent(StudyActivity.this,MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(StudyActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(StudyActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(StudyActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(StudyActivity.this, GameActivity.class));
                        break;


                }
            }
        });
    }
}