package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.repository.MainRepository;

public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository;

    public MainViewModel() {
        mainRepository = MainRepository.getInstance();
    }


    public LiveData<List<ChannelDataModel>> getChannel_kind(){
        return mainRepository.getChannel_kind();
    }
    public void requestChannel_kind(int kind){
        mainRepository.requestChannel_kind(kind);
    }

    public LiveData<ChannelDataModel> getChannel_detail(){return mainRepository.getChannel_detail();}
    public void requestChannel_detail(int id_channel){mainRepository.requestChannel_detail(id_channel);}

    public LiveData<List<ChannelDataModel>> getChannel_all(){return mainRepository.getChannel_all();}
    public void requestChannel_all(int age){mainRepository.requestChannel_all(age);}

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

    public LiveData<List<FunnyDataModel>> getFunny_subMenu(){ return mainRepository.getFunny_subMenu(); }
    public void requestFunny_subMenu(int id_subMeny){ mainRepository.requestFunny_subMenu(id_subMeny); }

    /////////////////////////

    public LiveData<FunnyDataModel> getFunny_single(){ return mainRepository.getFunny_single();}
    public void requestFunny_single(int id_funny){mainRepository.requestFunny_single(id_funny);}


    public LiveData<List<FunnyDataModel>> getFunny_search(){return mainRepository.getFunny_search();}
    public void requestFunny_search(String search){ mainRepository.requestFunny_search(search);}
}
