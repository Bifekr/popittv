package ir.popittv.myapplication.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.activity.DetailActivity;
import ir.popittv.myapplication.databinding.ItemChannelAllBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.utils.OnClickAllChannel;

public class AllChannel_Adapter extends RecyclerView.Adapter<AllChannel_Adapter.AllChannel_Holder> {

    private List<ChannelDataModel> channelDataModels;
    private final Context context;
    private final Activity activity;
    private final OnClickAllChannel onClickDetailActivity;
    private int row_index;

    public AllChannel_Adapter(Context context,OnClickAllChannel onClickDetailActivity,Activity activity) {
        this.context = context;
        this.onClickDetailActivity=onClickDetailActivity;
        this.activity=activity;
    }
    public void setChannelDataModels(List<ChannelDataModel> channelDataModels){
        this.channelDataModels=channelDataModels;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AllChannel_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemChannelAllBinding binding= DataBindingUtil
                .inflate(inflater,R.layout.item_channel_all,parent,false);

        return new AllChannel_Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllChannel_Holder holder, int position) {


        holder.binding.tvSubAllChannel.setText(channelDataModels.get(position).getFollowers());
        holder.binding.tvAge2AllChannel.setText(channelDataModels.get(position).getAge_name());
        holder.binding.titleFaItemAllChannel.setText(channelDataModels.get(position).getName_chan_fa());
        holder.binding.titleEnItemAllChannel.setText(channelDataModels.get(position).getName_chan_en());

        Glide.with(context).load(channelDataModels.get(position).getBanner_chann())
                .into(holder.binding.ivBannerItemChannelAll);
        Glide.with(context).load(channelDataModels.get(position).getProfile_chann())
                .into(holder.binding.ivProfileItemAllChan);

        holder.binding.ivBannerItemChannelAll.setOnClickListener(v -> {
            row_index=channelDataModels.get(position).getId_channel();
            int kind=channelDataModels.get(position).getKind();
            onClickDetailActivity.onClickDetailChannel(row_index);

            Intent intent=new Intent(context, DetailActivity.class);
            intent.putExtra("id_channel_single",row_index);
            intent.putExtra("kind",kind);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View,String>(holder.binding.ivBannerItemChannelAll,"banner_channel");
            pairs[1] = new Pair<View,String>(holder.binding.parentChannelProfileItemAllChan,"cardProfile_channel");
         //   pairs[2] = new Pair<View,String>(holder.binding.parentTitleItemAllChannel,"parentTitle_channel");
            ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(activity,pairs);
            context.startActivity(intent,activityOptions.toBundle());

        });

    }

    @Override
    public int getItemCount() {
        if (channelDataModels!=null){
            return channelDataModels.size();
        }else {
            return 0;
        }
    }

    public static class AllChannel_Holder extends RecyclerView.ViewHolder{

        private final ItemChannelAllBinding binding;

        public AllChannel_Holder(@NonNull ItemChannelAllBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
