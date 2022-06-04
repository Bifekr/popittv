package ir.popittv.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
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
import ir.popittv.myapplication.models.CafeModel;
import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class FragmentMain1 extends Fragment {


    Frg1Rv1_Adapter adapter;
    Frg2Rv1_Adapter adapter2;
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

        mainViewModel.getMovieApi(1);
        mainViewModel.retrieveCafe();

        configrc();

        observMoviePopular();
        obsercCafeBazar();


    }

    private void obsercCafeBazar() {

     mainViewModel.getCafe().observe(requireActivity(), new Observer<List<CafeModel>>() {
         @Override
         public void onChanged(List<CafeModel> cafeModelList) {
             if (cafeModelList!=null){
                 adapter2.getDataCafe(cafeModelList);
                 for (CafeModel cafe:cafeModelList
                      ) {
                     Toast.makeText(getContext(), "+++" + cafe.getPoster(), Toast.LENGTH_SHORT).show();
                 }
                 
             }else {
                 Toast.makeText(getContext(), "nulllllll", Toast.LENGTH_SHORT).show();
             }
         }
     });

    }

    private void observMoviePopular() {
        mainViewModel.getMovie().observe(requireActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModel) {
                if (movieModel!=null) {
                    adapter.setData(movieModel);
                    for (MovieModel movies : movieModel
                    ) {
                        Log.i("tag", "onChanged: " + movies.getOverview());
                       // adapter.setData(movieModel);


                    }
                } else {
                    Toast.makeText(getContext(), "null", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void configrc() {

        // RecyclerView 1
        adapter = new Frg1Rv1_Adapter(getActivity());
        binding.rv1Frg1.setAdapter(adapter);
        binding.rv1Frg1.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));


        //recyclerView 2
        adapter2 = new Frg2Rv1_Adapter(getContext());
        binding.rv2FirstDashboard.setAdapter(adapter2);
        binding.rv2FirstDashboard.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
    }















    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
