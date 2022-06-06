package ir.popittv.myapplication.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import ir.popittv.myapplication.AppExecuter;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.MovieResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.Response;

public class FunnyApiClient {

    private static FunnyApiClient funnyApiClient;
    public static FunnyApiClient getInstance(){
        if (funnyApiClient==null) {
            funnyApiClient = new FunnyApiClient();
        }
           return funnyApiClient;
    }

    private MutableLiveData<List<FunnyDataModel>> mFunny_best;
    //Constructor
    private FunnyApiClient(){
        mFunny_best = new MutableLiveData<>();

    }
    //liveData return Mutable content of funny_best
    public LiveData<List<FunnyDataModel>> getFunny_best(){
        return mFunny_best;
    }


    private FunnyBest_Runnable funnyBest_runnable;
    //method for request from host to get data and post in the livedata
    public void requestFunny_best(){

        if (funnyBest_runnable!=null){
            funnyBest_runnable = null;
        }
        funnyBest_runnable = new FunnyBest_Runnable();

        final Future myHandler = AppExecuter.getAppExecuter().networkIo().submit(funnyBest_runnable);
    }

    //create Runnable class for set to AppExecutor
    private class FunnyBest_Runnable implements Runnable{


        @Override
        public void run() {

            try {
                Response response = funnyResponseCall().execute();
                if (response.body()!= null){

                    List<FunnyDataModel> funnyDataModelList = new ArrayList<>(((FunnyResponse) response.body()).getFunny());

                    mFunny_best.postValue(funnyDataModelList);

                }else {
                    assert response.errorBody()!=null;
                    String error = response.errorBody().string();
                    Log.i("tag", "run: " + error);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mFunny_best.postValue(null);
            }

        }





        private Call<FunnyResponse> funnyResponseCall(){
            return Service.getApiClient().getFunny_best();
        }



    }



















}
