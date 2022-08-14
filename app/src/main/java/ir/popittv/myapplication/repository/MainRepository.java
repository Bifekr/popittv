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


    private final MainApiClient mainApiClient;
    private final FunnyApiClient funnyApiClient;

    private MainRepository() {
        mainApiClient = MainApiClient.getInstance();
        funnyApiClient = FunnyApiClient.getInstance();
    }



public LiveData<List<ChannelDataModel>> getChannel_kind(){
        return mainApiClient.getChannel_kind();
}

public void requestChannel_kind(int kind){
        mainApiClient.requestChannel_kind(kind);
}

public LiveData<ChannelDataModel> getChannel_detail(){return mainApiClient.getChannel_detail();}
public void requestChannel_detail(int id_channel,int kind){mainApiClient.requestChannel_detail(id_channel,kind);}

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
    public void requestFunny_view(int kind){funnyApiClient.requestFunny_view(kind);}

    public LiveData<List<FunnyDataModel>> getFunny_liky(){
        return funnyApiClient.getFunny_liky();
    }
    public void requestFunny_liky(int kind){funnyApiClient.requestFunny_liky(kind);
    }

    public LiveData<List<FunnyDataModel>> getFunny_subMenu(){ return funnyApiClient.getFunny_subMenu(); }
    public void requestFunny_subMenu(int id_subMenu,int kind){ funnyApiClient.requestFunny_subMenu(id_subMenu,kind); }

    public LiveData<FunnyDataModel> getFunny_single(){ return funnyApiClient.getFunny_single();}
    public void requestFunny_single(int id_funny){funnyApiClient.requestFunny_single(id_funny);}

    public LiveData<List<FunnyDataModel>> getFunny_search(){return funnyApiClient.getFunny_search();}
    public void requestFunny_search(String search){ funnyApiClient.requestFunny_search(search);}



}
