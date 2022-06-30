package ir.popittv.myapplication.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.utils.AppExecuter;
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
    private final MutableLiveData<UserDataModel> mUser;

    //constructor
    private UserApi(){
        mUser = new MutableLiveData<UserDataModel>();
    }

    ///////////////////////////////////////////////////////////

    private LoginUser_Run loginUser_run;


    //int livedata for post data from mutable and set into object
    public LiveData<UserDataModel> getUser(){return mUser;}
    public void request_loginUser(String phone){

        if (loginUser_run!=null){
            loginUser_run=null;
        }

        loginUser_run = new LoginUser_Run(phone);

        Future handle_loginUser= AppExecuter.getAppExecuter().networkIo().submit(loginUser_run);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handle_loginUser.cancel(true);
            }
        },1, TimeUnit.MINUTES);

    }

    //Runnable class
    private class LoginUser_Run implements Runnable{

        private String phone;
        private boolean canclable;

        public LoginUser_Run(String phone) {
            this.phone = phone;
            canclable=false;
        }

        @Override
        public void run() {
            try {
                if (canclable){
                    return;
                }
                Response response =call(phone).execute();
                if (response.isSuccessful()){
                    assert response.body()!=null;
                    UserDataModel userDataModel=(UserDataModel) response.body();
                    mUser.postValue(userDataModel);
                }else {
                    mUser.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUser.postValue(null);
            }
        }

        //method for response Api
        private Call<UserDataModel> call(String phone){
            return Service.getApiClient().userLogin(phone);
        }

    }





}
