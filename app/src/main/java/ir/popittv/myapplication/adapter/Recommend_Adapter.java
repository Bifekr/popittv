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
import ir.popittv.myapplication.databinding.ItemVidDefultBinding;
import ir.popittv.myapplication.databinding.ItemVidThumbBinding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class Recommend_Adapter extends RecyclerView.Adapter<Recommend_Adapter.Rv2ViewHolder> {

    private List<FunnyDataModel> funnyDataModels;
    private Context context;

    public Recommend_Adapter(Context context) {
        this.context = context;
    }

    public void setFunnyDataModels(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels =funnyDataModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Rv2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVidDefultBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_vid_defult,
                parent,false);

        return new Rv2ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Rv2ViewHolder holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
        holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
        holder.binding.tvViewItemVidDef.setText(funnyDataModels.get(position).getView());
        holder.binding.tvLikeItemVidDef.setText(funnyDataModels.get(position).getLiky());

        Glide.with(context).load(funnyDataModels.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);

        Glide.with(context).load(funnyDataModels.get(position).getProfile_chann())
                .into(holder.binding.ProfileChannelVideoThumb);



    }

    @Override
    public int getItemCount() {
        if (funnyDataModels!=null){
            return funnyDataModels.size();
        }else {
            return 0;
        }

    }

    public class Rv2ViewHolder extends RecyclerView.ViewHolder{

        private ItemVidDefultBinding binding;

        public Rv2ViewHolder(@NonNull ItemVidDefultBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
        }
    }
}
