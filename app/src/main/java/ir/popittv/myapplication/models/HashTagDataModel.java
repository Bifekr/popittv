package ir.popittv.myapplication.models;

import android.graphics.drawable.Drawable;

public class HashTagDataModel {

    private String titleTag;
    private int iconTag;
    private Drawable backgroundTag;

    public HashTagDataModel(String titleTag, int iconTag, Drawable backgroundTag) {
        this.titleTag = titleTag;
        this.iconTag = iconTag;
        this.backgroundTag = backgroundTag;
    }

    public String getTitleTag() {
        return titleTag;
    }

    public int getIconTag() {
        return iconTag;
    }

    public Drawable getBackgroundTag() {
        return backgroundTag;
    }
}
