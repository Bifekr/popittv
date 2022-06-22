package ir.popittv.myapplication.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.InfinitFrg1_PagerAdapter;
import ir.popittv.myapplication.adapter.Recommend_Adapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnClickFrg1 {

    MainViewModel mainViewModel;
    ActivityMainBinding binding;


    //global adapter
    private RvChannel_Frg1 rvChannel_frg1;
    private ChannelDetail_adapter detail_adapter;
    private InfinitFrg1_PagerAdapter infinitAdapter;
    Recommend_Adapter recommend_adapter;
    Recommend_Adapter recommend_adapter2;


    //global Variable
    int id_channel;
    String tv1;


    //Utils Class
    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //ViewModel Provider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recommend_adapter=new Recommend_Adapter(this);
        recommend_adapter2=new Recommend_Adapter(this);
        detail_adapter=new ChannelDetail_adapter(this);
        rvChannel_frg1 = new RvChannel_Frg1(this,this);
        infinitAdapter = new InfinitFrg1_PagerAdapter(this);
        initRailActivity();

        //retrieve data into modelClass
        request();

        /////
        initRv_Vp_adapter();

        //update AND get Data from DataModel into LiveData
        getChannel();
        getChannel_detail();
        getFunny_view();
        getFunny_liky();


    }

    private void getFunny_liky() {
        mainViewModel.getFunny_liky().observe(this,funnyDataModels -> {
            recommend_adapter2.setFunnyDataModels(funnyDataModels);

        });
    }

    private void request() {
        //request from Api to get DataModel
        mainViewModel.requestChannel();
        //detail Channel Selected
        mainViewModel.requestChannel_detail(3);
        // mainViewModel.requestChannel_detail(1);
        mainViewModel.requestFunny_view();
        mainViewModel.requestFunny_liky();

    }

    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Funny:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(MainActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(MainActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(MainActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(MainActivity.this, GameActivity.class));
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
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case R.id.Reality:
                        startActivity(new Intent(MainActivity.this, RealityActivity.class));
                        break;
                    case R.id.Learning:
                        startActivity(new Intent(MainActivity.this, StudyActivity.class));
                        break;
                    case R.id.Farsi:
                        startActivity(new Intent(MainActivity.this, FarsiActivity.class));
                        break;
                    case R.id.Games:
                        startActivity(new Intent(MainActivity.this, GameActivity.class));
                        break;


                }
            }
        });
    }
    private void initRv_Vp_adapter() {

        //init channel list Adapter
        binding.rvChannelListFrg1.setHasFixedSize(true);
        binding.rvChannelListFrg1.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.rvChannelListFrg1.setAdapter(rvChannel_frg1);

        //Show Detail Channel Recycler
        binding.rvDetailFrg1.setHasFixedSize(true);
        binding.rvDetailFrg1.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        binding.rvDetailFrg1.setAdapter(detail_adapter);

        //RecyclerView Selected 1
        binding.rvSelect1Frg1.setHasFixedSize(true);
        binding.rvSelect1Frg1.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        binding.rvSelect1Frg1.setAdapter(recommend_adapter);

        //recyclerView Selected2
        binding.rvSelect2Frg1.setHasFixedSize(true);
        binding.rvSelect2Frg1.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,true));
        binding.rvSelect2Frg1.setAdapter(recommend_adapter2);

        //horizontal viewpager infinite

        binding.infinitCycleFrg1.setAdapter(infinitAdapter);

        //Recommended Vide Rv
        binding.rvRecommendFrg1.setHasFixedSize(true);
        binding.rvRecommendFrg1.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false));
        binding.rvRecommendFrg1.setAdapter(recommend_adapter);



     /*   cardPagerAdapter2 = new CardPagerAdapter2(getActivity());
        shadowTransformer = new ShadowTransformer(binding.vpChannelListFrg1, cardPagerAdapter2);
        binding.vpChannelListFrg1.setAdapter(cardPagerAdapter2);
        binding.vpChannelListFrg1.setPageTransformer(false, shadowTransformer);
        binding.vpChannelListFrg1.setOffscreenPageLimit(6);*/


    }
    private void getChannel() {
        mainViewModel.getChannel().observe(this,channelDataModels -> {
            if (channelDataModels!=null) {
                rvChannel_frg1.setData(channelDataModels);

                for (ChannelDataModel channel : channelDataModels
                ) {
//                    cardPagerAdapter2.addCardItem(channel);

                }
            }
        });
    }
    private void getChannel_detail(){
        mainViewModel.getChannel_detail().observe(this, channelDataModel -> {

            List<FunnyDataModel> models = new ArrayList<>((channelDataModel).getVideos_channel());
            detail_adapter.setFunnyDataModels(models);

            Glide.with(this).load(channelDataModel.getProfile_chann())
                    .into(binding.showProfileChanFrg1);
            binding.tvSubDetailChanFrg1.setText(channelDataModel.getFollowers());
            binding.tvAgeDetailChanFrg1.setText(channelDataModel.getAge());
            binding.titleFaDetailChanFrg1.setText(channelDataModel.getName_chan_fa().trim());
            binding.titleEnDetailChanFrg1.setText(channelDataModel.getName_chan_en().trim());


        });
    }

    private void getFunny_view() {
        mainViewModel.getFunny_view().observe(this, funnyDataModels -> {
            if (funnyDataModels!=null) {
                infinitAdapter.setData(funnyDataModels);
                recommend_adapter.setFunnyDataModels(funnyDataModels);
            } else {
                Toast.makeText(this, "اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void OnclickDetail(int pos) {
        id_channel=pos;
        mainViewModel.requestChannel_detail(id_channel);

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
