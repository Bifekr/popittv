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

    private final MutableLiveData<List<FunnyDataModel>> mFunny_view;

    private final MutableLiveData<List<FunnyDataModel>> mFunny_best;
    private final MutableLiveData<List<FunnyDataModel>> mFunny_liky;
    private final MutableLiveData<List<FunnyDataModel>> mFunny_subMenu;
    private final MutableLiveData <FunnyDataModel> mFunny_single;


    //Constructor
    private FunnyApiClient() {
        mFunny_best = new MutableLiveData<>();
        mFunny_liky = new MutableLiveData<>();
        mFunny_view = new MutableLiveData<>();
        mFunny_subMenu = new MutableLiveData<>();
        mFunny_single = new MutableLiveData<>();

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
    public LiveData<FunnyDataModel> getFunny_single() {return mFunny_single; }


    public LiveData<List<FunnyDataModel>> getFunny_subMenu() {
        return mFunny_subMenu;
    }

    private FunnyBest_Runnable funnyBest_runnable;
    private FunnyView_Runnable funnyView_runnable;
    private FunnyLiky_Runnable funnyLiky_runnable;
    private FunnySubMenu_Runnable funnySubMenu_runnable;
    private FunnySingle_Run funnySingle_run;


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

    public void requestFunny_subMenu(int id_SubMenu) {


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
    private class FunnyBest_Runnable implements Runnable {

        private final boolean canclable;

        public FunnyBest_Runnable() {
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable) {
                    return;
                }
                Response response = funnyResponseCall().execute();
                if (response.body()!=null) {

                    List<FunnyDataModel> funnyDataModelList = new ArrayList<>(((FunnyResponse) response.body()).getFunny_best());

                    mFunny_best.postValue(funnyDataModelList);

                } else {
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
        private final boolean canclable;

        public FunnyView_Runnable() {
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable) {
                    return;
                }
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
        private final boolean canclable;

        public FunnyLiky_Runnable() {
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable) {
                    return;
                }
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
        int id_sumMenu;

        public FunnySubMenu_Runnable(int id_sumMenu) {
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


        private Call<FunnyResponse> cafeCallMethod(int id_subMenu) {
            return Service.getApiClient().getFunny(id_subMenu);

        }


    }

    public void requestFunny_single(int id_funny){

        if (funnySingle_run!=null){
            funnySingle_run=null;
        }

        funnySingle_run = new FunnySingle_Run(id_funny);
        Future singleHandler = AppExecuter.getAppExecuter().networkIo().submit(funnySingle_run);
        AppExecuter.getAppExecuter().networkIo().schedule(() -> {
            singleHandler.cancel(true);
        },2,TimeUnit.MINUTES);
    }
    private class FunnySingle_Run implements Runnable{

        int id_funny;
        private final boolean canclable;

        public FunnySingle_Run(int id_funny) {
            this.id_funny = id_funny;
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable) {
                    return;
                }
                Response response = call(id_funny).execute();
                if (response.isSuccessful()) {
                    FunnyDataModel funnyDataModel = (FunnyDataModel) response.body();
                    mFunny_single.postValue(funnyDataModel);
                } else {
                    String errorResponse = response.errorBody().string();
                    Log.e("tag", "error" + errorResponse);
                }


            } catch (IOException e) {
                e.printStackTrace();
                mFunny_single.postValue(null);
            }


        }

        private Call<FunnyDataModel> call(int id_funny){

            return Service.getApiClient().getFunny_single(id_funny);

        }

    }

}
