package ir.popittv.myapplication.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.response.FunnyResponse;
import ir.popittv.myapplication.utils.AppExecuter;
import retrofit2.Call;
import retrofit2.Response;

public class FunnyApiClient {

    private static FunnyApiClient funnyApiClient;

    private MutableLiveData<List<FunnyDataModel>> mFunny_view;

    private MutableLiveData<List<FunnyDataModel>> mFunny_best;
    private MutableLiveData<List<FunnyDataModel>> mFunny_liky;
    private MutableLiveData<List<FunnyDataModel>> mFunny_subMenu;


    //Constructor
    private FunnyApiClient() {
        mFunny_best = new MutableLiveData<>();
        mFunny_liky = new MutableLiveData<>();
        mFunny_view = new MutableLiveData<>();
        mFunny_subMenu = new MutableLiveData<>();

    }

    public static FunnyApiClient getInstance() {
        if (funnyApiClient==null) {
            funnyApiClient = new FunnyApiClient();
        }
        return funnyApiClient;
    }

    //liveData return Mutable content of funny_best
    public LiveData<List<FunnyDataModel>> getFunny_best() {
        return mFunny_best;
    }
    public LiveData<List<FunnyDataModel>> getFunny_view() {return mFunny_view;}
    public LiveData<List<FunnyDataModel>> getFunny_liky() {return mFunny_liky; }


    public LiveData<List<FunnyDataModel>> getFunny_subMenu() {
        return mFunny_subMenu;
    }

    private FunnyBest_Runnable funnyBest_runnable;
    private FunnyView_Runnable funnyView_runnable;
    private FunnyLiky_Runnable funnyLiky_runnable;
    private FunnySubMenu_Runnable funnySubMenu_runnable;


    //method for request from host to get data and post in the livedata
    public void requestFunny_best() {

        if (funnyBest_runnable!=null) {
            funnyBest_runnable = null;
        }
        funnyBest_runnable = new FunnyBest_Runnable();

        final Future myHandler = AppExecuter.getAppExecuter().networkIo().submit(funnyBest_runnable);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3, TimeUnit.MINUTES);
    }

    public void requestFunny_view() {
        if (funnyView_runnable!=null) {
            funnyView_runnable = null;
        }

        funnyView_runnable = new FunnyView_Runnable();

       final Future handlerView = AppExecuter.getAppExecuter().networkIo().submit(funnyView_runnable);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handlerView.cancel(true);
            }
        }, 3, TimeUnit.MINUTES);

    }

    public void requestFunny_liky() {
        if (funnyLiky_runnable!=null) {
            funnyView_runnable = null;
        }

        funnyLiky_runnable = new FunnyLiky_Runnable();

        final Future handlerView = AppExecuter.getAppExecuter().networkIo().submit(funnyLiky_runnable);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handlerView.cancel(true);
            }
        }, 3, TimeUnit.MINUTES);

    }

    public void requestFunny_subMenu(String id_SubMenu) {


        if (funnySubMenu_runnable!=null) {
            funnySubMenu_runnable = null;
        }

        funnySubMenu_runnable = new FunnySubMenu_Runnable(id_SubMenu);

        final Future myHandler2 = AppExecuter.getAppExecuter().networkIo().submit(funnySubMenu_runnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        }, 2, TimeUnit.MINUTES);
    }

    //create Runnable class for set to AppExecutor
    private class FunnyBest_Runnable implements Runnable{


        @Override
        public void run() {

            try {
                Response response = funnyResponseCall().execute();
                if (response.body()!= null){

                    List<FunnyDataModel> funnyDataModelList = new ArrayList<>(((FunnyResponse) response.body()).getFunny_best());

                    mFunny_best.postValue(funnyDataModelList);

                }else {
                    assert response.errorBody()!=null;
                    String error = response.errorBody().string();
                    Log.i("tagy", "run: " + error);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mFunny_best.postValue(null);
            }

        }


        private Call<FunnyResponse> funnyResponseCall() {
            return Service.getApiClient().getFunny_best();
        }


    }

    //Runnable class for request funny_view
    private class FunnyView_Runnable implements Runnable {

        @Override
        public void run() {

            try {
                Response response = funnyResponseCall().execute();

                if (response.body()!=null) {
                    List<FunnyDataModel> funnyDataModels = new ArrayList<>(((FunnyResponse) response.body()).getView());
                    mFunny_view.postValue(funnyDataModels);
                } else {
                    assert response.errorBody()!=null;
                    String error = response.errorBody().string();
                    Log.i("tagy", "run: " + error);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mFunny_view.postValue(null);
            }


        }

        private Call<FunnyResponse> funnyResponseCall() {
            return Service.getApiClient().getFunny_view();
        }
    }

    private class FunnyLiky_Runnable implements Runnable {

        @Override
        public void run() {

            try {
                Response response = funnyResponseCall().execute();

                if (response.body()!=null) {
                    List<FunnyDataModel> funnyDataModels = new ArrayList<>(((FunnyResponse) response.body()).getLiky());
                    mFunny_liky.postValue(funnyDataModels);
                } else {

                    assert response.errorBody()!=null;
                    String error = response.errorBody().string();
                    Log.i("tagy", "run: " + error);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mFunny_view.postValue(null);
            }


        }

        private Call<FunnyResponse> funnyResponseCall() {
            return Service.getApiClient().getFunny_liky();
        }
    }

    private class FunnySubMenu_Runnable implements Runnable {


        private final boolean canclable;
        String id_sumMenu;

        public FunnySubMenu_Runnable(String id_sumMenu) {
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

                    mFunny_subMenu.postValue(cafeModels);
                } else {
                    String error = response2.errorBody().string();
                    Log.i("tag", "run: " + error);
                }

            } catch (IOException e) {

                e.printStackTrace();
                mFunny_subMenu.postValue(null);
            }


        }


        private Call<FunnyResponse> cafeCallMethod(String id_subMenu) {
            return Service.getApiClient().getFunny(id_subMenu);

        }


    }

}
