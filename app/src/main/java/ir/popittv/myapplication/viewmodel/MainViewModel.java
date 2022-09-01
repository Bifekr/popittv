package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.HashTagDataModel;
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
    public void requestChannel_detail(int id_channel,int kind){mainRepository.requestChannel_detail(id_channel,kind);}

    public LiveData<List<ChannelDataModel>> getChannel_all(){return mainRepository.getChannel_all();}
    public void requestChannel_all(int kind,int age){mainRepository.requestChannel_all(kind,age);}

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
    public void requestFunny_view(int kind){
        mainRepository.requestFunny_view(kind);
    }

    ////////// funny_liky ////////////
    public LiveData<List<FunnyDataModel>> getFunny_liky(){
        return mainRepository.getFunny_liky();
    }
    public void requestFunny_liky(int kind){mainRepository.requestFunny_liky(kind);
    }

    /////////// funny_subMenu ////////////////

    public LiveData<List<FunnyDataModel>> getFunny_subMenu(){ return mainRepository.getFunny_subMenu(); }
    public void requestFunny_subMenu(int id_subMeny,int kind){ mainRepository.requestFunny_subMenu(id_subMeny,kind); }

    /////////////////////////

    public LiveData<FunnyDataModel> getFunny_single(){ return mainRepository.getFunny_single();}
    public void requestFunny_single(int id_funny,int kind){mainRepository.requestFunny_single(id_funny,kind);}


    public LiveData<List<FunnyDataModel>> getFunny_search(){return mainRepository.getFunny_search();}
    public void requestFunny_search(String search,int kind){ mainRepository.requestFunny_search(search,kind);}

    public LiveData<List<HashTagDataModel>> getTag(){return mainRepository.getTag();}
    public void request_tag(int kind){ mainRepository.request_tag(kind);}
}
