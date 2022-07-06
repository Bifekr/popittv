package ir.popittv.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FunnyDataModel implements Parcelable {

    private int id_funny;
    private int id_channel;
    private int id_reality;
    private int id_study;
    private int id_farsi;
    private String view;
    private String liky;
    private String title_en;
    private String title_fa;
    private String poster;
    private String link_480;
    private String link_720;
    private String profile_chann;
    private String banner_chann;
    private String followers;
    private String age_name;
    private int kind;


    protected FunnyDataModel(Parcel in) {
        id_funny = in.readInt();
        id_channel = in.readInt();
        id_reality = in.readInt();
        id_study = in.readInt();
        id_farsi = in.readInt();
        view = in.readString();
        liky = in.readString();
        title_en = in.readString();
        title_fa = in.readString();
        poster = in.readString();
        link_480 = in.readString();
        link_720 = in.readString();
        profile_chann = in.readString();
        banner_chann = in.readString();
        followers = in.readString();
        age_name = in.readString();
        kind = in.readInt();
    }

    public static final Creator<FunnyDataModel> CREATOR = new Creator<FunnyDataModel>() {
        @Override
        public FunnyDataModel createFromParcel(Parcel in) {
            return new FunnyDataModel(in);
        }

        @Override
        public FunnyDataModel[] newArray(int size) {
            return new FunnyDataModel[size];
        }
    };

    public int getId_funny() {
        return id_funny;
    }

    public int getId_channel() {
        return id_channel;
    }

    public int getId_reality() {
        return id_reality;
    }

    public int getId_study() {
        return id_study;
    }

    public int getId_farsi() {
        return id_farsi;
    }

    public String getView() {
        return view;
    }

    public String getLiky() {
        return liky;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getTitle_fa() {
        return title_fa;
    }

    public String getPoster() {
        return poster;
    }

    public String getLink_480() {
        return link_480;
    }

    public String getLink_720() {
        return link_720;
    }

    public String getProfile_chann() {
        return profile_chann;
    }

    public String getBanner_chann() {
        return banner_chann;
    }

    public String getFollowers() {
        return followers;
    }

    public String getAge_name() {
        return age_name;
    }

    public int getKind() {
        return kind;
    }

    public FunnyDataModel() {
    }

    public FunnyDataModel(int id_funny, int id_channel, int id_reality, int id_study,
                          int id_farsi, String view, String liky, String title_en,
                          String title_fa, String poster, String link_480, String link_720,
                          String profile_chann, String banner_chann, String followers,
                          String age_name, int kind) {
        this.id_funny = id_funny;
        this.id_channel = id_channel;
        this.id_reality = id_reality;
        this.id_study = id_study;
        this.id_farsi = id_farsi;
        this.view = view;
        this.liky = liky;
        this.title_en = title_en;
        this.title_fa = title_fa;
        this.poster = poster;
        this.link_480 = link_480;
        this.link_720 = link_720;
        this.profile_chann = profile_chann;
        this.banner_chann = banner_chann;
        this.followers = followers;
        this.age_name = age_name;
        this.kind = kind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_funny);
        dest.writeInt(id_channel);
        dest.writeInt(id_reality);
        dest.writeInt(id_study);
        dest.writeInt(id_farsi);
        dest.writeString(view);
        dest.writeString(liky);
        dest.writeString(title_en);
        dest.writeString(title_fa);
        dest.writeString(poster);
        dest.writeString(link_480);
        dest.writeString(link_720);
        dest.writeString(profile_chann);
        dest.writeString(banner_chann);
        dest.writeString(followers);
        dest.writeString(age_name);
        dest.writeInt(kind);
    }
}