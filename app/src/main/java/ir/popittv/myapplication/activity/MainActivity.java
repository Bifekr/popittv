package ir.popittv.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;


import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.FunnyAdapter;
import ir.popittv.myapplication.adapter.InfinitFrg1_PagerAdapter;
import ir.popittv.myapplication.adapter.Recommend_Adapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.adapter.TagAdapter;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.HashTagDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnClickFrg1 , OnClickFunny {


    //global Variable
    private int id_channel;
    private int id_user;
    private boolean b_switchLink;

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor switchEditor;

    //-------------


    private ChannelDetail_adapter funnyAdapter;
    private FunnyAdapter funnyAdapter_liky;
    private FunnyAdapter funnyAdapter_view;

    private final int FUNNY_KIND=1;

    //global adapter
    private RvChannel_Frg1 rvChannel_frg1;
    private FunnyAdapter detail_adapter;
    private InfinitFrg1_PagerAdapter infinitAdapter;
    private ChannelDetail_adapter recommend_adapter;


    private TagAdapter tagAdapter;

    //Utils Class
    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get preferences data
        sharedPreferences=getSharedPreferences("user_info",MODE_PRIVATE);
        id_user=sharedPreferences.getInt("id_user",0);

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        switchNet();






        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ViewModel Provider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        rvChannel_frg1 = new RvChannel_Frg1(this, this);
        recommend_adapter = new ChannelDetail_adapter(this);

        detail_adapter = new FunnyAdapter(this,this,true);
        infinitAdapter = new InfinitFrg1_PagerAdapter(this);

        funnyAdapter = new ChannelDetail_adapter(this);
        funnyAdapter_liky = new FunnyAdapter(this,this,b_switchLink);
        funnyAdapter_view = new FunnyAdapter(this,this,b_switchLink);

        initRailActivity();
        taginit();

        /////
        initRv_Vp_adapter();

        //retrieve data into modelClass
        request();

        //update AND get Data from DataModel into LiveData
        allChannel();
        getChannel_kind();
        getChannel_detail();
        getFunny_view();
        getFunny_liky();
        getFunny_subMenu();


binding.profileShowChannelMainActivity.setOnClickListener(v -> {

});











    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void switchNet() {

        binding.switchNetToolbar.setChecked(sharedPreferences.getBoolean("switchNet",true));
        b_switchLink=sharedPreferences.getBoolean("switchNet",true);
        Toast.makeText(this,""+b_switchLink,Toast.LENGTH_LONG).show();

        binding.switchNetToolbar.setOnCheckedChangeListener((buttonView, isChecked) -> {

            switchEditor = sharedPreferences.edit();
            switchEditor.putBoolean("switchNet", isChecked);
            switchEditor.commit();
            recreate();



        });
    }


    //request from Api to get DataModel
    private void request() {


        mainViewModel.requestChannel_kind(1);
        //detail Channel Selected
        mainViewModel.requestChannel_detail(3);
        // mainViewModel.requestChannel_detail(1);
        mainViewModel.requestFunny_view();
        mainViewModel.requestFunny_liky();
        mainViewModel.requestFunny_subMenu(2);
    }

    //Initialize widgets
    @SuppressLint("NonConstantResourceId")
    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(item -> {
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
        });
        
binding.navRail.getHeaderView().findViewById(R.id.fab_add).setOnClickListener(v -> {
    startActivity(new Intent(MainActivity.this, UserActivity.class));
});
        
             /*  binding.navRail.setOnItemReselectedListener(item -> {
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
        });*/
    }

    private void taginit() {

        binding.rvMenuTagFrg1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        GradientDrawable drawable1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xffeff400, 0xffaff600});
        GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFF03A9F4, 0xFF90CAF9});
        GradientDrawable drawable3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFFFFEB3B, 0xffaaf400});
        GradientDrawable drawable4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFF7ADCCF, 0xFF80CBC4});
        GradientDrawable drawable5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xf469a9, 0xFFF48FB1});
        ArrayList<HashTagDataModel> tagList = new ArrayList<>();
        tagList.add(new HashTagDataModel("#اگی واگی", R.drawable.tag_huggy_1, drawable1));
        tagList.add(new HashTagDataModel("#سونیک", R.drawable.tag_sonic_1, drawable2));
        tagList.add(new HashTagDataModel("#آدمک خای خمیری", R.drawable.tag_claymixer_1, drawable3));
        tagList.add(new HashTagDataModel("#کریستمس", R.drawable.tag_christmas_1, drawable4));
        tagList.add(new HashTagDataModel("#کیسی میسی", R.drawable.tag_kissy_1, drawable5));


        tagAdapter = new TagAdapter(tagList, this);
        binding.rvMenuTagFrg1.setAdapter(tagAdapter);


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
                LinearLayoutManager.HORIZONTAL, false));
        binding.rvDetailFrg1.setAdapter(detail_adapter);

        //RecyclerView Selected 1
        binding.rvPopularMainActivity.setHasFixedSize(true);
        binding.rvPopularMainActivity.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopularMainActivity.setAdapter(funnyAdapter_liky);

        //recyclerView Selected2
        binding.rvBestViewMainActivity.setHasFixedSize(true);
        binding.rvBestViewMainActivity.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.rvBestViewMainActivity.setAdapter(funnyAdapter_view);

        //horizontal viewpager infinite

        binding.infinitCycleFrg1.setAdapter(infinitAdapter);

        //Recommended Vide Rv
        binding.rvRecommendFrg1.setHasFixedSize(true);
        binding.rvRecommendFrg1.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        binding.rvRecommendFrg1.setAdapter(recommend_adapter);


        binding.rvSubMenuTagFrg1.setAdapter(funnyAdapter);
        binding.rvSubMenuTagFrg1.setLayoutManager(new GridLayoutManager
                (this, 3, GridLayoutManager.VERTICAL, false));
       // binding.rvSubMenuTagFrg1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));






    }

    //Set Data to LiveData
    private void allChannel() {
        binding.showAllChannel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllChannelActivity.class);
            startActivity(intent);
        });
    }

    private void getChannel_kind() {
        mainViewModel.getChannel_kind().observe(this, channelDataModels -> {
            if (channelDataModels!=null) {
                rvChannel_frg1.setData(channelDataModels);
            }
        });
    }

    private void getChannel_detail() {
        mainViewModel.getChannel_detail().observe(this, channelDataModel -> {

            if (channelDataModel!=null){
                List<FunnyDataModel> funnyDataModels=new ArrayList<>((channelDataModel).getVideos_channel());

                detail_adapter.setData(funnyDataModels);


                Glide.with(this).load(channelDataModel.getProfile_chann())
                        .into(binding.profileShowChannelMainActivity);
                binding.profileShowChannelMainActivity.setOnClickListener(v -> {
                int id_channel_single=channelDataModel.getId_channel();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("id_channel", id_channel_single);
                    startActivity(intent);
                });
                binding.subShowChannelMainActivity.setText(channelDataModel.getFollowers());
                binding.titleShowChannelMainActivity.setText(channelDataModel.getName_chan_en().trim());

            }else {
                Toast.makeText(MainActivity.this,"net not connection",Toast.LENGTH_LONG).show();
            }



        });
    }

    private void getFunny_view() {
        mainViewModel.getFunny_view().observe(this, funnyDataModels -> {
            if (funnyDataModels!=null) {
                infinitAdapter.setData(funnyDataModels);
                funnyAdapter_view.setData(funnyDataModels);
            } else {
                Toast.makeText(this, "اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFunny_liky() {
        mainViewModel.getFunny_liky().observe(this, funnyDataModels -> {

            if (funnyDataModels!=null) {
                funnyAdapter_liky.setData(funnyDataModels);
            }else {mainViewModel.requestFunny_liky();}

        });
    }

    private void getFunny_subMenu() {

        mainViewModel.getFunny_subMenu().observe(this, funnyDataModels -> {
            if (funnyDataModels!=null) {
                funnyAdapter.setFunnyDataModels(funnyDataModels);
                recommend_adapter.setFunnyDataModels(funnyDataModels);
            }
        });
    }


    @Override
    public void OnclickDetail(int pos) {
        id_channel = pos;
        mainViewModel.requestChannel_detail(id_channel);

    }

    @Override
    public void onMenuClick(int position) {
        mainViewModel.requestFunny_subMenu(position);

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClickSave(int id_vid) {
        Service.getApiClient().insertUserSave(id_user,id_vid,1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "bookmark saved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClickSee(int id_vid) {
        Service.getApiClient().insertUserSee(id_user,id_vid,FUNNY_KIND).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
       
    }

    @Override
    public void onClickLike(int id_vid) {
        Service.getApiClient().insertUserLike(id_user,id_vid,FUNNY_KIND).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClickLater(int id_vid) {
        Service.getApiClient().insertUserLater(id_user,id_vid,FUNNY_KIND).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClickSub(int id_channel) {

    }
}