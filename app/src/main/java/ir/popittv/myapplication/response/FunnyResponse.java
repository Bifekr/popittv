package ir.popittv.myapplication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;

public class FunnyResponse {


    private List<FunnyDataModel> funny;

    public List<FunnyDataModel> getFunny_all() {
        return funny;
    }

    private List<FunnyDataModel> view;
    public List<FunnyDataModel> getFunny_view() {
        return view;
    }
}
