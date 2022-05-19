package ir.popittv.myapplication.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.AppExecuter;
import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.models.MovieResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MainApiClient {


    public static final String API_KEY = "4d47df412982ceebfee953c580aea342";
    private static MainApiClient mainApiClient;
    private RetrieveMovieRunnable retrieveMovieRunnable;
    private MutableLiveData<List<MovieModel>> mMovie;


    private MainApiClient() {

        mMovie = new MutableLiveData<>();
    }

    public static MainApiClient getInstance() {
        if (mainApiClient==null) {
            mainApiClient = new MainApiClient();
        }
        return mainApiClient;
    }

    public LiveData<List<MovieModel>> getMovie() {
        return mMovie;
    }


    public void retrieveMovie(String query, int page) {


        if (retrieveMovieRunnable!=null) {
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(query, page);

        final Future myHandler = AppExecuter.getAppExecuter().networkIo().submit(retrieveMovieRunnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMovieRunnable implements Runnable {

        private String query;
        private int page;
        private boolean canclable;

        public RetrieveMovieRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            canclable = false;
        }


        @Override
        public void run() {


            try {
                Response response = getMovieResponseCall(query, page).execute();
                if (canclable) {
                    return;
                }

                if (response.code()==200) {
                    assert response.body()!=null;
                    List<MovieModel> movieModels = new ArrayList<>(((MovieResponse) response.body()).getMovie());
                    if (page==1) {

                        mMovie.postValue(movieModels);
                    } else {
                        List<MovieModel> currentMovies = mMovie.getValue();
                        currentMovies.addAll(movieModels);
                        mMovie.postValue(currentMovies);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovie.postValue(null);
            }


        }


        private Call<MovieResponse> getMovieResponseCall(String query, int page) {
            return Service.getApiClient().getMovieSearchResponse(API_KEY, query, page);

        }


    }


}
