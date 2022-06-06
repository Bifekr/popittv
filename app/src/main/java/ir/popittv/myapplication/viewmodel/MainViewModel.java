package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.repository.MainRepository;

public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository;

    public MainViewModel() {
        mainRepository = MainRepository.getInstance();
    }


    ////////// funny all video /////////////////
    public LiveData<List<FunnyDataModel>> getCafe() {
        return mainRepository.getCafeBazar();
    }

    public void retrieveCafe() {
        mainRepository.retrieveCafe();
    }

    ///////////  funny Best /////////////////
    public LiveData<List<FunnyDataModel>> getFunny_best() {
        return mainRepository.getFunny_best();
    }

    public void requestFunny_best() {
        mainRepository.requestFunny_best();
    }
}
