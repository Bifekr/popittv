package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;

public class FunnyResponse {


    private List<FunnyDataModel> funny;

    public List<FunnyDataModel> getFunny() {
        return funny;
    }


    private List<FunnyDataModel> best;
    public List<FunnyDataModel> getFunny_best() {
        return best;
    }

    private List<FunnyDataModel> view;
    public List<FunnyDataModel> getView(){return view;}

    private List<FunnyDataModel> liky;
    public List<FunnyDataModel> getLiky(){return liky;}

    private List<FunnyDataModel> search;
    public List<FunnyDataModel> getSearch(){return search;}

    private FunnyDataModel single;
    public FunnyDataModel getSingle(){return single;}
}
