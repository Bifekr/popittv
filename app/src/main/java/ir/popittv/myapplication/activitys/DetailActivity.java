package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityDetailBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class DetailActivity extends AppCompatActivity  {

    private ActivityDetailBinding binding;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        viewModel=new ViewModelProvider(this).get(MainViewModel.class);
    }
}