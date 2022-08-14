package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
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
    private  boolean bool_like=false;
    private  boolean boo_mark=false;
    private  boolean boo_later=false;

    public FunnyAdapter(Context context,OnClickFunny onClickFunny) {
        this.context = context;
        this.onClickFunny=onClickFunny;


    }

    @NonNull
    @Override
    public FunnyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVidDefultBinding binding= DataBindingUtil
                .inflate(inflater,R.layout.item_vid_defult,parent,false);
        return new FunnyHolder(binding);
    }
    public String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FunnyHolder holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
        holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
        int like= Integer.parseInt(funnyDataModels.get(position).getLiky());
        int view= Integer.parseInt(funnyDataModels.get(position).getView());
        holder.binding.tvLikeItemVidDef.setText(prettyCount(like));
        holder.binding.tvViewItemVidDef.setText(prettyCount(view));
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
                    holder.binding.ivMarcItemDef.setBackgroundResource(R.drawable.shape_tag4);
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
                holder.binding.ivLaterItemDef.setBackgroundResource(R.drawable.shape_tag4);
                boo_later=false;
            }
        });

        //region playerActivity
        holder.binding.ivPosterItemVideo.setOnClickListener(v -> {
            int id_funny3 = funnyDataModels.get(position).getId_funny();
            int kind = funnyDataModels.get(position).getKind();
            int id_channel = funnyDataModels.get(position).getId_channel();
                if (!boo_later) {
                    int id_funny2 = funnyDataModels.get(position).getId_funny();
                    onClickFunny.onClickLater(id_funny2);
                    holder.binding.parentViewItemVidDef.setBackgroundResource(R.drawable.shape_tag2);
                    boo_later=true;
                }else {
                    int id_funny2 = funnyDataModels.get(position).getId_funny();
                    onClickFunny.onClickLater(id_funny2);
                    boo_later=false;
                }
            onClickFunny.onClickSee(id_funny3);
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("id_vid_funny",id_funny3);
            intent.putExtra("kind",kind);
            intent.putExtra("id_channel",id_channel);
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
