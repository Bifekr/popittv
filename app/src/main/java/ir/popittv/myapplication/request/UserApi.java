package ir.popittv.myapplication.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.response.UserResponse;
import ir.popittv.myapplication.utils.AppExecuter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserApi {

    private static UserApi userApi;

    //singleTone
    public static UserApi getUserApi(){
        if (userApi==null){
            userApi=new UserApi();
        }
        return userApi;
    }

    //init mutable


    private final MutableLiveData<List<FunnyDataModel>> mUserSub;
    private final MutableLiveData<List<FunnyDataModel>> mUserSave;
    private final MutableLiveData<List<FunnyDataModel>> mUserSee;
    private final MutableLiveData<List<FunnyDataModel>> mUserLater;
    private final MutableLiveData<List<FunnyDataModel>> mUserLike;

    //constructor
    private UserApi(){

        mUserSub = new MutableLiveData<>();
        mUserSave = new MutableLiveData<>();
        mUserSee = new MutableLiveData<>();
        mUserLater = new MutableLiveData<>();
        mUserLike = new MutableLiveData<>();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    private UserSub_Run userSub_run;
    private UserSave_Run userSave_run;
    private UserLike_Run userLike_run;
    private UserSee_Run userSee_run;
    private UserLater_Run userLater_run;


    //int livedata for post data from mutable and set into object

    public LiveData<List<FunnyDataModel>> getUserSub(){return mUserSub;}
    public LiveData<List<FunnyDataModel>> getUserSave(){return mUserSave;}
    public LiveData<List<FunnyDataModel>> getUserSee(){return mUserSee;}
    public LiveData<List<FunnyDataModel>> getUserLater(){return mUserLater;}
    public LiveData<List<FunnyDataModel>> getUserLike(){return mUserLike;}


    public void request_userSub(int id_user,int kind){

        if (userSub_run!=null){
            userSub_run=null;
        }

        userSub_run=new UserSub_Run(id_user,kind);

        Future handler_userSub = AppExecuter.getAppExecuter().networkIo().submit(userSub_run);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {

                handler_userSub.cancel(true);
            }
        },2,TimeUnit.MINUTES);



    }
    public void request_userSave(int id_user,int kind){
        if (userSave_run!=null){
            userSave_run=null;
        }

        userSave_run=new UserSave_Run(id_user, kind);

        Future handler_userSave=AppExecuter.getAppExecuter().networkIo().submit(userSub_run);
        AppExecuter.getAppExecuter().networkIo().schedule(() -> {
            handler_userSave.cancel(true);
        },2,TimeUnit.MINUTES);
    }
    public void request_userLike(int id_user,int kind){
        if (userLike_run!=null){
            userLike_run=null;
        }

        userLike_run = new UserLike_Run(id_user, kind);

        Future handler_userLike=AppExecuter.getAppExecuter().networkIo().submit(userLike_run);
        AppExecuter.getAppExecuter().networkIo().schedule(() -> {
            userLike_run.canclable=true;
            handler_userLike.cancel(true);
        },2,TimeUnit.MINUTES);
    }
    public void request_userSee(int id_user,int kind){
        if (userSee_run!=null){
            userSee_run=null;
        }

        userSee_run=new UserSee_Run(id_user,kind);

        Future handler4=AppExecuter.getAppExecuter().networkIo().submit(userSee_run);
        AppExecuter.getAppExecuter().networkIo().schedule(() -> {
            userLike_run.canclable=true;
            handler4.cancel(true);



        },2,TimeUnit.MINUTES);

    }
    public void request_userLater(int id_user,int kin){
        if (userLater_run!=null){
            userLater_run=null;
        }
        userLater_run=new UserLater_Run(id_user,kin);

        Future handler5=AppExecuter.getAppExecuter().networkIo().submit(userLater_run);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                userLater_run.canclable=true;
                handler5.cancel(true);
            }
        },2,TimeUnit.MINUTES);
    }



    //Runnable class

    private class UserSub_Run implements Runnable{

        private int id_user;
        private int kind;
        private boolean canclable;

        public UserSub_Run(int id_user, int kind) {
            this.id_user = id_user;
            this.kind = kind;
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }
                Response response=call_userSub(id_user,kind).execute();
                if (response.isSuccessful()){

                    assert response.body()!=null;
                    List<FunnyDataModel> funnyDataModels=new ArrayList<>(((UserResponse)response.body()).getUserSub());
                    mUserSub.postValue(funnyDataModels);

                }else {
                    assert response.errorBody()!=null;
                    mUserSub.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUserSub.postValue(null);
            }


        }

        private Call<UserResponse> call_userSub(int id_user,int kind){
            return Service.getApiClient().getUserSub(id_user,kind);
        }

    }
    private class UserSave_Run implements Runnable{

        private int id_user;
        private int kind;
        private boolean canclable;

        public UserSave_Run(int id_user, int kind) {
            this.id_user = id_user;
            this.kind = kind;
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }
                Response response2=call_userSave(id_user,kind).execute();
                assert response2.body()!=null;
                List<FunnyDataModel> funnyDataModels2=new ArrayList<>(((UserResponse)response2.body()).getUserSave());
                mUserSave.postValue(funnyDataModels2);
            } catch (IOException e) {
                e.printStackTrace();
                mUserSave.postValue(null);
            }


        }
        private Call<UserResponse> call_userSave(int id_user,int kind){
            return Service.getApiClient().getUserSave(id_user,kind);
        }

    }
    private class UserLike_Run implements Runnable{

        private int id_user;
        private int kind;
        private boolean canclable;

        public UserLike_Run(int id_user, int kind) {
            this.id_user = id_user;
            this.kind = kind;
            canclable = false;
        }

        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }
                Response response3=call_userLike(id_user,kind).execute();
                if (response3.isSuccessful()){
                    assert response3.body()!=null;
                    List<FunnyDataModel> funnyDataModels3=new ArrayList<>(((UserResponse)response3.body()).getUserLike());
                    mUserLike.postValue(funnyDataModels3);

                }else {
                    mUserLike.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUserLike.postValue(null);
            }

        }

        private Call<UserResponse> call_userLike(int id_user,int kind){
            return Service.getApiClient().getUserLike(id_user,kind);
        }
    }
    private class UserSee_Run implements Runnable{

        private int id_user;
        private int kind;
        private boolean canclable;

        public UserSee_Run(int id_user, int kind) {
            this.id_user = id_user;
            this.kind = kind;
            canclable = false;
        }
        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }

                Response response=call_userSee(id_user,kind).execute();
                if (response.isSuccessful()) {
                    assert response.body()!=null;
                    List<FunnyDataModel> funnyDataModels4 = new ArrayList<>(((UserResponse) response.body()).getUserSee());
                    mUserSee.postValue(funnyDataModels4);

                }else {
                    mUserSee.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUserSee.postValue(null);
            }

        }
        private Call<UserResponse> call_userSee(int id_user,int kind){
            return Service.getApiClient().getUserSee(id_user,kind);
        }
    }
    private class UserLater_Run implements Runnable{

        private int id_user;
        private int kind;
        private boolean canclable;

        public UserLater_Run(int id_user, int kind) {
            this.id_user = id_user;
            this.kind = kind;
            canclable = false;
        }
        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }

                Response response=call_userLater(id_user,kind).execute();
                if (response.isSuccessful()){
                    assert response.body()!=null;
                    List<FunnyDataModel> funnyDataModels5=new ArrayList<>(((UserResponse)response.body()).getUserLater());
                    mUserLater.postValue(funnyDataModels5);
                }else {
                    mUserLater.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUserLater.postValue(null);
            }

        }
        private Call<UserResponse> call_userLater(int id_user,int kind){
            return Service.getApiClient().getUserLater(id_user,kind);
        }
    }





}
