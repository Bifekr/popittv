package ir.popittv.myapplication.request;

import ir.popittv.myapplication.response.ChannelResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {


    @GET("getFunny.php")
    Call<FunnyResponse> getFunny(@Query("id_subMenu") int id_subMenu);

    @GET("getFunny_best.php")
    Call<FunnyResponse> getFunny_best();

    @GET("getFunny_liky.php")
    Call<FunnyResponse> getFunny_liky();

    @GET("getFunny_view.php")
    Call<FunnyResponse> getFunny_view();
    @GET("getChannel.php")
    Call<ChannelResponse> getChannel(@Query("kind") int kin);
}
