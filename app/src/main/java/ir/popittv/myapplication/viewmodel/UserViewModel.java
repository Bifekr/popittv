package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    public UserViewModel(){
        userRepository=UserRepository.getUserRepository();
    }

    ///////////////////////////////////////////

    public LiveData<List<FunnyDataModel>> getUserSub(){return userRepository.getUserSub(); }
    public void request_userSub(int id_user,int kind) {userRepository.request_userSub(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserSave(){return userRepository.getUserSave(); }
    public void request_userSave(int id_user,int kind) {userRepository.request_userSave(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserSee(){return userRepository.getUserSee(); }
    public void request_userSee(int id_user,int kind) {userRepository.request_userSee(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserLater(){return userRepository.getUserLater(); }
    public void request_userLater(int id_user,int kind) {userRepository.request_userLater(id_user, kind);}

    public LiveData<List<FunnyDataModel>> getUserLike(){return userRepository.getUserLike(); }
    public void request_userLike(int id_user,int kind) {userRepository.request_userLike(id_user, kind);}

}
