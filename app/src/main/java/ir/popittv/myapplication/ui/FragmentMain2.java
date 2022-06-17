package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.ShadowTransformer;
import ir.popittv.myapplication.adapter.CardPagerAdapter;
import ir.popittv.myapplication.adapter.DynamicRvAdapter;
import ir.popittv.myapplication.adapter.PagerAdapter;
import ir.popittv.myapplication.adapter.PagerAdapter2;
import ir.popittv.myapplication.adapter.StaticRvAdapter;
import ir.popittv.myapplication.databinding.FragmentMain2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.StaticRvModel;
import ir.popittv.myapplication.utils.OnClickStaticRv;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain2 extends Fragment implements OnClickStaticRv {

    PagerAdapter pagerAdapter;
    PagerAdapter2 pagerAdapter2;
    StaticRvAdapter staticRvAdapter;
    DynamicRvAdapter dynamicRvAdapter;
    int id_subMenu;

    private FragmentMain2Binding binding;
    private MainViewModel mainViewModel;

    Animation animLogoMove,animTransition;


    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // init adapters
        initAdapter();

        //set data into dataModel
        mainViewModel.requestFunny_best();
        mainViewModel.requestFunny_view();
        mainViewModel.requestFunny_liky();
        mainViewModel.requestFunny_subMenu(2);


        animLogoMove = AnimationUtils.loadAnimation(getContext(), R.anim.logo_move);
        animTransition = AnimationUtils.loadAnimation(getContext(), R.anim.transition);

        ArrayList<StaticRvModel> staticRvModels=new ArrayList<>();
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_1,"جدیدترین ها"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_2,"پرورش خلاقیت"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_3," کاردستی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"مهارت افردی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_3,"مهارت اجتماعی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"تقویت هوش هیجانی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_3," رفار صحیح در خانه وجامعه"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"نکات علمی و دانش عمومی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"تقویت مهارت در نقاشی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"جشن ها و مناسبت ها"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"آشنایی و نگهداری از طبیعت"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"افزایش انرژی و شادابی"));
        staticRvModels.add(new StaticRvModel(R.drawable.iv_btn_4,"رقص با خانواده"));
        staticRvAdapter = new StaticRvAdapter(staticRvModels,this);
        binding.rvSubMenuFunny.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.rvSubMenuFunny.setAdapter(staticRvAdapter);


        //get data from dataModel
        getFunny_best();
        getFunny_view();
        getFunny_liky();
        getFunny_subMenu();

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);







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





    }


    private void getFunny_best() {

        mainViewModel.getFunny_best().observe(requireActivity(), funnyDataModels -> {
            if (funnyDataModels!=null) {
                for (FunnyDataModel fuuny : funnyDataModels
                ) {
                    mCardAdapter.addCardItem(fuuny);
                }


            }
        });

    }

    private void getFunny_view() {
        mainViewModel.getFunny_view().observe(requireActivity(), funnyDataModels -> {
            if (funnyDataModels!=null) {
                pagerAdapter.setData(funnyDataModels);
                for (FunnyDataModel funnyDataModel : funnyDataModels) {
                   // Toast.makeText(getActivity(), "" + funnyDataModel.getTitle_en(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "ggggg", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFunny_liky() {
        mainViewModel.getFunny_liky().observe(requireActivity(), funnyDataModels -> {
            if (funnyDataModels!=null) {
                pagerAdapter2.setData(funnyDataModels);
            }
        });
    }

    private void getFunny_subMenu() {

        mainViewModel.getFunny_subMenu().observe(requireActivity(), funnyDataModels -> {
            if (funnyDataModels!=null) {
                dynamicRvAdapter.setData(funnyDataModels);
            }
        });
    }

    private void initAdapter() {

        pagerAdapter = new PagerAdapter(getActivity());
        pagerAdapter2 = new PagerAdapter2(getActivity());
        mCardAdapter = new CardPagerAdapter(getActivity());


        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager2.setAdapter(pagerAdapter2);
        // All Funny Video from SubMenu into RecyclerView
        dynamicRvAdapter = new DynamicRvAdapter(getActivity());
        binding.rvAllFunny.setAdapter(dynamicRvAdapter);
        binding.rvAllFunny.setLayoutManager(new GridLayoutManager
                (getActivity(), 3, GridLayoutManager.VERTICAL, false));
        binding.rvAllFunny.addItemDecoration(new DividerItemDecoration(requireActivity(),LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onMenuClick(int position) {
       // Toast.makeText(requireActivity(), "menu posiution" + position, Toast.LENGTH_SHORT).show();
        id_subMenu=position;
        mainViewModel.requestFunny_subMenu(id_subMenu);

    }
}
