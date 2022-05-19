package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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

    public void getMovieApi(String query, int page) {
        mainRepository.getMovieApi(query, page);
    }

}
