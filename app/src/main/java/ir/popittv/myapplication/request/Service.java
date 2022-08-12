package ir.popittv.myapplication.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit retrofit;
    private static  Retrofit.Builder builder =new Retrofit.Builder()

            .baseUrl("https://pikoboom.ir/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = builder.build();
        }
        return retrofit;
    }
    private static ApiClient movieApi = getRetrofit().create(ApiClient.class);
    public static ApiClient getApiClient(){
        return movieApi;
    }
}
