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
import ir.popittv.myapplication.databinding.ItemSearchBinding;
import ir.popittv.myapplication.models.FunnyDataModel;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder_search> {

    private List<FunnyDataModel> funnyDataModels;
    private Context context;

    public SearchAdapter( Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder_search onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemSearchBinding binding= DataBindingUtil
                .inflate(inflater, R.layout.item_search,parent,false);
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

        private ItemSearchBinding binding;

        public ViewHolder_search(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }
}
