package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

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



    public LiveData<List<FunnyDataModel>> getCafeBazar(){
       return mainApiClient.getCafeBazar();
    }
    public void retrieveCafe(int id_sumMenu){
        mainApiClient.retrieveCafe(id_sumMenu);
    }


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
