package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter;
import ir.popittv.myapplication.adapter.Frg1Rv1_Adapter;
import ir.popittv.myapplication.adapter.PagerAdapter;
import ir.popittv.myapplication.adapter.PagerAdapter2;
import ir.popittv.myapplication.databinding.FragmentMain2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain2 extends Fragment {

    Frg1Rv1_Adapter adapter;
    PagerAdapter pagerAdapter;
    PagerAdapter2 pagerAdapter2;
    private FragmentMain2Binding binding;
    private MainViewModel mainViewModel;
    Animation animLogoMove,animTransition;


    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater, container, false);
        //// mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        animLogoMove = AnimationUtils.loadAnimation(getContext(), R.anim.logo_move);
        animTransition = AnimationUtils.loadAnimation(getContext(), R.anim.transition);


 /*       mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.drawable.banner1, R.string.alarms_destination_label));
        mCardAdapter.addCardItem(new CardItem(R.drawable.banner2, R.string.app_name));
        mCardAdapter.addCardItem(new CardItem(R.drawable.banner3, R.string.app_name));
        mCardAdapter.addCardItem(new CardItem(R.drawable.banner4, R.string.app_name));

        mCardShadowTransformer = new ShadowTransformer(binding.viewpagerSlideFrg2,  mCardAdapter);

        binding.viewpagerSlideFrg2.setAdapter(mCardAdapter);
        binding.viewpagerSlideFrg2.setPageTransformer(false, mCardShadowTransformer);
        binding.viewpagerSlideFrg2.setOffscreenPageLimit(3);*/






        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        //set data into dataModel
        mainViewModel.requestFunny_best();

        // init adapters
        adapter = new Frg1Rv1_Adapter(getActivity());
        pagerAdapter = new PagerAdapter(getActivity());
        pagerAdapter2 = new PagerAdapter2(getActivity());

        mCardAdapter = new CardPagerAdapter(getActivity());

        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager2.setAdapter(pagerAdapter2);

     /*  binding.ivLogo.setOnClickListener(v -> {
           binding.ivLogo.setVisibility(View.VISIBLE);
           binding.ivLogo.startAnimation(animLogoMove);
           binding.viewPager.setVisibility(View.VISIBLE);
           binding.viewPager.startAnimation(animTransition);
       });*/


        mCardShadowTransformer = new ShadowTransformer(binding.viewpagerSlideFrg2, mCardAdapter);
        binding.viewpagerSlideFrg2.setAdapter(mCardAdapter);
        binding.viewpagerSlideFrg2.setPageTransformer(false, mCardShadowTransformer);
        binding.viewpagerSlideFrg2.setOffscreenPageLimit(3);


        //get data from dataModel
        getBestFunny();


    }


    private void getBestFunny() {

        mainViewModel.getFunny_best().observe(requireActivity(), new Observer<List<FunnyDataModel>>() {
            @Override
            public void onChanged(List<FunnyDataModel> funnyDataModels) {
                if (funnyDataModels!=null) {
                    adapter.setData(funnyDataModels);
                    pagerAdapter.setData(funnyDataModels);
                    pagerAdapter2.setData(funnyDataModels);
                    for (FunnyDataModel fuuny : funnyDataModels
                    ) {
                        mCardAdapter.addCardItem(fuuny);
                    }


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
