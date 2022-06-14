package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;

public class FunnyResponse {


    private List<FunnyDataModel> funny;

    public List<FunnyDataModel> getFunny() {
        return funny;
    }


    private List<FunnyDataModel> best;
    public List<FunnyDataModel> getFunny_view() {
        return best;
    }
}
