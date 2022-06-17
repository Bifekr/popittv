package ir.popittv.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelDataModel implements Parcelable {

    private int id_channel;
    private String name_chan_en;
    private String name_chan_fa;
    private String profile_chann;
    private String banner_chann;
    private String followers;

    public ChannelDataModel() {
    }

    public ChannelDataModel(int id_channel, String name_chan_en, String name_chan_fa, String profile_chann, String banner_chann, String followers) {
        this.id_channel = id_channel;
        this.name_chan_en = name_chan_en;
        this.name_chan_fa = name_chan_fa;
        this.profile_chann = profile_chann;
        this.banner_chann = banner_chann;
        this.followers = followers;
    }



    protected ChannelDataModel(Parcel in) {
        id_channel = in.readInt();
        name_chan_en = in.readString();
        name_chan_fa = in.readString();
        profile_chann = in.readString();
        banner_chann = in.readString();
        followers = in.readString();
    }

    public static final Creator<ChannelDataModel> CREATOR = new Creator<ChannelDataModel>() {
        @Override
        public ChannelDataModel createFromParcel(Parcel in) {
            return new ChannelDataModel(in);
        }

        @Override
        public ChannelDataModel[] newArray(int size) {
            return new ChannelDataModel[size];
        }
    };

    public int getId_channel() {
        return id_channel;
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
        dest.writeInt(id_channel);
        dest.writeString(name_chan_en);
        dest.writeString(name_chan_fa);
        dest.writeString(profile_chann);
        dest.writeString(banner_chann);
        dest.writeString(followers);
    }
}
