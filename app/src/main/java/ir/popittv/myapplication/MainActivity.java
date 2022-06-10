package ir.popittv.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ir.popittv.myapplication.adapter.TabAdapter;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.ui.FragmentMain1;
import ir.popittv.myapplication.ui.FragmentMain2;
import ir.popittv.myapplication.ui.FunnyActivity;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //ViewModel Provider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initRail();

    }

    private  void  initRail() {

        binding.navRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Navigation.findNavController(MainActivity.this,item.getItemId())
                        .navigate(R.id.action_fragmentMain1_to_fragmentMain2);
                switch (item.getItemId()){
                    case R.id.alarms2:
                        Toast.makeText(MainActivity.this, "Alarms Clicked", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.schedule:
                        Toast.makeText(MainActivity.this, "Alarms xcClicked", Toast.LENGTH_SHORT).show();
                      /*  getSupportFragmentManager().beginTransaction()
                                .replace(binding.containerMain.getId(),new FragmentMain1()).commit();*/

                        return true;

                    case R.id.timer:
                        Toast.makeText(MainActivity.this, "Alarms xcxcClicked", Toast.LENGTH_SHORT).show();
               /*         getSupportFragmentManager().beginTransaction()
                                .replace(binding.containerMain.getId(),new FragmentMain2()).commit();*/
                        return true;
                }
                return false;
            }
        });

        binding.navRail.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.alarms2:
                        Toast.makeText(MainActivity.this, "Alarms Clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, FunnyActivity.class));
                   break;

                    case R.id.schedule:
                        Toast.makeText(MainActivity.this, "Alarms xcClicked", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(binding.containerMain.getId(),new FragmentMain1()).commit();

                       break;

                    case R.id.timer:
                        Toast.makeText(MainActivity.this, "Alarms xcxcClicked", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(binding.containerMain.getId(),new FragmentMain2()).commit();
                       break;
                }

            }
        });

    }




}