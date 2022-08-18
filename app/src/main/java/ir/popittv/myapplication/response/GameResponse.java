package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.GameDataModel;
import ir.popittv.myapplication.models.PosterDataModel;

public class GameResponse {
    private List<GameDataModel> game;

    public List<GameDataModel> getGame() {
        return game;
    }

    public List<PosterDataModel> game_poster;

    public List<PosterDataModel> getGame_poster() {
        return game_poster;
    }
}
