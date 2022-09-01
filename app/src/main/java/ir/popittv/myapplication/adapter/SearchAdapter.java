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
import ir.popittv.myapplication.databinding.ItemChannelDetailBinding;
import ir.popittv.myapplication.databinding.ItemSearchBinding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFunny;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder_search> {

    private List<FunnyDataModel> funnyDataModels;
    private Context context;
    private  boolean boo_later=false;
    private final OnClickFunny onClickFunny;

    public SearchAdapter( Context context,OnClickFunny onClickFunny) {
        this.onClickFunny=onClickFunny;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder_search onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemChannelDetailBinding binding= DataBindingUtil
                .inflate(inflater, R.layout.item_channel_detail,parent,false);
        return new ViewHolder_search(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_search holder, int position) {

        holder.binding.titleEnVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_en());
        holder.binding.titleFaVideoItemVideoThumb.setText(funnyDataModels.get(position).getTitle_fa());
        Glide.with(context).load(funnyDataModels.get(position).getPoster())
                .into(holder.binding.ivPosterItemVideo);
        Glide.with(context).load(funnyDataModels.get(position).getProfile_chann())
                .into(holder.binding.ProfileChannelVideoThumb);

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
            onClickFunny.onClickPlayer(id_funny3,id_channel,kind);


        });

    }

    @Override
    public int getItemCount() {
        if (funnyDataModels!=null){
            return funnyDataModels.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder_search extends RecyclerView.ViewHolder{

        private ItemChannelDetailBinding binding;

        public ViewHolder_search(@NonNull ItemChannelDetailBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }
}
