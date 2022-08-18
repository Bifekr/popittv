package ir.popittv.myapplication.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.popittv.myapplication.models.GameDataModel;
import ir.popittv.myapplication.response.GameResponse;
import ir.popittv.myapplication.utils.AppExecuter;
import retrofit2.Call;
import retrofit2.Response;

public class GameRequest {
    private static GameRequest gameRequest;
    public static GameRequest getInstance(){
        if (gameRequest==null){
         gameRequest = new GameRequest();
        }
        return gameRequest;
    }

    private Game_Runnable game_runnable;


    private final MutableLiveData<List<GameDataModel>> mGame;


    private GameRequest(){

        mGame = new MutableLiveData<>();
    }
    public LiveData<List<GameDataModel>> getGame(){return mGame;}
    public void request_game(){
        if (game_runnable!=null){
            game_runnable=null;
        }
        game_runnable = new Game_Runnable();

        final Future game_future = AppExecuter.getAppExecuter().networkIo().submit(game_runnable);
        AppExecuter.getAppExecuter().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                game_runnable.canclable=true;
                game_future.cancel(true);
            }
        },10000, TimeUnit.MILLISECONDS);

    }
    private class Game_Runnable implements Runnable{
        private boolean canclable;
        public Game_Runnable(){
            canclable = false;
        }
        @Override
        public void run() {

            try {
                if (canclable){
                    return;
                }

                Response response = call().execute();
                if (response.isSuccessful()) {
                    assert response.body()!=null;
                    List<GameDataModel> gameDataModels = new ArrayList<>(((GameResponse) response.body()).getGame());
                    mGame.postValue(gameDataModels);
                }else {
                    mGame.postValue(null);
                }
            } catch (IOException e) {
                mGame.postValue(null);
                e.printStackTrace();
            }

        }
        private Call<GameResponse> call(){
            return Service.getApiClient().getGame();
        }
    }
}
