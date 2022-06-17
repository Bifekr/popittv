package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter2;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.databinding.FragmentMain1Binding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain1 extends Fragment {


    MainViewModel mainViewModel;
    private FragmentMain1Binding binding;
    RvChannel_Frg1 rvChannel_frg1;

    private CardPagerAdapter2 cardPagerAdapter2;
    private ShadowTransformer shadowTransformer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain1Binding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.requestChannel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        rvChannel_frg1=new RvChannel_Frg1(requireActivity());
        binding.vpChannelListFrg1.setAdapter(rvChannel_frg1);
        binding.vpChannelListFrg1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
     /*   cardPagerAdapter2 = new CardPagerAdapter2(getActivity());
        shadowTransformer = new ShadowTransformer(binding.vpChannelListFrg1, cardPagerAdapter2);
        binding.vpChannelListFrg1.setAdapter(cardPagerAdapter2);
        binding.vpChannelListFrg1.setPageTransformer(false, shadowTransformer);
        binding.vpChannelListFrg1.setOffscreenPageLimit(6);*/


getChannel();

    }

    private void getChannel() {
        mainViewModel.getChannel().observe(requireActivity(),channelDataModels -> {
            if (channelDataModels!=null){
                rvChannel_frg1.setData(channelDataModels);
                for (ChannelDataModel channel:channelDataModels
                     ) {
//                    cardPagerAdapter2.addCardItem(channel);

                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
