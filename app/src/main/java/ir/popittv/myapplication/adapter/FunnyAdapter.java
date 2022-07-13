package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.activity.DetailActivity;
import ir.popittv.myapplication.activity.PlayerActivity;
import ir.popittv.myapplication.databinding.ItemVidDefultBinding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFunny;

public class FunnyAdapter extends RecyclerView.Adapter<FunnyAdapter.FunnyHolder>{


    private List<FunnyDataModel> funnyDataModels;
    private Context context;
    private OnClickFunny onClickFunny;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean b_kindlink;

    public FunnyAdapter(Context context,OnClickFunny onClickFunny,boolean b_kindlink) {
        this.context = context;
        this.onClickFunny=onClickFunny;
        this.b_kindlink=b_kindlink;
        sharedPreferences= context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public FunnyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVidDefultBinding binding= DataBindingUtil
                .inflate(inflater,R.layout.item_vid_defult,parent,false);
        return new FunnyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FunnyHolder holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
        holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
        holder.binding.tvLikeItemVidDef.setText(funnyDataModels.get(position).getLiky());
        holder.binding.tvViewItemVidDef.setText(funnyDataModels.get(position).getView());
        Glide.with(context).load(funnyDataModels.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);
        Glide.with(context).load(funnyDataModels.get(position).getProfile_chann())
                .into(holder.binding.ProfileChannelVideoThumb);
        
        holder.binding.ivMarcItemDef.setOnClickListener(v -> {
            int id_funny = funnyDataModels.get(position).getId_funny();
            onClickFunny.onClickSave(id_funny);
            holder.binding.ivMarcItemDef.setBackgroundResource(R.drawable.shape_tag2);

        });

        holder.binding.parentLikeItemVidDef.setOnClickListener(v -> {
            int id_funny1 = funnyDataModels.get(position).getId_funny();
            onClickFunny.onClickLike(id_funny1);
            holder.binding.parentLikeItemVidDef.setBackgroundResource(R.drawable.shape_tag2);
        });

        holder.binding.ivLaterItemDef.setOnClickListener(v -> {
            int id_funny2 = funnyDataModels.get(position).getId_funny();
            onClickFunny.onClickLater(id_funny2);
            holder.binding.ivLaterItemDef.setBackgroundResource(R.drawable.shape_tag2);
        });

        holder.binding.ivPosterItemVideo.setOnClickListener(v -> {
            int id_funny3 = funnyDataModels.get(position).getId_funny();
            onClickFunny.onClickSee(id_funny3);
            Intent intent = new Intent(context, PlayerActivity.class);
            id_funny3 = funnyDataModels.get(position).getId_funny();
            intent.putExtra("id_vid_funny", id_funny3);
            context.startActivity(intent);

        });

        holder.binding.ProfileChannelVideoThumb.setOnClickListener(v -> {
            int id_channel=funnyDataModels.get(position).getId_channel();
            onClickFunny.onClickSub(id_channel);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id_channel", id_channel);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (funnyDataModels!=null) {
            return funnyDataModels.size();
        } else
            return 0;

    }

    public class FunnyHolder extends RecyclerView.ViewHolder{
       private ItemVidDefultBinding binding;
        public FunnyHolder(@NonNull ItemVidDefultBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }

    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }
}
