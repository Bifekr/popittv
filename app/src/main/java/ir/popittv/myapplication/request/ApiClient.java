package ir.popittv.myapplication.request;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.response.ChannelResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import ir.popittv.myapplication.response.UserResponse;
import okhttp3.ResponseBody;
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
    Call<ChannelResponse> getChannel_all(@Query("kind") int kind, @Query("age") int age);


    @GET("login.php")
    Call<ResponseBody> userLogin(@Query("phone") String phone);

    @GET("getCode.php")
    Call<UserDataModel> getUser(@Query("phone") String phone, @Query("code") String code);

    @GET("getUserSub.php")
    Call<UserResponse> getUserSub(@Query("id_user") int id_user);

    @GET("getUserSave.php")
    Call<UserResponse> getUserSave(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserLater.php")
    Call<UserResponse> getUserLater(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserLike.php")
    Call<UserResponse> getUserLike(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserSee.php")
    Call<UserResponse> getUserSee(@Query("id_user") int id_user, @Query("id_table") int kind);



}
