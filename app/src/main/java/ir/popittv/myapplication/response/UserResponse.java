package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.FunnyDataModel;

public class UserResponse {

    private List<FunnyDataModel> userSub;
    private List<FunnyDataModel> userBookMark;
    private List<FunnyDataModel> userWatchLater;
    private List<FunnyDataModel> userSee;

    public List<FunnyDataModel> getUserSub() {
        return userSub;
    }

    public List<FunnyDataModel> getUserBookMark() {
        return userBookMark;
    }

    public List<FunnyDataModel> getUserWatchLater() {
        return userWatchLater;
    }

    public List<FunnyDataModel> getUserSee() {
        return userSee;
    }
}
