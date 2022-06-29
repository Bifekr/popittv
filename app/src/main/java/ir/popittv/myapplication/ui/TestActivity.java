package ir.popittv.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import ir.popittv.myapplication.R;

public class TestActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment fragment;
    boolean back = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




       /*     if (fragment==null) {
           fragmentManager = getSupportFragmentManager();
           transaction = fragmentManager.beginTransaction();
           transaction.replace(binding.containerMain.getId(), new FragmentMain1()).commit();
       }
        initRail();*/


    }
     /*  private void initRail() {


        binding.navRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.alarms2:
                        fragment = new FragmentMain1();
                        break;
                    case R.id.schedule:
                        fragment = new FragmentMain2();
                        break;
                    case R.id.timer:
                        fragment = new FragmentMain3();
                        break;
                    case R.id.stopwatch:
                        fragment = new FragmentMain4();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.containerMain.getId(), fragment)
                        .addToBackStack(null).commit();
                return true;
            }
        });

        binding.navRail.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.alarms2:
                        Toast.makeText(MainActivity.this, "Alarms Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.schedule:
                        Toast.makeText(MainActivity.this, "Alarms xcClicked", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.timer:
                        Toast.makeText(MainActivity.this, "Alarms xcxcClicked", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

    }*/
}