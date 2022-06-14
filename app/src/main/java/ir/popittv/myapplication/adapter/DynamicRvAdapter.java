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
import ir.popittv.myapplication.databinding.ItemVidThumbBinding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class DynamicRvAdapter extends RecyclerView.Adapter<DynamicRvAdapter.DynamicRvHoldeer>{


    private List<FunnyDataModel> funnyDataModels;
    private Context context;

    public DynamicRvAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DynamicRvHoldeer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVidThumbBinding binding= DataBindingUtil
                .inflate(inflater,R.layout.item_vid_thumb,parent,false);
        return new DynamicRvHoldeer(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicRvHoldeer holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
        holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
        Glide.with(context).load(funnyDataModels.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);

    }

    @Override
    public int getItemCount() {
        if (funnyDataModels!=null) {
            return funnyDataModels.size();
        } else
            return 0;

    }

    public class DynamicRvHoldeer extends RecyclerView.ViewHolder{
       private ItemVidThumbBinding binding;
        public DynamicRvHoldeer(@NonNull ItemVidThumbBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }

    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }
}
