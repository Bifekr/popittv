package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.databinding.ActivityDetailBinding;
import ir.popittv.myapplication.utils.OnClickDetailActivity;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class DetailActivity extends AppCompatActivity  {

    private ActivityDetailBinding binding;
    private MainViewModel viewModel;
    private int id_channel;

    private ChannelDetail_adapter detail_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);


        id_channel= getIntent().getIntExtra("id_channel",0);
        viewModel.requestChannel_detail(id_channel);

        detail_adapter=new ChannelDetail_adapter(this);

        binding.rvAllVideoDetailActivity.setHasFixedSize(true);
        binding.rvAllVideoDetailActivity.setLayoutManager(new GridLayoutManager(this,3, RecyclerView.VERTICAL
                ,false));
        binding.rvAllVideoDetailActivity.setAdapter(detail_adapter);




        getChannel_detail();
    }



    private void getChannel_detail() {
        viewModel.getChannel_detail().observe(this,channelDataModel -> {

            detail_adapter.setFunnyDataModels(channelDataModel.getVideos_channel());

            Glide.with(this).load(channelDataModel.getBanner_chann())
                    .into(binding.ivBannerItemChannelAll);
            Glide.with(this).load(channelDataModel.getProfile_chann())
                    .into(binding.ivProfileItemAllChan);
            binding.tvSubAllChannel.setText(channelDataModel.getFollowers());
            binding.tvAgeAllChannel.setText(channelDataModel.getAge_name());
            binding.titleFaItemAllChannel.setText(channelDataModel.getName_chan_fa().trim());
            binding.titleEnItemAllChannel.setText(channelDataModel.getName_chan_en().trim());


        });
    }


}