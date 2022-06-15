package ir.popittv.myapplication.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.utils.AppExecuter;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MainApiClient {



    private static MainApiClient mainApiClient;


    private RetrieveCafeBazarRunnable cafeBazarRunnable;


    private final MutableLiveData<List<FunnyDataModel>> mCafe;


    private MainApiClient() {

        mCafe = new MutableLiveData<>();
    }

    public static MainApiClient getInstance() {
        if (mainApiClient==null) {
            mainApiClient = new MainApiClient();
        }
        return mainApiClient;
    }


    public LiveData<List<FunnyDataModel>> getCafeBazar() {
        return mCafe;
    }





    //retrieve data from localHost
    public void retrieveCafe(int id_SubMenu) {


        if (cafeBazarRunnable!=null) {
            cafeBazarRunnable = null;
        }

        cafeBazarRunnable = new RetrieveCafeBazarRunnable(id_SubMenu);

        final Future myHandler2 = AppExecuter.getAppExecuter().networkIo().submit(cafeBazarRunnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        }, 2, TimeUnit.MINUTES);
    }

    private class RetrieveCafeBazarRunnable implements Runnable {


        private final boolean canclable;
        int id_sumMenu;

        public RetrieveCafeBazarRunnable(int id_sumMenu) {
            this.id_sumMenu=id_sumMenu;
            canclable = false;
        }


        @Override
        public void run() {


            try {
                if (canclable)
                    return;

                Response response2 = cafeCallMethod(id_sumMenu).execute();

                if (response2.code()==200) {
                    assert response2.body()!=null;
                    List<FunnyDataModel> cafeModels = new ArrayList<>(((FunnyResponse) response2.body()).getFunny());

                    mCafe.postValue(cafeModels);
                } else {
                    String error = response2.errorBody().string();
                    Log.i("tag", "run: " + error);
                }

            } catch (IOException e) {

                e.printStackTrace();
                mCafe.postValue(null);
            }


        }


        private Call<FunnyResponse> cafeCallMethod(int id_subMenu) {
            return Service.getApiClient().getFunny(id_subMenu);

        }


    }

}
