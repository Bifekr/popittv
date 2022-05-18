package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.ViewModel;

import ir.popittv.myapplication.repository.MainRepository;

public class MainViewModel extends ViewModel {



    public MainViewModel() {
        MainRepository.getInstance();
    }

}
