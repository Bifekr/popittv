package ir.popittv.myapplication.request;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.response.ChannelResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {


    @GET("getFunny.php")
    Call<FunnyResponse> getFunny(@Query("id_subMenu") int id_subMenu);
    @GET("getFunny_single.php")
    Call<FunnyDataModel> getFunny_single(@Query("id_funny") int id_funny);
    @GET("getFunny_best.php")
    Call<FunnyResponse> getFunny_best();
    @GET("getFunny_liky.php")
    Call<FunnyResponse> getFunny_liky();
    @GET("getFunny_view.php")
    Call<FunnyResponse> getFunny_view();

    @GET("getChannel.php")
    Call<ChannelResponse> getChannel_kind(@Query("kind") int kind);
    @GET("getChannel_detail.php")
    Call<ChannelDataModel> getChannel_detail(@Query("id_channel") int id_channel);
    @GET("getChannel_all.php")
    Call<ChannelResponse> getChannel_all(@Query("kind") int kind , @Query("age") int age);
}
