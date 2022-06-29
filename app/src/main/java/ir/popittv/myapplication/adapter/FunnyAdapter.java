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

public class FunnyAdapter extends RecyclerView.Adapter<FunnyAdapter.FunnyHolder>{


    private List<FunnyDataModel> funnyDataModels;
    private Context context;

    public FunnyAdapter(Context context) {
        this.context = context;
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
        holder.binding.tvLikeItemVidDef.setText(funnyDataModels.get(position).getLiky()+"");
        holder.binding.tvViewItemVidDef.setText(funnyDataModels.get(position).getView()+"");
        Glide.with(context).load(funnyDataModels.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);
        Glide.with(context).load(funnyDataModels.get(position).getProfile_chann())
                .into(holder.binding.ProfileChannelVideoThumb);

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
