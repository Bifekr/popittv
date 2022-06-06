package ir.popittv.myapplication.request;

import java.util.List;

import ir.popittv.myapplication.models.CafeModel;
import ir.popittv.myapplication.models.MovieResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("3/search/movie")
    Call<MovieResponse> getMovieSearchResponse(
            @Query("api_key") String  key,
            @Query("query") String query,
            @Query("page") int page);

    @GET("3/movie/popular/")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey,
                                               @Query("page") int page);

    @GET("getFunny.php")
    Call<MovieResponse> getCafe();

    @GET("getFunny_best.php")
    Call<FunnyResponse> getFunny_best();
}
