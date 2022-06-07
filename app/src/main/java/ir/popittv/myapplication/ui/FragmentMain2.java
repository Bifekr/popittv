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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ir.popittv.myapplication.adapter.Frg1Rv1_Adapter;
import ir.popittv.myapplication.databinding.FragmentMain2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain2 extends Fragment {

    Frg1Rv1_Adapter adapter;
    private FragmentMain2Binding binding;
    private MainViewModel mainViewModel;
    private Handler slideHandler = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain2Binding.inflate(inflater, container, false);
        //// mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        //set data into dataModel
        mainViewModel.requestFunny_best();

        // RecyclerView 1
       adapter = new Frg1Rv1_Adapter(getActivity());
        binding.rv4Frg1.setAdapter(adapter);
        binding.rv4Frg1.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        //get data from dataModel
        getBestFunny();


    }


    private void getBestFunny() {

        mainViewModel.getFunny_best().observe(requireActivity(), new Observer<List<FunnyDataModel>>() {
            @Override
            public void onChanged(List<FunnyDataModel> funnyDataModels) {
                if (funnyDataModels!=null) {
                    adapter.setData(funnyDataModels);
                    for (FunnyDataModel cafe:funnyDataModels
                    ) {
                        Toast.makeText(getContext(), "+++" + cafe.getPoster(), Toast.LENGTH_SHORT).show();
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
