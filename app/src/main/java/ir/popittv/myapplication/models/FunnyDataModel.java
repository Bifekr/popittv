package ir.popittv.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FunnyDataModel implements Parcelable {

    private int id_funny;
    private  int id_channel;
    private int view;
    private int liky;
    private int age1;
    private int age2;
    private int age3;
    private int age4;
    private int age5;
    private String title_en;
    private String title_fa;
    private String poster;
    private String link_480;
    private String link_720;
    private String name_chan_en;
    private String name_chan_fa;
    private String profile_chann;
    private String banner_chann;
    private String followers;

    public FunnyDataModel() {
    }


    public FunnyDataModel(int id_funny, int id_channel, int view, int liky, int age1, int age2, int age3, int age4, int age5, String title_en, String title_fa, String poster, String link_480, String link_720, String name_chan_en, String name_chan_fa, String profile_chann, String banner_chann, String followers) {
        this.id_funny = id_funny;
        this.id_channel = id_channel;
        this.view = view;
        this.liky = liky;
        this.age1 = age1;
        this.age2 = age2;
        this.age3 = age3;
        this.age4 = age4;
        this.age5 = age5;
        this.title_en = title_en;
        this.title_fa = title_fa;
        this.poster = poster;
        this.link_480 = link_480;
        this.link_720 = link_720;
        this.name_chan_en = name_chan_en;
        this.name_chan_fa = name_chan_fa;
        this.profile_chann = profile_chann;
        this.banner_chann = banner_chann;
        this.followers = followers;
    }

    protected FunnyDataModel(Parcel in) {
        id_funny = in.readInt();
        id_channel = in.readInt();
        view = in.readInt();
        liky = in.readInt();
        age1 = in.readInt();
        age2 = in.readInt();
        age3 = in.readInt();
        age4 = in.readInt();
        age5 = in.readInt();
        title_en = in.readString();
        title_fa = in.readString();
        poster = in.readString();
        link_480 = in.readString();
        link_720 = in.readString();
        name_chan_en = in.readString();
        name_chan_fa = in.readString();
        profile_chann = in.readString();
        banner_chann = in.readString();
        followers = in.readString();
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

    public int getView() {
        return view;
    }

    public int getLiky() {
        return liky;
    }

    public int getAge1() {
        return age1;
    }

    public int getAge2() {
        return age2;
    }

    public int getAge3() {
        return age3;
    }

    public int getAge4() {
        return age4;
    }

    public int getAge5() {
        return age5;
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

    public String getName_chan_en() {
        return name_chan_en;
    }

    public String getName_chan_fa() {
        return name_chan_fa;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_funny);
        dest.writeInt(id_channel);
        dest.writeInt(view);
        dest.writeInt(liky);
        dest.writeInt(age1);
        dest.writeInt(age2);
        dest.writeInt(age3);
        dest.writeInt(age4);
        dest.writeInt(age5);
        dest.writeString(title_en);
        dest.writeString(title_fa);
        dest.writeString(poster);
        dest.writeString(link_480);
        dest.writeString(link_720);
        dest.writeString(name_chan_en);
        dest.writeString(name_chan_fa);
        dest.writeString(profile_chann);
        dest.writeString(banner_chann);
        dest.writeString(followers);
    }
}

