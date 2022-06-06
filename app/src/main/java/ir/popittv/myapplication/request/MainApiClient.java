package ir.popittv.myapplication.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.AppExecuter;
import ir.popittv.myapplication.models.CafeModel;
import ir.popittv.myapplication.models.MovieModel;
import ir.popittv.myapplication.models.MovieResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MainApiClient {


    public static final String API_KEY = "4d47df412982ceebfee953c580aea342";
    private static MainApiClient mainApiClient;

    private RetrieveMovieRunnable retrieveMovieRunnable;
    private RetrieveCafeBazarRunnable cafeBazarRunnable;

    private final MutableLiveData<List<MovieModel>> mMovie;
    private final MutableLiveData<List<CafeModel>> mCafe;


    private MainApiClient() {

        mMovie = new MutableLiveData<>();
        mCafe = new MutableLiveData<>();
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

    public LiveData<List<CafeModel>> getCafeBazar() {
        return mCafe;
    }


    //retrieve data from movie.db
    public void retrieveMovie(int page) {


        if (retrieveMovieRunnable!=null) {
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(page);

        final Future myHandler = AppExecuter.getAppExecuter().networkIo().submit(retrieveMovieRunnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);

            }
        }, 30, TimeUnit.MINUTES);
    }
    private class RetrieveMovieRunnable implements Runnable {

        private final int page;
        private final boolean canclable;

        public RetrieveMovieRunnable(int page) {
            this.page = page;
            canclable = false;
        }


        @Override
        public void run() {


            try {
                if (canclable)
                    return;

                Response response = getMovieResponseCall(page).execute();

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

                } else {
                    String error = response.errorBody().string();
                    Log.i("tag", "run: " + error);
                }

            } catch (IOException e) {

                e.printStackTrace();
                mMovie.postValue(null);
            }


        }


        private Call<MovieResponse> getMovieResponseCall(int page) {
            return Service.getApiClient().getPopularMovies(API_KEY, page);

        }


    }


    //retrieve data from localHost
    public void retrieveCafe() {


        if (cafeBazarRunnable!=null) {
            cafeBazarRunnable = null;
        }

        cafeBazarRunnable = new RetrieveCafeBazarRunnable();

        final Future myHandler2 = AppExecuter.getAppExecuter().networkIo().submit(cafeBazarRunnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        }, 2, TimeUnit.MINUTES);
    }

    private class RetrieveCafeBazarRunnable implements Runnable {


        private final boolean canclable;

        public RetrieveCafeBazarRunnable() {

            canclable = false;
        }


        @Override
        public void run() {


            try {
                if (canclable)
                    return;

                Response response2 = cafeCallMethod().execute();

                if (response2.code()==200) {
                    assert response2.body()!=null;
                    List<CafeModel> cafeModels = new ArrayList<>(((MovieResponse) response2.body()).getApp());

                    mCafe.postValue(cafeModels);
                } else {
                    String error = response2.errorBody().string();
                    Log.i("tag", "run: " + error);
                }

            } catch (IOException e) {

                e.printStackTrace();
                mCafe.postValue(null);
            }


        }


        private Call<MovieResponse> cafeCallMethod() {
            return Service.getApiClient().getCafe();

        }


    }

}
