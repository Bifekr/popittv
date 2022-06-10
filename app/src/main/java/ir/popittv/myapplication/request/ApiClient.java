package ir.popittv.myapplication.request;

import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {


    @GET("getFunny.php")
    Call<FunnyResponse> getFunny_all();

    @GET("getFunny_best.php")
    Call<FunnyResponse> getFunny_best();
}
