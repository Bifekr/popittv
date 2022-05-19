package ir.popittv.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private List<MovieModel> movie;

    public List<MovieModel> getMovie() {
        return movie;
    }
}
