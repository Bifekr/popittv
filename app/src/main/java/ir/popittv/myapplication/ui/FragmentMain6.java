package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.popittv.myapplication.databinding.FragmentMain6Binding;

public class FragmentMain6  extends Fragment {

    private FragmentMain6Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentMain6Binding.inflate(inflater,container,false);









       return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding =null;
    }
}
