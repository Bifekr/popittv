package ir.popittv.myapplication.request;

import ir.popittv.myapplication.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("3/search/movie")
    Call<MovieResponse> getMovieSearchResponse(
            @Query("api_key") String  key,
            @Query("query") String query,
            @Query("page") int page);
}
