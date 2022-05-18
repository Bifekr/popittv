package ir.popittv.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ir.popittv.myapplication.adapter.TabAdapter;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.ui.FragmentMain1;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction transaction;
    FragmentManager manager;
    FragmentMain1 fragmentMain1;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fragmentMain1 = new FragmentMain1();


        transaction = getSupportFragmentManager().beginTransaction();


    binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_1_border));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_2));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_3));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_4));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_5));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_6));
        binding.tblMainActivity.addTab(binding.tblMainActivity.newTab().setIcon(R.drawable.iv_btn_7));
        binding.tblMainActivity.setTabGravity(TabLayout.GRAVITY_CENTER);

        int numTab = binding.tblMainActivity.getTabCount();

        binding.viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), getLifecycle(), numTab));
        new TabLayoutMediator(binding.tblMainActivity, binding.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {



                switch (position){
                    case 0:
                        tab.setIcon(R.drawable.iv_btn_1);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.iv_btn_2);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.iv_btn_3);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.iv_btn_4);
                        break;
                    case 4:
                        tab.setIcon(R.drawable.iv_btn_5);
                        break;
                    case 5:
                        tab.setIcon(R.drawable.iv_btn_6);
                        break;
                    case 6:
                        tab.setIcon(R.drawable.iv_btn_7);
                        break;

                }




            }
        }).attach();


    }
}