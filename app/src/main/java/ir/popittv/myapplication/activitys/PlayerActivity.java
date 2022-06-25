package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityPlayerBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private MainViewModel viewModel;

    private int id_vid_funny;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPlayerBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);



    }
}