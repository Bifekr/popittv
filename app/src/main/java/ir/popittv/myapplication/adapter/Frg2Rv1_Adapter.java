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

public class Frg2Rv1_Adapter extends RecyclerView.Adapter<Frg2Rv1_Adapter.Rv2ViewHolder> {

    private List<FunnyDataModel> cafeModelList;
    private Context context;

    public Frg2Rv1_Adapter(Context context) {
        this.context = context;
    }

    public void getDataCafe(List<FunnyDataModel> cafeModelList){
        this.cafeModelList=cafeModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Rv2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVidThumbBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_vid_thumb,
                parent,false);

        return new Rv2ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Rv2ViewHolder holder, int position) {

        holder.binding.titleFaVideoItemVideoThumb.setText(cafeModelList.get(position).getTitle_en());
        Glide.with(context).load(cafeModelList.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);



    }

    @Override
    public int getItemCount() {
        if (cafeModelList!=null){
            return cafeModelList.size();
        }else {
            return 0;
        }

    }

    public class Rv2ViewHolder extends RecyclerView.ViewHolder{

        private ItemVidThumbBinding binding;

        public Rv2ViewHolder(@NonNull ItemVidThumbBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
        }
    }
}
