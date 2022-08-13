package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.ChannelDetail_adapter;
import ir.popittv.myapplication.adapter.InfinitFrg1_PagerAdapter;
import ir.popittv.myapplication.adapter.Recommend_Adapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.databinding.FragmentMain1Binding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain1 extends Fragment implements OnClickFrg1 , OnClickFunny {

    private FragmentMain1Binding binding;
    private MainViewModel mainViewModel;


    //global adapter
    private RvChannel_Frg1 rvChannel_frg1;
    private ChannelDetail_adapter detail_adapter;
    private InfinitFrg1_PagerAdapter infinitAdapter;
    Recommend_Adapter recommend_adapter;


    //global Variable
    int id_channel;
    String tv1;


    //Utils Class
    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain1Binding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        initRv_Vp_adapter();
        //request from Api to get DataModel
        mainViewModel.requestChannel_kind(1);
        //detail Channel Selected
       // mainViewModel.requestChannel_detail(1);
        mainViewModel.requestFunny_view();





        //update AND get Data from DataModel into LiveData
        getChannel_kind();
        getChannel_detail();
        getFunny_view();


        return binding.getRoot();
    }


    private void initRv_Vp_adapter() {

        //init channel list Adapter
        rvChannel_frg1 = new RvChannel_Frg1(requireActivity(),this);
        binding.parentChannelListFrg1.setAdapter(rvChannel_frg1);
        binding.parentChannelListFrg1.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        //Show Detail Channel Recycler
        detail_adapter=new ChannelDetail_adapter(requireActivity(),this);
        binding.rvDetailFrg1.setAdapter(detail_adapter);
        binding.rvDetailFrg1.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL,false));


        //horizontal viewpager infinite
        infinitAdapter = new InfinitFrg1_PagerAdapter(requireActivity());
        binding.infinitCycleFrg1.setAdapter(infinitAdapter);

        //Recommended Vide Rv
        recommend_adapter=new Recommend_Adapter(requireActivity());
        binding.rvRecommendFrg1.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL,false));
        binding.rvRecommendFrg1.setAdapter(recommend_adapter);



     /*   cardPagerAdapter2 = new CardPagerAdapter2(getActivity());
        shadowTransformer = new ShadowTransformer(binding.vpChannelListFrg1, cardPagerAdapter2);
        binding.vpChannelListFrg1.setAdapter(cardPagerAdapter2);
        binding.vpChannelListFrg1.setPageTransformer(false, shadowTransformer);
        binding.vpChannelListFrg1.setOffscreenPageLimit(6);*/


    }

    private void getChannel_kind() {
        mainViewModel.getChannel_kind().observe(requireActivity(), channelDataModels -> {
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
        mainViewModel.getChannel_detail().observe(requireActivity(), channelDataModel -> {




           Glide.with(requireActivity()).load(channelDataModel.getProfile_chann())
                    .into(binding.showProfileChanFrg1);

            List<FunnyDataModel> models = new ArrayList<>((channelDataModel).getVideos_channel());
            detail_adapter.setFunnyDataModels(models);


       //  binding.tvSubDetailChanFrg1.setText(tv1);
           // binding.tvAgeDetailChanFrg1.setText(channelDataModel.getAge()+"");
           // binding.titleFaDetailChanFrg1.setText(channelDataModel.getName_chan_fa()+"");
           // binding.titleEnDetailChanFrg1.setText(channelDataModel.getName_chan_en()+"");



        });
    }

    private void getFunny_view() {
        mainViewModel.getFunny_view().observe(requireActivity(), funnyDataModels -> {
            if (funnyDataModels!=null) {
                infinitAdapter.setData(funnyDataModels);
                recommend_adapter.setFunnyDataModels(funnyDataModels);
               /* for (FunnyDataModel funnyDataModel : funnyDataModels) {
                     Toast.makeText(getActivity(), "" + funnyDataModel.getTitle_en(), Toast.LENGTH_SHORT).show();
                }*/
            } else {
                Toast.makeText(getActivity(), "ggggg", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    }



    @Override
    public void OnclickDetail(int pos) {

        id_channel=pos;
        mainViewModel.requestChannel_detail(id_channel);

    }

    @Override
    public void onMenuClick(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
}
