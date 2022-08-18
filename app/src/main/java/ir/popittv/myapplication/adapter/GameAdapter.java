package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.activity.GameDetailActivity;
import ir.popittv.myapplication.databinding.ItemGameBinding;
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

        GameDataModel gameDataModel= gameDataModels.get(position);
        int id_game = gameDataModel.getId_game();
        String title_en = gameDataModel.getTitle_en();
        String title_fa = gameDataModel.getTitle_fa();
        String icon = gameDataModel.getIcon();
        String banner_game = gameDataModel.getBanner_game();
        String trailer = gameDataModel.getTrailer();
        String apk = gameDataModel.getApk();
        String descrip = gameDataModel.getDescrip();
        String star = gameDataModel.getStar();
        String download = gameDataModel.getDownload();








        holder.binding.tvViewItemVidDef.setText(star);
        holder.binding.tvLikeItemVidDef.setText(download);
        holder.binding.titleFaVideoItemVideoThumb.setText(title_fa);
        holder.binding.titleEnVideoItemVideoThumb.setText(title_en);
        Glide.with(context).load(banner_game)
                .into(holder.binding.ivPosterItemVideo);
        Glide.with(context).load(icon)
                .into(holder.binding.ProfileChannelVideoThumb);

        holder.binding.parentPosterItemDef.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("id_game",id_game);
            intent.putExtra("title_en",title_en);
            intent.putExtra("title_fa",title_fa);
            intent.putExtra("icon",icon);
            intent.putExtra("banner_game",banner_game);
            intent.putExtra("trailer",trailer);
            intent.putExtra("apk",apk);
            intent.putExtra("descrip",descrip);
            intent.putExtra("star",star);
            intent.putExtra("download",download);
            context.startActivity(intent);


        });




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
