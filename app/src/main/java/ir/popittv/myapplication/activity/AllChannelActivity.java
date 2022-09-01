package ir.popittv.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.AgeMenu_Adapter;
import ir.popittv.myapplication.adapter.AllChannel_Adapter;
import ir.popittv.myapplication.databinding.ActivityAllChannelBinding;
import ir.popittv.myapplication.models.AgeDataModel;
import ir.popittv.myapplication.utils.OnClickAllChannel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class AllChannelActivity extends AppCompatActivity implements OnClickAllChannel {

    private ActivityAllChannelBinding binding;
    private MainViewModel viewModel;

    //init Adapter
    AgeMenu_Adapter ageMenu_adapter;
    AllChannel_Adapter allChannel_adapter;
    View view;
    //variable
    private int kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllChannelBinding.inflate(getLayoutInflater());
       view=binding.getRoot();
        setContentView(view);

        viewModel=new ViewModelProvider(this).get(MainViewModel.class);
        allChannel_adapter = new AllChannel_Adapter(this,this,AllChannelActivity.this);
        kind=getIntent().getIntExtra("kind",1);
        viewModel.requestChannel_all(kind,0);

setActivityName();

        initAgeMenu();

        initRecyclerView();


        getChannel_All();



    }

    private void setActivityName() {
        switch (kind){
            case 1:
                binding.tvAllChannelActivityAllChannel.setText("All Funny Channels");

                view.setBackgroundResource(R.color.red_active);
                break;
            case 2:
                binding.tvAllChannelActivityAllChannel.setText("All Reality Channels");
                view.setBackgroundResource(R.color.blue_active);
                break;
            case 3:
                binding.tvAllChannelActivityAllChannel.setText("All Learning Channels");
                view.setBackgroundResource(R.color.green_active);
                break;
            case 4:
                binding.tvAllChannelActivityAllChannel.setText("All Farsi Channels");
                view.setBackgroundResource(R.color.purple_active);
                break;
        }
    }

    private void initAgeMenu() {
        binding.rvAgeMenuActivityAllChannel.setLayoutManager(new LinearLayoutManager
                (this,RecyclerView.HORIZONTAL,false));
        GradientDrawable drawable1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xffeff400, 0xffaff600});
        GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFF03A9F4, 0xFF90CAF9});
        GradientDrawable drawable3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFFFFEB3B, 0xffaaf400});
        GradientDrawable drawable4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xFF7ADCCF, 0xFF80CBC4});

        ArrayList<AgeDataModel> ageList = new ArrayList<>();
        ageList.add(new AgeDataModel("بدون محدویت سنی",drawable1));
        ageList.add(new AgeDataModel("تا 2 6 ",drawable1));
        ageList.add(new AgeDataModel("4 تا 8 ",drawable2));
        ageList.add(new AgeDataModel("612  ",drawable3));
        ageList.add(new AgeDataModel("8 تا 14 ",drawable4));


        ageMenu_adapter=new AgeMenu_Adapter(ageList,this);
        binding.rvAgeMenuActivityAllChannel.setAdapter(ageMenu_adapter);

    }

    private void initRecyclerView() {
        binding.rvAllChannelActivityAllChannel.setLayoutManager(new GridLayoutManager
                (this, 2, GridLayoutManager.VERTICAL, false));
        binding.rvAllChannelActivityAllChannel.setAdapter(allChannel_adapter);

    }


    private void getChannel_All(){
        viewModel.getChannel_all().observe(this,channelDataModels -> {
            if (channelDataModels!=null){
            allChannel_adapter.setChannelDataModels(channelDataModels);

            }
        });
    }

    @Override
    public void onClickAge(int pos) {

        Toast.makeText(this, "age id ; "+pos, Toast.LENGTH_SHORT).show();
        viewModel.requestChannel_all(kind,pos);

    }

    @Override
    public void onClickDetailChannel(int pos) {



    }
}