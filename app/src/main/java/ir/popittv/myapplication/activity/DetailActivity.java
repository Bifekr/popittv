package ir.popittv.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.databinding.ActivityDetailBinding;
import ir.popittv.myapplication.request.ApiClient;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;
import ir.popittv.myapplication.viewmodel.UserViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements OnClickFrg1, OnClickFunny {

   private int id_channel2;
  private   int kind2;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int id_user;
    private boolean b_sub;
    private ActivityDetailBinding binding;
    private MainViewModel viewModel;
    private UserViewModel userViewModel;
    private ChannelDetail_adapter detail_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id_user = sharedPreferences.getInt("id_user", 0);

        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        /*if (!b_sub) {
            binding.parentSub.setBackgroundResource(R.drawable.shape_active_youtube);
            b_sub=false;


        }*/

        detail_adapter = new ChannelDetail_adapter(this, this);


        binding.rvAllVideoDetailActivity.setHasFixedSize(true);
        binding.rvAllVideoDetailActivity.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL
                , false));
        binding.rvAllVideoDetailActivity.setAdapter(detail_adapter);

        id_channel2 = getIntent().getIntExtra("id_channel_single", 0);
        kind2 = getIntent().getIntExtra("kind", 0);
        Toast.makeText(this, "" + kind2, Toast.LENGTH_SHORT).show();
        viewModel.requestChannel_detail(id_channel2, kind2);
        binding.parentSub.setOnClickListener(v -> {


            if (id_user>0 ) {
                Service.getApiClient().insertUserSub(id_user,id_channel2).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if(view.getTag()==null){
                            //first time set color to green
                            view.setTag("green");
                            view.setBackgroundResource(R.color.green);
                            binding.parentSub.setBackgroundResource(R.drawable.shape_youtube_desable);
                            Toast.makeText(DetailActivity.this, "اضافه شد", Toast.LENGTH_SHORT).show();
                        }else if(view.getTag().toString().equals("green")){
                            //green color already set change to grey
                            view.setBackgroundResource(R.color.gray);
                            binding.parentSub.setBackgroundResource(R.drawable.shape_active_youtube);
                            Toast.makeText(DetailActivity.this, "حذف شد", Toast.LENGTH_SHORT).show();
                            view.setTag(null);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        binding.parentSub.setBackgroundResource(R.drawable.shape_active_youtube);
                    }
                });


            }else {
                Toast.makeText(DetailActivity.this, "برای فالو کرن ، ابتدا وارد شوید", Toast.LENGTH_SHORT).show();
            }
        });

        getChannel_detail();
    }


    private void getChannel_detail() {
        viewModel.getChannel_detail().observe(this, channelDataModel -> {

            if (channelDataModel!=null) {
                detail_adapter.setFunnyDataModels(channelDataModel.getVideos_channel());

                Glide.with(this).load(channelDataModel.getBanner_chann())
                        .into(binding.ivBannerItemChannelAll);
                Glide.with(this).load(channelDataModel.getProfile_chann())
                        .into(binding.ivProfileItemAllChan);
                binding.tvSubAllChannel.setText(channelDataModel.getFollowers());
                binding.tvAge2AllChannel.setText(channelDataModel.getAge_name());
                binding.titleFaItemAllChannel.setText(channelDataModel.getName_chan_fa().trim());
                binding.titleEnItemAllChannel.setText(channelDataModel.getName_chan_en().trim());
            }


        });
    }


    @Override
    public void OnclickDetail(int pos) {

    }

    @Override
    public void onMenuClick(int position) {

    }

    @Override
    public void onRow_index(int position) {

    }

    @Override
    public void onClickSave(int id_vid) {

    }

    @Override
    public void onClickSee(int id_vid) {

    }

    @Override
    public void onClickLike(int id_vid) {

    }

    @Override
    public void onClickLater(int id_vid) {

    }

    @Override
    public void onClickSub(int id_channel) {

    }

    @Override
    public void onClickPlayer(int id_vid_funny, int id_channel, int kind) {
        Intent intent = new Intent(DetailActivity.this, PlayerActivity.class);
        intent.putExtra("id_vid_funny", id_vid_funny);
        intent.putExtra("kind", kind2);
        Toast.makeText(DetailActivity.this, "kind" + kind2 + "kk" + kind, Toast.LENGTH_SHORT).show();
        intent.putExtra("id_channel", id_channel2);
        startActivity(intent);
    }
}