package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.popittv.myapplication.models.CafeModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.request.FunnyApiClient;
import ir.popittv.myapplication.request.MainApiClient;

public class MainRepository {

    private static MainRepository mainRepository;
    public static MainRepository getInstance() {
        if (mainRepository==null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }


    private  MainApiClient mainApiClient;
    private FunnyApiClient funnyApiClient;

    private MainRepository() {
        mainApiClient = MainApiClient.getInstance();
        funnyApiClient = FunnyApiClient.getInstance();
    }


    public LiveData<List<MovieModel>> getMovie() {
        return mainApiClient.getMovie();
    }
    public void getMovieApi( int page) {
        mainApiClient.retrieveMovie(page);
    }

    public LiveData<List<CafeModel>> getCafeBazar(){
       return mainApiClient.getCafeBazar();
    }
    public void retrieveCafe(){
        mainApiClient.retrieveCafe();
    }


    public LiveData<List<FunnyDataModel>> getFunny_best(){
        return funnyApiClient.getFunny_best();
    }
    public void requestFunny_best(){
        funnyApiClient.requestFunny_best();
    }

}
