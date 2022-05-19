package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.request.MainApiClient;

public class MainRepository {

    private static MainRepository mainRepository;
    private final MainApiClient mainApiClient;

    private MainRepository() {
        mainApiClient = MainApiClient.getInstance();
    }

    public static MainRepository getInstance() {
        if (mainRepository==null) {
            mainRepository = new MainRepository();
        }
        return getInstance();
    }

    public LiveData<List<MovieModel>> getMovie() {
        return mainApiClient.getMovie();
    }

    public void getMovieApi(String query, int page) {
        mainApiClient.retrieveMovie(query, page);
    }
}
