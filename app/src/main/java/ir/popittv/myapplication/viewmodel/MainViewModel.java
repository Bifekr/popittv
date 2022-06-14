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

    public void retrieveCafe(String id_sumMenu) {
        mainRepository.retrieveCafe(id_sumMenu);
    }

    ///////////  funny Best /////////////////
    public LiveData<List<FunnyDataModel>> getFunny_best() {
        return mainRepository.getFunny_best();
    }

    public void requestFunny_best() {
        mainRepository.requestFunny_best();
    }

    ///////// funny_view /////////////
    public LiveData<List<FunnyDataModel>> getFunny_view(){
        return mainRepository.getFunny_view();
    }
    public void requestFunny_view(){
        mainRepository.requestFunny_view();
    }

    ////////// funny_liky ////////////
    public LiveData<List<FunnyDataModel>> getFunny_liky(){
        return mainRepository.getFunny_liky();
    }

    public void requestFunny_liky(){
        mainRepository.requestFunny_liky();
    }

    /////////// funny_subMenu ////////////////

    public LiveData<List<FunnyDataModel>> getFunny_subMenu(){
        return mainRepository.getFunny_subMenu();
    }

    public void requestFunny_subMenu(String id_subMeny){
        mainRepository.requestFunny_subMenu(id_subMeny);
    }


}
