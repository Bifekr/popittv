package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import ir.popittv.myapplication.adapter.Frg2_VpSelect_Adapter;
import ir.popittv.myapplication.databinding.FragmentMain2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain2 extends Fragment {

    private FragmentMain2Binding binding;
    private MainViewModel mainViewModel;
    Frg2_VpSelect_Adapter select_adapter;
    private Handler slideHandler = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater,container,false);


        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        select_adapter = new Frg2_VpSelect_Adapter(binding.selectVp2Frg2);
        mainViewModel.requestFunny_best();



        getBestFunny();
        binding.selectVp2Frg2.setAdapter(select_adapter);
        binding.selectVp2Frg2.setOffscreenPageLimit(6);
        binding.selectVp2Frg2.setClipChildren(false);
        binding.selectVp2Frg2.setClipToPadding(false);
        binding.selectVp2Frg2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(20));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });

            binding.selectVp2Frg2.setPageTransformer(transformer);
            binding.selectVp2Frg2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                    slideHandler.removeCallbacks(runnable);
                    slideHandler.postDelayed(runnable,200);
                }
            });







    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

          binding.selectVp2Frg2.setCurrentItem(binding.selectVp2Frg2.getCurrentItem() + 1);
        }
    };

    private void getBestFunny() {

        mainViewModel.getFunny_best().observe(requireActivity(), new Observer<List<FunnyDataModel>>() {
            @Override
            public void onChanged(List<FunnyDataModel> funnyDataModels) {
                if (funnyDataModels!=null){
                    select_adapter.setFunnyDataModelList(funnyDataModels);

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        slideHandler.removeCallbacks(runnable);
    }
}
