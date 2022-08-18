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
import ir.popittv.myapplication.databinding.ItemGameBinding;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.models.GameDataModel;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private List<GameDataModel> gameDataModels;
    private Context context;

    public GameAdapter( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        ItemGameBinding binding = DataBindingUtil
                .inflate(inflater,R.layout.item_game,parent,false);
        return new GameHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Glide.with(context).load(gameDataModels.get(position).getBanner_game())
                .into(holder.binding.bannerGame);



    }

    @Override
    public int getItemCount() {
        if (gameDataModels!=null){
            return gameDataModels.size();
        }else {
            return 0;
        }
    }

    public class GameHolder extends RecyclerView.ViewHolder{
        private ItemGameBinding binding;
        public GameHolder(@NonNull ItemGameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void setData(List<GameDataModel> gameDataModels){
        this.gameDataModels=gameDataModels;
        notifyDataSetChanged();
    }
}
