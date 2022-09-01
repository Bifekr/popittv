package ir.popittv.myapplication.models;

import android.graphics.drawable.Drawable;

public class HashTagDataModel {

    private int id_subMenu_funny;
    private String tag_title_en;
    private String tag_title_fa;
    private String tag_pic;
    private Drawable backgroundTag;

    public HashTagDataModel(int id_subMenu_funny,String tag_title_en, String tag_pic, Drawable backgroundTag, String tag_title_fa) {
        this.id_subMenu_funny=id_subMenu_funny;
        this.tag_title_en = tag_title_en;
        this.tag_title_fa = tag_title_fa;
        this.tag_pic = tag_pic;
        this.backgroundTag = backgroundTag;
    }

    public HashTagDataModel(Drawable backgroundTag) {
        this.backgroundTag = backgroundTag;
    }

    public void setBackgroundTag(Drawable backgroundTag) {
        this.backgroundTag = backgroundTag;
    }

    public int getId_subMenu_funny() {
        return id_subMenu_funny;
    }

    public String getTag_title_fa() {
        return tag_title_fa;
    }

    public String getTag_title_en() {
        return tag_title_en;
    }

    public String getTag_pic() {
        return tag_pic;
    }

    public Drawable getBackgroundTag() {
        return backgroundTag;
    }
}
