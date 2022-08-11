package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.activity.PlayerActivity;
import ir.popittv.myapplication.databinding.ItemChannelDetailBinding;
import ir.popittv.myapplication.databinding.ItemVidDefultBinding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class ChannelDetail_adapter extends RecyclerView.Adapter<ChannelDetail_adapter.DetailChannel_holder> {

    private List<FunnyDataModel> funnyDataModels;
    private Context context;
    private int id_vid_funny;

    public ChannelDetail_adapter(Context context) {
        this.context = context;
    }

    public void setFunnyDataModels(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels =funnyDataModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DetailChannel_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemChannelDetailBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_channel_detail,
                parent,false);

        return new DetailChannel_holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailChannel_holder holder, int position) {

        if(funnyDataModels!=null) {


            holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
            holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
            holder.binding.tvViewItemVidDef.setText(funnyDataModels.get(position).getView() + "");
            //  holder.binding.tvLikeItemVidDef.setText(funnyDataModels.get(position).getLiky()+"");
            Glide.with(context).load(funnyDataModels.get(position).getPoster())
                    .into(holder.binding.ivPosterItemVideo);

            holder.binding.ivPosterItemVideo.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlayerActivity.class);
                id_vid_funny = funnyDataModels.get(position).getId_funny();
                intent.putExtra("id_vid_funny", id_vid_funny);
                context.startActivity(intent);
            });

        }
    }

    @Override
    public int getItemCount() {
        if (funnyDataModels!=null){
            return funnyDataModels.size();
        }else {
            return 0;
        }
    }

    public class DetailChannel_holder extends RecyclerView.ViewHolder{

        private ItemChannelDetailBinding binding;

        public DetailChannel_holder(@NonNull ItemChannelDetailBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
        }
    }

}
