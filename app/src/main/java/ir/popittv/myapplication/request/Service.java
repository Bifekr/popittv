package ir.popittv.myapplication.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit retrofit;
    private static final Retrofit.Builder builder =new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = builder.build();
        }
        return retrofit;
    }

    public ApiClient getClient(){
        return Service.getRetrofit().create(ApiClient.class);
    }
}
