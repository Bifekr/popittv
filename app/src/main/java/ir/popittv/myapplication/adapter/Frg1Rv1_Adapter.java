package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemVidThumbBinding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class Frg1Rv1_Adapter extends RecyclerView.Adapter<Frg1Rv1_Adapter.Frg1Rv1_ViewHolder> {



    List<FunnyDataModel> movieModelList;
    Context context;

    public Frg1Rv1_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Frg1Rv1_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frg1_rv1,parent,false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVidThumbBinding binding= DataBindingUtil.inflate(inflater,R.layout.item_vid_thumb,parent,false);

        return new Frg1Rv1_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Frg1Rv1_ViewHolder holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(movieModelList.get(position).getTitle_fa());
        holder.binding.titleEnVideoItemVideoThumb.setText(movieModelList.get(position).getTitle_en());
        Glide.with(context).load(movieModelList.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);

      /*  holder.tv_title.setText(movieModelList.get(position).getTitle());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieModelList.get(position).getPoster_path())
                .into(holder.iv_poster);*/

    }

    @Override
    public int getItemCount() {
        if (movieModelList!=null) {
            return movieModelList.size();
        } else
            return 0;

    }

    public class Frg1Rv1_ViewHolder extends RecyclerView.ViewHolder{

        private ItemVidThumbBinding binding;
      //  ImageView iv_poster;
       // TextView tv_title;


        public Frg1Rv1_ViewHolder(@NonNull ItemVidThumbBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
           // iv_poster=itemView.findViewById(R.id.ivPoster_itemFrg1_rv1);
           // tv_title=itemView.findViewById(R.id.tvTitle_itemFrg1_rv1);



        }
    }

    public void setData(List<FunnyDataModel> movieModelList){
    this.movieModelList=movieModelList;
    notifyDataSetChanged();
    }


}
