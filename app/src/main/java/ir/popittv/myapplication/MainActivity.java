package ir.popittv.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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






    }
}