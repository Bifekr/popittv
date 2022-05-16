package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.FragmentMain1Binding;

public class FragmentMain1 extends Fragment {


        private FragmentMain1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMain1Binding.inflate(inflater,container,false);
        View view=binding.getRoot();


        











        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


    //Reg

//    private FragmentJavaPracticeBinding binding;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentJavaPracticeBinding.inflate(inflater,container,false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        binding.notesRecyclerView.setVisibility(View.VISIBLE);//this hide/show recyclerview visibility
//        Log.d("TAG", "hidden: ");

    //endReg
}
