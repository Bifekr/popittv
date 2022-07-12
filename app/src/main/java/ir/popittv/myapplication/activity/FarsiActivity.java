package ir.popittv.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityFarsiBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FarsiActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityFarsiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFarsiBinding.inflate(getLayoutInflater());
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
                        startActivity(new Intent(FarsiActivity.this, MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(FarsiActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(FarsiActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(FarsiActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(FarsiActivity.this, GameActivity.class));
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
                        startActivity(new Intent(FarsiActivity.this,MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(FarsiActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(FarsiActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(FarsiActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(FarsiActivity.this, GameActivity.class));
                        break;


                }
            }
        });
    }
}