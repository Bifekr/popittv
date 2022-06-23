package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityAllChannelBinding;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class AllChannelActivity extends AppCompatActivity {

    private ActivityAllChannelBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllChannelBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);

        initRecyclerView();




    }

    private void initRecyclerView() {
        binding.rvAllChannelActivityAllChannel.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL,false));

    }
}