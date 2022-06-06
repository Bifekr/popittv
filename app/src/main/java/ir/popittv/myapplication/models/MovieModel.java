package ir.popittv.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable{

    @SerializedName("title_en")
    @Expose
        private String title;
    @SerializedName("poster")
    @Expose
        private String poster_path;
        private int id;
        private String release_date;
        private float vote_average;
        private String overview;
        private int runtime;

        public MovieModel(String title, String poster_path, int id, String release_date, float vote_average, String overview
                ,int runtime) {
            this.title = title;
            this.poster_path = poster_path;
            this.id = id;
            this.release_date = release_date;
            this.vote_average = vote_average;
            this.overview = overview;
            this.runtime=runtime;
        }

        protected MovieModel(Parcel in) {
            title = in.readString();
            poster_path = in.readString();
            id = in.readInt();
            release_date = in.readString();
            vote_average = in.readFloat();
            overview = in.readString();
            runtime=in.readInt();

        }

        public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
            @Override
            public MovieModel createFromParcel(Parcel in) {
                return new MovieModel(in);
            }

            @Override
            public MovieModel[] newArray(int size) {
                return new MovieModel[size];
            }
        };

        public  int getRuntime(){return runtime;}

        public String getTitle() {
            return title;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public int getId() {
            return id;
        }

        public String getRelease_date() {
            return release_date;
        }

        public float getVote_average() {
            return vote_average;
        }

        public String getOverview() {
            return overview;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(poster_path);
            dest.writeInt(id);
            dest.writeString(release_date);
            dest.writeFloat(vote_average);
            dest.writeString(overview);
            dest.writeInt(runtime);
        }


}
