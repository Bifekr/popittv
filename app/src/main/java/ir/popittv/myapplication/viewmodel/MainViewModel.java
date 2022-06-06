package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.CafeModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.repository.MainRepository;

public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository;

    public MainViewModel() {
        mainRepository = MainRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovie() {
        return mainRepository.getMovie();
    }

    public void getMovieApi( int page) {
        mainRepository.getMovieApi(page);
    }

    public LiveData<List<CafeModel>> getCafe(){return mainRepository.getCafeBazar();}
    public void retrieveCafe(){mainRepository.retrieveCafe();}

    public LiveData<List<FunnyDataModel>> getFunny_best(){
        return mainRepository.getFunny_best();
    }

    public void requestFunny_best(){
        mainRepository.requestFunny_best();
    }
}
