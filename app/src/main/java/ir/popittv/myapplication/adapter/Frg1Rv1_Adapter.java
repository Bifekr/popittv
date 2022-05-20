package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.MovieModel;

public class Frg1Rv1_Adapter extends RecyclerView.Adapter<Frg1Rv1_Adapter.Frg1Rv1_ViewHolder> {



    List<MovieModel> movieModelList;
    Context context;

    public Frg1Rv1_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Frg1Rv1_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frg1_rv1,parent,false);

        return new Frg1Rv1_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Frg1Rv1_ViewHolder holder, int position) {

        holder.tv_title.setText(movieModelList.get(position).getTitle());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieModelList.get(position).getPoster_path())
                .into(holder.iv_poster);

    }

    @Override
    public int getItemCount() {
        if (movieModelList!=null) {
            return movieModelList.size();
        } else
            return 0;

    }

    public class Frg1Rv1_ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_poster;
        TextView tv_title;


        public Frg1Rv1_ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_poster=itemView.findViewById(R.id.ivPoster_itemFrg1_rv1);
            tv_title=itemView.findViewById(R.id.tvTitle_itemFrg1_rv1);



        }
    }

    public void setData(List<MovieModel> movieModelList){
    this.movieModelList=movieModelList;
    notifyDataSetChanged();
    }


}
