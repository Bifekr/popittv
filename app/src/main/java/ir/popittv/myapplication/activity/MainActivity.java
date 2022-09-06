package ir.popittv.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.FunnyAdapter;
import ir.popittv.myapplication.adapter.InfinitFrg1_PagerAdapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.adapter.SearchAdapter;
import ir.popittv.myapplication.adapter.TagAdapter;
import ir.popittv.myapplication.databinding.ActivityMainBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.HashTagDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.response.FunnyResponse;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnClickFrg1, OnClickFunny {

    private final int KIND = 1;
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    //global Variable
    int id_channel_single;
    private int row_index;
    private int id_user;
 
    private String  phone_user ;
    private Long  expireDate ;
    private Long  lastDate ;
    private boolean b_switchLink;
    private boolean b_search = false;

    private SharedPreferences sharedPreferences;

    //-------------
    private SharedPreferences.Editor switchEditor;
    private SharedPreferences.Editor editor;
    private ChannelDetail_adapter funnyAdapter;
    private FunnyAdapter funnyAdapter_liky;
    private FunnyAdapter funnyAdapter_view;
    private SearchAdapter searchAdapter;
    //global adapter
    private RvChannel_Frg1 rvChannel_frg1;
    private FunnyAdapter detail_adapter;
    private InfinitFrg1_PagerAdapter infinitAdapter;
    private InfinitFrg1_PagerAdapter infinitAdapter2;
    private ChannelDetail_adapter recommend_adapter;
    Snackbar mSnackbar;
    boolean doubleBackToExitPressedOnce = false;
    private TagAdapter tagAdapter;

    //Utils Class
    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get preferences data
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id_user = sharedPreferences.getInt("id_user", 0);
        phone_user = sharedPreferences.getString("phone_user", null);
        expireDate = sharedPreferences.getLong("expireDate", 0);
        lastDate = sharedPreferences.getLong("lastDate", 0);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //ViewModel Provider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        switchNet();


        searchAdapter = new SearchAdapter(MainActivity.this,this);
        search();
   //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initNewRv(this, this);
      initRailActivity();
        taginit();
        shareApp();
        initRv_Vp_adapter();
        getBest();
        getNew_best();
        getPeymentFromServer();
        //retrieve data into modelClass
        request();

        //update AND get Data from DataModel into LiveData
        allChannel();
        getChannel_kind();
        getChannel_detail();
        getFunny_view();
        getFunny_liky();
        getFunny_subMenu();
        getSearchFunny();

        mSnackbar = Snackbar.make(binding.getRoot(), "Please click BACK again to exit", Snackbar.LENGTH_SHORT);
        mSnackbar.setAction("exit",v -> {
            onBackPressed();

        }).setActionTextColor(Color.RED)
        .setTextColor(Color.YELLOW);


     ///////////-----------------------OnCreate----------------------///////////////
    }//////////---------------------------------------------------////////////////

    private void shareApp() {
        binding.share.setOnClickListener(v -> {
            Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"http://dl.pikoboom.ir/game/12%20Locks%202/com.rud.twelvelocks2_12_apps.evozi.com.apk");
            startActivity(Intent.createChooser(shareIntent,"لینک دانلود برنامه پیکوبوم"));
        });
    }

    private void initNewRv(MainActivity mainActivity, MainActivity mainActivity1) {
        rvChannel_frg1 = new RvChannel_Frg1(mainActivity, mainActivity1);
        recommend_adapter = new ChannelDetail_adapter(mainActivity, mainActivity1);

        detail_adapter = new FunnyAdapter(mainActivity, mainActivity1);
        infinitAdapter = new InfinitFrg1_PagerAdapter(MainActivity.this,mainActivity1);
        infinitAdapter2 = new InfinitFrg1_PagerAdapter(MainActivity.this,mainActivity1);

        funnyAdapter = new ChannelDetail_adapter(mainActivity, mainActivity1);
        funnyAdapter_liky = new FunnyAdapter(mainActivity, mainActivity1);
        funnyAdapter_view = new FunnyAdapter(mainActivity, mainActivity1);


        tagAdapter = new TagAdapter( mainActivity);
    }
    private void getPeymentFromServer() {

        if (phone_user!=null) {
            Service.getApiClient().getPayment(phone_user).enqueue(new Callback<UserDataModel>() {
                @Override
                public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                    if (response.isSuccessful()) {
                        assert response.body()!=null;
                        expireDate = response.body().getExpireDate();
                        lastDate = expireDate - System.currentTimeMillis();
                        lastDate = (lastDate / 1000);
                        lastDate = (lastDate / 60);
                        lastDate = (lastDate / 60);
                        lastDate = (lastDate / 24);
                        editor.putLong("lastDate", lastDate);
                        editor.commit();
                        if (lastDate<=0){
                            Toast.makeText(MainActivity.this, "کد دسترسی به محتوا به پایان رسیده است", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDataModel> call, Throwable t) {

                }
            });

        }

    }
    private void getSearchFunny() {
        mainViewModel.getFunny_search().observe(this, funnyDataModels -> {

            if (funnyDataModels!=null) {
                b_search = true;
                binding.rvSearch.setVisibility(View.VISIBLE);
                searchAdapter.setData(funnyDataModels);
            }

        });
    }

    private void search() {

        binding.searchToolbar.setImeOptions( EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        binding.searchToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchToolbar.setIconified(false);
            }
        });
        binding.searchToolbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("")) {
                    mainViewModel.requestFunny_search(query,KIND);

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!newText.equals("")) {
                    mainViewModel.requestFunny_search(newText,KIND);

                }
                return true;
            }
        });
    }

    //-----------------get 720 or 480 -------------------------------
    private void switchNet() {

        binding.switchNetToolbar.setChecked(sharedPreferences.getBoolean("switchNet", true));
        b_switchLink = sharedPreferences.getBoolean("switchNet", true);


        binding.switchNetToolbar.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                binding.iconWifiToolbar.setImageResource(R.drawable.ic_baseline_account_circle_24);
            }
            switchEditor = sharedPreferences.edit();
            switchEditor.putBoolean("switchNet", isChecked);
            switchEditor.commit();
            recreate();


        });
    }

    //request from Api to get DataModel
    private void request() {

        mainViewModel.requestChannel_kind(KIND);
        //detail Channel Selected

        mainViewModel.requestChannel_detail(1, KIND);
        mainViewModel.requestFunny_view(KIND);
        mainViewModel.requestFunny_liky(KIND);
        mainViewModel.requestFunny_subMenu(0, KIND);
        binding.parentItemTag.setOnClickListener(v->{
            mainViewModel.requestFunny_subMenu(0, KIND);
        });
        mainViewModel.request_tag(KIND);
    }

    //Initialize widgets

    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(item -> {
                int item2=item.getItemId();
            if(item2==R.id.reality){
                startActivity(new Intent(MainActivity.this, RealityActivity.class));
            }else if (item2==R.id.farsi){
                startActivity(new Intent(MainActivity.this, FarsiActivity.class));
            }else if (item2==R.id.learning){
                startActivity(new Intent(MainActivity.this, StudyActivity.class));
            }else if (item2==R.id.games){
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
         return true;
        });

        Objects.requireNonNull(binding.navRail.getHeaderView()).findViewById(R.id.fab_add).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        });

    }

    private void taginit() {


    /*    GradientDrawable drawable1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
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
        tagList.add(new HashTagDataModel("#Huggy Wuggy", R.drawable.tag_huggy_1, drawable1, "#اگی واگی"));
        tagList.add(new HashTagDataModel("#Sonic", R.drawable.tag_sonic_1, drawable2, "#سونیک"));
        tagList.add(new HashTagDataModel("#duls khamir", R.drawable.tag_claymixer_1, drawable3, "#آدمک خای خمیری"));
        tagList.add(new HashTagDataModel("#Christmas", R.drawable.tag_christmas_1, drawable4, "#کریستمس"));
        tagList.add(new HashTagDataModel("#Kissy Missy", R.drawable.tag_kissy_1, drawable5, "#کیسی میسی"));
*/
        mainViewModel.getTag().observe(this, hashTagDataModels -> {
            tagAdapter.setData(hashTagDataModels);
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
        binding.infinitCycle2Frg1.setAdapter(infinitAdapter2);

        //Recommended Vide Rv
        binding.rvRecommendFrg1.setHasFixedSize(true);
        binding.rvRecommendFrg1.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        binding.rvRecommendFrg1.setAdapter(recommend_adapter);


        binding.rvSubMenuTagFrg1.setAdapter(funnyAdapter);
        binding.rvSubMenuTagFrg1.setLayoutManager(new GridLayoutManager
                (this, 3, GridLayoutManager.VERTICAL, false));
        // binding.rvSubMenuTagFrg1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        binding.rvSearch.setAdapter(searchAdapter);
        binding.rvSearch.setLayoutManager(new GridLayoutManager
                (this, 3, GridLayoutManager.VERTICAL, false));

        binding.rvMenuTagFrg1.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false));
        binding.rvMenuTagFrg1.setAdapter(tagAdapter);


    }

    //Set Data to LiveData
    private void allChannel() {
        binding.showAllChannel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllChannelActivity.class);
            intent.putExtra("kind",KIND);
            startActivity(intent);
        });
    }

    private void getChannel_kind() {
        mainViewModel.getChannel_kind().observe(this, channelDataModels -> {
            if (channelDataModels!=null) {
                rvChannel_frg1.setData(channelDataModels);

            }else {
                Toast.makeText(this,"not success",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getChannel_detail() {
        mainViewModel.getChannel_detail().observe(this, channelDataModel -> {

            if (channelDataModel!=null) {
                List<FunnyDataModel> funnyDataModels5 = new ArrayList<>((channelDataModel).getVideos_channel());

                detail_adapter.setData(funnyDataModels5);


                Glide.with(this).load(channelDataModel.getProfile_chann())
                        .into(binding.profileShowChannelMainActivity);
                binding.profileShowChannelMainActivity.setOnClickListener(v -> {
                    id_channel_single = channelDataModel.getId_channel();


                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("id_channel_single", id_channel_single);
                    intent.putExtra("kind", KIND);

                    startActivity(intent);
                });
                binding.subShowChannelMainActivity.setText(channelDataModel.getFollowers());
                binding.titleShowChannelMainActivity.setText(channelDataModel.getName_chan_en().trim());

            } else {
                Toast.makeText(MainActivity.this, "net not connection", Toast.LENGTH_LONG).show();
            }


        });
    }

    private void getNew_best(){
        Service.getApiClient().getNew_Best(KIND).enqueue(new Callback<List<FunnyDataModel>>() {
            @Override
            public void onResponse(Call<List<FunnyDataModel>> call, Response<List<FunnyDataModel>> response) {
                if (response.body()!=null) {
                    infinitAdapter2.setData(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<FunnyDataModel>> call, Throwable t) {

            }
        });
    }

    private void getBest(){

        Service.getApiClient().getBest(KIND).enqueue(new Callback<List<FunnyDataModel>>() {
            @Override
            public void onResponse(Call<List<FunnyDataModel>> call, Response<List<FunnyDataModel>> response) {
                if (response.body()!=null) {
                    infinitAdapter.setData(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<FunnyDataModel>> call, Throwable t) {

            }
        });
    }

    private void getFunny_view() {
        mainViewModel.getFunny_view().observe(this, funnyDataModels -> {
            if (funnyDataModels!=null) {

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
            } else {
                mainViewModel.requestFunny_liky(KIND);
            }

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
        id_channel_single = pos;

        mainViewModel.requestChannel_detail(pos, KIND);

    }

    @Override
    public void onMenuClick(int position) {
        mainViewModel.requestFunny_subMenu(position, KIND);

    }

    @Override
    public void onRow_index(int position) {
        mainViewModel.requestChannel_detail(position,KIND);
    }


    @Override
    protected void onResume() {
        super.onResume();
getChannel_detail();
request();

    }


    @Override
    public void onBackPressed() {
        if (b_search) {
            binding.rvSearch.setVisibility(View.GONE);
        } else {

            if (mSnackbar.isShown()) {
                super.onBackPressed();
            } else {
                mSnackbar.show();
            }
         /*   if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);*/
        }


    }

    @Override
    public void onClickSave(int id_vid) {
        Service.getApiClient().insertUserSave(id_user, id_vid, KIND).enqueue(new Callback<ResponseBody>() {
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
        Service.getApiClient().insertUserSee(id_user, id_vid, KIND).enqueue(new Callback<ResponseBody>() {
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
        Service.getApiClient().insertUserLike(id_user, id_vid, KIND).enqueue(new Callback<ResponseBody>() {
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
        Service.getApiClient().insertUserLater(id_user, id_vid, KIND).enqueue(new Callback<ResponseBody>() {
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

    @Override
    public void onClickPlayer(int id_vid_funny, int id_channel, int kind) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putExtra("id_vid_funny", id_vid_funny);
        intent.putExtra("kind", KIND);
        intent.putExtra("id_channel", id_channel);
        startActivity(intent);
    }
}
