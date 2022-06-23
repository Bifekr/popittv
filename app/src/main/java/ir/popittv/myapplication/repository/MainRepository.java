package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.request.FunnyApiClient;
import ir.popittv.myapplication.request.MainApiClient;

public class MainRepository {

    private static MainRepository mainRepository;
    public static MainRepository getInstance() {
        if (mainRepository==null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }


    private  MainApiClient mainApiClient;
    private FunnyApiClient funnyApiClient;

    private MainRepository() {
        mainApiClient = MainApiClient.getInstance();
        funnyApiClient = FunnyApiClient.getInstance();
    }



public LiveData<List<ChannelDataModel>> getChannel(){
        return mainApiClient.getChannel();
}

public void requestChannel(){
        mainApiClient.requestChannel_kind();
}

public LiveData<ChannelDataModel> getChannel_detail(){return mainApiClient.getChannel_detail();}
public void requestChannel_detail(int id_channel){mainApiClient.requestChannel_detail(id_channel);}

public LiveData<List<ChannelDataModel>> getChannel_all(){return mainApiClient.getChannel_all();}
public void requestChannel_all(int age){mainApiClient.requestChannel_all(age);}


    public LiveData<List<FunnyDataModel>> getFunny_best(){
        return funnyApiClient.getFunny_best();
    }
    public void requestFunny_best(){
        funnyApiClient.requestFunny_best();
    }

    public LiveData<List<FunnyDataModel>> getFunny_view(){
       return funnyApiClient.getFunny_view();
    }
    public void requestFunny_view(){funnyApiClient.requestFunny_view();}

    public LiveData<List<FunnyDataModel>> getFunny_liky(){
        return funnyApiClient.getFunny_liky();
    }
    public void requestFunny_liky(){
        funnyApiClient.requestFunny_liky();
    }

    public LiveData<List<FunnyDataModel>> getFunny_subMenu(){
        return funnyApiClient.getFunny_subMenu();
    }

    public void requestFunny_subMenu(int id_subMenu){
        funnyApiClient.requestFunny_subMenu(id_subMenu);
    }



}
