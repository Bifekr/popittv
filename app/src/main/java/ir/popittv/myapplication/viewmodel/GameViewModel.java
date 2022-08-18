package ir.popittv.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.popittv.myapplication.models.GameDataModel;
import ir.popittv.myapplication.repository.GameRepository;

public class GameViewModel extends ViewModel {
    private final GameRepository gameRepository;

    public GameViewModel(){
        gameRepository = GameRepository.getInstance();
    }
    public LiveData<List<GameDataModel>> getGame(){return gameRepository.getGame();}
    public void request_game (){gameRepository.request_game();}
}
