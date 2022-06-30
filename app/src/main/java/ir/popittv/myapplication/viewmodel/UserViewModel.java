package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private UserViewModel(){
        userRepository=UserRepository.getUserRepository();
    }

    ///////////////////////////////////////////

    public LiveData<UserDataModel> getUser(){return userRepository.getUser();}
    public void request_loginUser(String phone){userRepository.request_loginUser(phone);}

}
