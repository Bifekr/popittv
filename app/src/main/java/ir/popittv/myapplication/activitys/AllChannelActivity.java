package ir.popittv.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.AgeMenu_Adapter;
import ir.popittv.myapplication.adapter.AllChannel_Adapter;
import ir.popittv.myapplication.adapter.TagAdapter;
import ir.popittv.myapplication.databinding.ActivityAllChannelBinding;
import ir.popittv.myapplication.models.AgeDataModel;
import ir.popittv.myapplication.models.HashTagDataModel;
import ir.popittv.myapplication.utils.OnClickAllChannel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class AllChannelActivity extends AppCompatActivity implements OnClickAllChannel {

    private ActivityAllChannelBinding binding;
    private MainViewModel viewModel;

    //init Adapter
    AgeMenu_Adapter ageMenu_adapter;
    AllChannel_Adapter allChannel_adapter;

    //variable
    private int id_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllChannelBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        viewModel=new ViewModelProvider(this).get(MainViewModel.class);
        allChannel_adapter = new AllChannel_Adapter(this,this,AllChannelActivity.this);
        viewModel.requestChannel_all(0);



        initAgeMenu();

        initRecyclerView();


        getChannel_All();


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
        GradientDrawable drawable5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{0xf469a9, 0xFFF48FB1});
        ArrayList<AgeDataModel> ageList = new ArrayList<>();
        ageList.add(new AgeDataModel("بدون محدویت سنی",drawable1));
        ageList.add(new AgeDataModel("تا 2 4 ",drawable1));
        ageList.add(new AgeDataModel("5 تا 8 ",drawable2));
        ageList.add(new AgeDataModel("7تا 9  ",drawable3));
        ageList.add(new AgeDataModel("9 تا 14 ",drawable4));
        ageList.add(new AgeDataModel("5 تا 12 ",drawable5));
        ageList.add(new AgeDataModel("مناسب تمام سنین",drawable5));

        ageMenu_adapter=new AgeMenu_Adapter(ageList,this);
        binding.rvAgeMenuActivityAllChannel.setAdapter(ageMenu_adapter);

    }

    private void initRecyclerView() {
        binding.rvAllChannelActivityAllChannel.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL,false));
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
        viewModel.requestChannel_all(pos);

    }

    @Override
    public void onClickDetailChannel(int pos) {



    }
}