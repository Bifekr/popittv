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
    private final Context context;
    private final OnClickFunny onClickFunny;
    private  boolean b_kindlink;

    private  boolean bool_like=false;
    private  boolean boo_mark=false;
    private  boolean boo_later=false;


    public FunnyAdapter(Context context,OnClickFunny onClickFunny,boolean b_kindlink) {
        this.context = context;
        this.onClickFunny=onClickFunny;
        this.b_kindlink=b_kindlink;

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
                if (!boo_mark) {
                int id_funny = funnyDataModels.get(position).getId_funny();
                onClickFunny.onClickSave(id_funny);
                holder.binding.ivMarcItemDef.setBackgroundResource(R.drawable.shape_tag2);
                boo_mark=true;
                }else {
                    int id_funny = funnyDataModels.get(position).getId_funny();
                    onClickFunny.onClickSave(id_funny);
                    holder.binding.ivMarcItemDef.setBackgroundResource(R.drawable.shape_tag1);
                    boo_mark=false;
                }
            });


        holder.binding.parentLikeItemVidDef.setOnClickListener(v -> {
            if (!bool_like){
                int id_funny1 = funnyDataModels.get(position).getId_funny();
                onClickFunny.onClickLike(id_funny1);
                holder.binding.parentLikeItemVidDef.setBackgroundResource(R.drawable.shape_tag2);
                bool_like=true;
            }else {
                int id_funny1 = funnyDataModels.get(position).getId_funny();
                onClickFunny.onClickLike(id_funny1);
                holder.binding.parentLikeItemVidDef.setBackgroundResource(R.drawable.shape_tag1);
                bool_like=false;
            }

        });

        holder.binding.ivLaterItemDef.setOnClickListener(v -> {
            if (!boo_later) {
                int id_funny2 = funnyDataModels.get(position).getId_funny();
                onClickFunny.onClickLater(id_funny2);
                holder.binding.ivLaterItemDef.setBackgroundResource(R.drawable.shape_tag2);
                boo_later=true;
            }else {
                int id_funny2 = funnyDataModels.get(position).getId_funny();
                onClickFunny.onClickLater(id_funny2);
                holder.binding.ivLaterItemDef.setBackgroundResource(R.drawable.shape_tag1);
                boo_later=false;
            }
        });


        //region playerActivity
        holder.binding.ivPosterItemVideo.setOnClickListener(v -> {
            int id_funny3 = funnyDataModels.get(position).getId_funny();
            onClickFunny.onClickSee(id_funny3);
            Intent intent = new Intent(context, PlayerActivity.class);
            id_funny3 = funnyDataModels.get(position).getId_funny();

            intent.putExtra("id_vid_funny", id_funny3);
            intent.putExtra("title_en",funnyDataModels.get(position).getTitle_en());
            intent.putExtra("title_fa",funnyDataModels.get(position).getTitle_fa());
            intent.putExtra("name_chann_fa",funnyDataModels.get(position).getName_chan_fa());
            intent.putExtra("name_chann_en",funnyDataModels.get(position).getName_chan_en());
            intent.putExtra("followers",funnyDataModels.get(position).getFollowers());
            intent.putExtra("view",funnyDataModels.get(position).getView());
            intent.putExtra("like",funnyDataModels.get(position).getLiky());
            intent.putExtra("profile_chan",funnyDataModels.get(position).getProfile_chann());
            intent.putExtra("poster",funnyDataModels.get(position).getPoster());

            if (b_kindlink) {
                String link_480=funnyDataModels.get(position).getLink_480();
                intent.putExtra("link",link_480);
            }else {
                String link_720=funnyDataModels.get(position).getLink_720();
                intent.putExtra("link",link_720);
            }
            context.startActivity(intent);

        });

        //endregion

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
