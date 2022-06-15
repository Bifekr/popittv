package ir.popittv.myapplication.ui;

import android.os.Bundle;
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
import ir.popittv.myapplication.adapter.Frg2Rv1_Adapter;
import ir.popittv.myapplication.databinding.FragmentMain1Binding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain1 extends Fragment {


    MainViewModel mainViewModel;
    private FragmentMain1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMain1Binding.inflate(inflater, container, false);


        return binding.getRoot();
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
