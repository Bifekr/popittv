package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.UserApi;

public class UserRepository {

    //singleTone
    private static UserRepository userRepository;
    public static UserRepository getUserRepository(){
        if (userRepository==null){
            userRepository= new UserRepository();
        }
        return userRepository;
    }

    //constructor - instance userApi
    private final UserApi userApi;
    private UserRepository(){
        userApi=UserApi.getUserApi();
    }

    ////////////////////////////////////////

    public LiveData<UserDataModel> getUser(){return userApi.getUser();}
    public void request_loginUser(String phone){userApi.request_loginUser(phone);}


}
