package ir.popittv.myapplication.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.popittv.myapplication.models.GameDataModel;
import ir.popittv.myapplication.models.PosterDataModel;
import ir.popittv.myapplication.request.GameRequest;

public class GameRepository {


    private static GameRepository gameRepository;
    public static GameRepository getInstance(){
        if (gameRepository == null) {
           gameRepository = new GameRepository();
        }
        return gameRepository;
    }

    private final GameRequest gameRequest;

    private GameRepository(){
        gameRequest = GameRequest.getInstance();
    }

    public LiveData<List<GameDataModel>> getGame(){return gameRequest.getGame();}
    public void request_game (){gameRequest.request_game();}

    public LiveData<List<PosterDataModel>> getPoster(){return  gameRequest.getPoster();}
    public void request_poster(int id_game){gameRequest.request_poster(id_game);}

}
