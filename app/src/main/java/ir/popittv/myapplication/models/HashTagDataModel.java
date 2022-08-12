package ir.popittv.myapplication.models;

import android.graphics.drawable.Drawable;

public class HashTagDataModel {

    private String titleTagEn;
    private String titleTagFa;
    private int iconTag;
    private Drawable backgroundTag;

    public HashTagDataModel(String titleTagEn, int iconTag, Drawable backgroundTag,String titleTagFa) {
        this.titleTagEn = titleTagEn;
        this.titleTagFa = titleTagFa;
        this.iconTag = iconTag;
        this.backgroundTag = backgroundTag;
    }

    public String getTitleTagFa() {
        return titleTagFa;
    }

    public String getTitleTagEn() {
        return titleTagEn;
    }

    public int getIconTag() {
        return iconTag;
    }

    public Drawable getBackgroundTag() {
        return backgroundTag;
    }
}
