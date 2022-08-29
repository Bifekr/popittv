package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.HashTagDataModel;
import ir.popittv.myapplication.utils.OnClickFrg1;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {

    private List<HashTagDataModel> tagDataModels;
    private int row_index=0;
    private OnClickFrg1 onClickFrg1;

    public TagAdapter(OnClickFrg1 onClickFrg1) {

        this.onClickFrg1=onClickFrg1;
    }
    public void setData(List<HashTagDataModel> tagDataModels){
        this.tagDataModels=tagDataModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag,parent,false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        holder.titleTagEn.setText(tagDataModels.get(position).getTag_title_en());
        holder.titleTagFa.setText(tagDataModels.get(position).getTag_title_fa());
      //  Toast.makeText(holder.parentTag.getContext(), ""+tagDataModels.get(position).getTag_title_fa(), Toast.LENGTH_SHORT).show();
        Glide.with(holder.parentItemTag.getContext()).load(tagDataModels.get(position).getTag_pic())
                .into(holder.iconTag);
       // holder.parentTag.setBackground(tagDataModels.get(position).getBackgroundTag());
        holder.parentItemTag.setOnClickListener(v->{
            row_index= holder.getAdapterPosition();
            onClickFrg1.onMenuClick(row_index);
        });

    }

    @Override
    public int getItemCount() {
        if (tagDataModels!=null) {
            return tagDataModels.size();
        }else {
            return 0;
        }
    }

    public class TagHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentItemTag;
        RelativeLayout parentTag;
        TextView titleTagEn;
        TextView titleTagFa;
        ImageView iconTag;

        public TagHolder(@NonNull View itemView) {
            super(itemView);
            parentItemTag=itemView.findViewById(R.id.parentItemTag);
            parentTag=itemView.findViewById(R.id.tagParent_itemTag);
            titleTagEn =itemView.findViewById(R.id.titleEn_tag);
            titleTagFa =itemView.findViewById(R.id.titleFa_tag);
            iconTag=itemView.findViewById(R.id.tagImage_itemTag);

        }
    }
}
