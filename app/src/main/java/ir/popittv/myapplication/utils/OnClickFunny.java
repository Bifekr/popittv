package ir.popittv.myapplication.utils;

import android.util.Pair;

public interface OnClickFunny {

    void onClickSave(int id_vid);
    void onClickSee(int id_vid);
    void onClickLike(int id_vid);
    void onClickLater(int id_vid);
    void onClickSub(int id_channel);
    void onClickPlayer(int id_vid_funny,int id_channel,  int kind);
}
