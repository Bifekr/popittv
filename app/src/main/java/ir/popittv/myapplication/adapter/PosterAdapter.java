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
import ir.popittv.myapplication.databinding.ItemPosterBinding;
import ir.popittv.myapplication.models.PosterDataModel;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterHolder> {

    private List<PosterDataModel> posterDataModelList;
    private final Context context;

    public PosterAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PosterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ItemPosterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_poster, parent, false);
        return new PosterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterHolder holder, int position) {

        Glide.with(context).load(posterDataModelList.get(position).getPoster())
                .into(holder.binding.itemPoster);

    }

    @Override
    public int getItemCount() {
        if (posterDataModelList!=null) {
            return posterDataModelList.size();
        } else {
            return 0;
        }
    }

    public void setData(List<PosterDataModel> posterDataModelList) {
        this.posterDataModelList = posterDataModelList;
        notifyDataSetChanged();
    }

    public class PosterHolder extends RecyclerView.ViewHolder {
        private final ItemPosterBinding binding;

        public PosterHolder(@NonNull ItemPosterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
