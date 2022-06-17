package ir.popittv.myapplication.request;

import android.util.Log;

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


    private final MutableLiveData<List<ChannelDataModel>> mChannel;


    private MainApiClient() {

        mChannel = new MutableLiveData<>();
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





    //retrieve data from localHost
    public void requestChannel() {


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
            return Service.getApiClient().getChannel();

        }


    }

}
