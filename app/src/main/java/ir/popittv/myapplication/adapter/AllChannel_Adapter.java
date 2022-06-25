package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemChannelAllBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;

public class AllChannel_Adapter extends RecyclerView.Adapter<AllChannel_Adapter.AllChannel_Holder> {

    private List<ChannelDataModel> channelDataModels;
    private Context context;

    public AllChannel_Adapter(Context context) {
        this.context = context;
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

        Glide.with(context).load(channelDataModels.get(position).getBanner_chann())
                .into(holder.binding.ivBannerItemChannelAll);
        Glide.with(context).load(channelDataModels.get(position).getProfile_chann())
                .into(holder.binding.ivProfileItemAllChan);
        holder.binding.tvSubAllChannel.setText(channelDataModels.get(position).getFollowers());
        holder.binding.tvAgeAllChannel.setText(channelDataModels.get(position).getAge_name());
        holder.binding.titleFaItemAllChannel.setText(channelDataModels.get(position).getName_chan_fa());
        holder.binding.titleEnItemAllChannel.setText(channelDataModels.get(position).getName_chan_en());

    }

    @Override
    public int getItemCount() {
        if (channelDataModels!=null){
            return channelDataModels.size();
        }else {
            return 0;
        }
    }

    public class AllChannel_Holder extends RecyclerView.ViewHolder{

        private ItemChannelAllBinding binding;

        public AllChannel_Holder(@NonNull ItemChannelAllBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
