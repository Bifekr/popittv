package ir.popittv.myapplication.models;

import android.graphics.drawable.Drawable;

public class AgeDataModel {

    private String age_name;
    private Drawable parent_age;

    public AgeDataModel(String age_name, Drawable parent_age) {
        this.age_name = age_name;
        this.parent_age = parent_age;
    }

    public String getAge_name() {
        return age_name;
    }

    public Drawable getParent_age() {
        return parent_age;
    }
}
