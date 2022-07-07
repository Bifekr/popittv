package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;
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

   public LiveData<List<FunnyDataModel>> getUserSub(){return userApi.getUserSub(); }
   public void request_userSub(int id_user,int kind) {userApi.request_userSub(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserSave(){return userApi.getUserSave(); }
    public void request_userSave(int id_user,int kind) {userApi.request_userSave(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserSee(){return userApi.getUserSee(); }
    public void request_userSee(int id_user,int kind) {userApi.request_userSee(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserLater(){return userApi.getUserLater(); }
    public void request_userLater(int id_user,int kind) {userApi.request_userLater(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserLike(){return userApi.getUserLike(); }
    public void request_userLike(int id_user,int kind) {userApi.request_userLike(id_user, kind);}


}
