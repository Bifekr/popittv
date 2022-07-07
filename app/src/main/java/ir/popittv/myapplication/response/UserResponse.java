package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;

public class UserResponse {

    private List<FunnyDataModel> userSub;
    private List<FunnyDataModel> userSave;
    private List<FunnyDataModel> userLater;
    private List<FunnyDataModel> userSee;
    private List<FunnyDataModel> userLike;

    public List<FunnyDataModel> getUserSub() { return userSub;}

    public List<FunnyDataModel> getUserSave() {
        return userSave;
    }

    public List<FunnyDataModel> getUserLater() {
        return userLater;
    }

    public List<FunnyDataModel> getUserSee() {
        return userSee;
    }

    public List<FunnyDataModel> getUserLike() { return userLike;}
}
