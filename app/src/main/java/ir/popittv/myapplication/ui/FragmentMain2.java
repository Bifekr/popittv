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
import ir.popittv.myapplication.adapter.Frg1Rv1_Adapter;
import ir.popittv.myapplication.adapter.PagerAdapter;

import ir.popittv.myapplication.databinding.FragmentMain2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain2 extends Fragment {

    Frg1Rv1_Adapter adapter;
    PagerAdapter pagerAdapter;
    private FragmentMain2Binding binding;
    private MainViewModel mainViewModel;
    Animation animLogoMove,animTransition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater, container, false);
        //// mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        animLogoMove= AnimationUtils.loadAnimation(getContext(), R.anim.logo_move);
        animTransition = AnimationUtils.loadAnimation(getContext(),R.anim.transition);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        //set data into dataModel
        mainViewModel.requestFunny_best();

        // RecyclerView 1
       adapter = new Frg1Rv1_Adapter(getActivity());
       pagerAdapter = new PagerAdapter(getActivity());
       binding.viewPager.setAdapter(pagerAdapter);
       binding.ivLogo.setOnClickListener(v -> {
           binding.ivLogo.setVisibility(View.VISIBLE);
           binding.ivLogo.startAnimation(animLogoMove);
           binding.viewPager.setVisibility(View.VISIBLE);
           binding.viewPager.startAnimation(animTransition);
       });


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
