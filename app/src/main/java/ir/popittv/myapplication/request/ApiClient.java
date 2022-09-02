package ir.popittv.myapplication.request;

import java.util.List;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.response.ChannelResponse;
import ir.popittv.myapplication.response.FunnyResponse;
import ir.popittv.myapplication.response.GameResponse;
import ir.popittv.myapplication.response.UserResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {


    @GET("getFunny.php")
    Call<FunnyResponse> getFunny(@Query("id_subMenu") int id_subMenu,@Query("kind") int kind);

    @GET("getFunny_single.php")
    Call<FunnyDataModel> getFunny_single(@Query("id_funny") int id_funny,@Query("kind") int kind);

    @GET("getFunny_best.php")
    Call<FunnyResponse> getFunny_best();
    @GET("get_best.php")
    Call<List<FunnyDataModel>> getBest(@Query("kind") int kind);
    @GET("get_best.php")
    Call<List<FunnyDataModel>> getNew_Best(@Query("kind") int kind);
    @GET("get_new_best.php")
    Call<FunnyResponse> getFunny_liky(@Query("kind") int kind);

    @GET("getFunny_view.php")
    Call<FunnyResponse> getFunny_view(@Query("kind") int kind);

    @GET("getChannel.php")
    Call<ChannelResponse> getChannel_kind(@Query("kind") int kind);

    @GET("getChannel_detail.php")
    Call<ChannelDataModel> getChannel_detail(@Query("id_channel") int id_channel,@Query("kind") int kind);

    @GET("getChannel_all.php")
    Call<ChannelResponse> getChannel_all(@Query("kind") int kind, @Query("age") int age);

    @GET("getTag.php")
    Call<FunnyResponse> getTag(@Query("kind") int kind);

    @GET("login.php")
    Call<ResponseBody> userLogin(@Query("phone") String phone);

    @GET("getCode.php")
    Call<UserDataModel> getUser(@Query("phone") String phone, @Query("code") String code);

    @GET("setPayment.php")
    Call<UserDataModel> setPayment(@Query("phone")String phone,@Query("transactionId")String transactionId,@Query("firstDate")String firstDate,@Query("expireDate")Long expireDate,@Query("amount")String amount);
    @GET("getPayment.php")
    Call<UserDataModel> getPayment(@Query("phone")String phone);

    @GET("getUserSub.php")
    Call<ChannelResponse> getUserSub(@Query("id_user") int id_user );

    @GET("getUserSave.php")
    Call<UserResponse> getUserSave(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserLater.php")
    Call<UserResponse> getUserLater(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserLike.php")
    Call<UserResponse> getUserLike(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("getUserSee.php")
    Call<UserResponse> getUserSee(@Query("id_user") int id_user, @Query("id_table") int kind);

    @GET("searchFunny.php")
    Call<FunnyResponse> searchFunny(@Query("search") String search, @Query("kind") int kind);

    @GET("userSave.php")
    Call<ResponseBody> insertUserSave(@Query("id_user") int id_user,@Query("id_vid") int id_vid, @Query("id_table") int kind);
    @GET("userSub.php")
    Call<ResponseBody> insertUserSub(@Query("id_user") int id_user,@Query("id_channel") int id_channel);
    @GET("userSee.php")
    Call<ResponseBody> insertUserSee(@Query("id_user") int id_user,@Query("id_vid") int id_vid, @Query("id_table") int kind);
    @GET("userLike.php")
    Call<ResponseBody> insertUserLike(@Query("id_user") int id_user,@Query("id_vid") int id_vid, @Query("id_table") int kind);
    @GET("userLater.php")
    Call<ResponseBody> insertUserLater(@Query("id_user") int id_user,@Query("id_vid") int id_vid, @Query("id_table") int kind);

    @GET("getGame.php")
    Call<GameResponse> getGame ();
    @GET("getGame_poster.php")
    Call<GameResponse> getPoster(@Query("id_game")int id_game);

}
