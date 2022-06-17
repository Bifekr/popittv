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

import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.InfinitFrg1_PagerAdapter;
import ir.popittv.myapplication.adapter.Recommend_Adapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.databinding.FragmentMain1Binding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain1 extends Fragment {

    private FragmentMain1Binding binding;
    private MainViewModel mainViewModel;


    //global adapter
    private RvChannel_Frg1 rvChannel_frg1;
    private InfinitFrg1_PagerAdapter infinitAdapter;
    Recommend_Adapter recommend_adapter;


    //Utils Class
    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain1Binding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        //request from Api to get DataModel
        mainViewModel.requestChannel();
        mainViewModel.requestFunny_view();


        initRv_Vp_adapter();


        //update AND get Data from DataModel into LiveData
        getChannel();
        getFunny_view();


        return binding.getRoot();
    }


    private void initRv_Vp_adapter() {

        //init channel list Adapter
        rvChannel_frg1 = new RvChannel_Frg1(requireActivity());
        //init Rv channel List
        binding.rvChannelListFrg1.setAdapter(rvChannel_frg1);
        binding.rvChannelListFrg1.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
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

    private void getChannel() {
        mainViewModel.getChannel().observe(requireActivity(),channelDataModels -> {
            if (channelDataModels!=null) {
                rvChannel_frg1.setData(channelDataModels);

                for (ChannelDataModel channel : channelDataModels
                ) {
//                    cardPagerAdapter2.addCardItem(channel);

                }
            }
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
