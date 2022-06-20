package ir.popittv.myapplication.request;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.response.ChannelResponse;
import ir.popittv.myapplication.utils.AppExecuter;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.response.FunnyResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MainApiClient {



    private static MainApiClient mainApiClient;


    private ChannelRunnable channelRunnable;
    private ChannelDetail_Run channelDetail_run;


    private final MutableLiveData<List<ChannelDataModel>> mChannel;
    private final MutableLiveData<ChannelDataModel> mDetailChannel;


    private MainApiClient() {

        mChannel = new MutableLiveData<>();
        mDetailChannel = new MutableLiveData<>();
    }

    public static MainApiClient getInstance() {
        if (mainApiClient==null) {
            mainApiClient = new MainApiClient();
        }
        return mainApiClient;
    }


    public LiveData<List<ChannelDataModel>> getChannel() {
        return mChannel;
    }
    public LiveData<ChannelDataModel> getChannel_detail(){return  mDetailChannel;}





    //retrieve data from dataBase
    public void requestChannel_kind() {


        if (channelRunnable!=null) {
            channelRunnable = null;
        }

        channelRunnable = new ChannelRunnable();

        final Future myHandler2 = AppExecuter.getAppExecuter().networkIo().submit(channelRunnable);

        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        }, 2, TimeUnit.MINUTES);
    }
    private class ChannelRunnable implements Runnable {


        private final boolean canclable;


        public ChannelRunnable() {
            canclable = false;
        }


        @Override
        public void run() {
            try {
                if (canclable)
                    return;

                Response response2 = channelResponseCall().execute();

                if (response2.code()==200) {
                    assert response2.body()!=null;
                    List<ChannelDataModel> channelDataModels = new ArrayList<>(((ChannelResponse) response2.body()).getChannel());

                    mChannel.postValue(channelDataModels);
                } else {
                    String error = response2.errorBody().string();
                    Log.i("tag", "run: " + error);
                }

            } catch (IOException e) {

                e.printStackTrace();
                mChannel.postValue(null);
            }


        }


        private Call<ChannelResponse> channelResponseCall() {
            return Service.getApiClient().getChannel(1);

        }


    }

    //retrieve detail Channel from dataBase
    public void requestChannel_detail(int id_channel){

        if (channelDetail_run!=null){
            channelDetail_run=null;
        }

        channelDetail_run = new ChannelDetail_Run(id_channel);
        Future detailHandler = AppExecuter.getAppExecuter().networkIo().submit(channelDetail_run);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                detailHandler.cancel(true);
            }
        },2,TimeUnit.MINUTES);
    }
    private class ChannelDetail_Run implements Runnable{

        int id_channel;

        public ChannelDetail_Run(int id_channel) {
            this.id_channel = id_channel;
        }

        @Override
        public void run() {

            try {
                Response response= call(id_channel).execute();
                if (response.isSuccessful()){
                    ChannelDataModel channelDataModel=(ChannelDataModel) response.body();
                    mDetailChannel.postValue(channelDataModel);
                }else {
                   String errorResponse= response.errorBody().string();
                   Log.e("tag","error"+errorResponse);
                }



            } catch (IOException e) {
                e.printStackTrace();
                mDetailChannel.postValue(null);
            }


        }

        private Call<ChannelDataModel> call(int id_channel){

            return Service.getApiClient().getChannel_detail(id_channel);

        }

    }

}
