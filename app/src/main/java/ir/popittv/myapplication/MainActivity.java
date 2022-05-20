package ir.popittv.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import ir.popittv.myapplication.adapter.TabAdapter;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.models.MovieModel;
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

        initTablayout();
    }

    private void initTablayout() {
        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_1);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view1));

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_2);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view2));

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_3);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view3));

        View view4 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view4.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_4);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view4));

        View view5 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view5.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_5);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view5));

        View view6 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view6.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_6);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view6));

        View view7 = getLayoutInflater().inflate(R.layout.custom_tab, null, false);
        view7.findViewById(R.id.icon).setBackgroundResource(R.drawable.iv_btn_7);
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setCustomView(view7));





        /*binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_2));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_3));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_4));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_5));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_6));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_7));*/


        int numTab = binding.tblMainActivity.getTabCount();

        binding.viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), getLifecycle(), numTab));
        new TabLayoutMediator(binding.tblMainActivity, binding.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


                switch (position) {
                    case 0:
                        tab.setCustomView(view1);
                        break;
                    case 1:

                        tab.setCustomView(view2);
                        break;
                    case 2:

                        tab.setCustomView(view3);
                        break;
                    case 3:

                        tab.setCustomView(view4);
                        break;
                    case 4:

                        tab.setCustomView(view5);
                        break;
                    case 5:

                        tab.setCustomView(view6);
                        break;
                    case 6:
                        tab.setCustomView(view7);
                        break;

                }


            }
        }).attach();
    }
}