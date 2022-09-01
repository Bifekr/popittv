package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.HashTagDataModel;
import ir.popittv.myapplication.utils.OnClickFrg1;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {

    private List<HashTagDataModel> tagDataModels;
    private int row_index=-1;
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
        int id_tag=tagDataModels.get(position).getId_subMenu_funny();
      //  Toast.makeText(holder.parentTag.getContext(), ""+tagDataModels.get(position).getTag_title_fa(), Toast.LENGTH_SHORT).show();
        Glide.with(holder.parentItemTag.getContext()).load(tagDataModels.get(position).getTag_pic())
                .into(holder.iconTag);
       // holder.parentTag.setBackground(tagDataModels.get(position).getBackgroundTag());
        holder.parentItemTag.setOnClickListener(v->{
            row_index= holder.getAdapterPosition();
            onClickFrg1.onMenuClick(id_tag);

            notifyDataSetChanged();
        });

        if (row_index==position){

            holder.parentTag.setBackgroundResource(R.color.popit_yellow);
            //   holder.parentTag.setCardElevation(28.8f);
            Toast.makeText(holder.parentItemTag.getContext(), "ro index"+row_index, Toast.LENGTH_SHORT).show();
        }else {
          //  holder.parentTag.setBackgroundResource(R.color.reply_blue_50_alpha_060);
            GradientDrawable drawable1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xffeff400, 0xffaff600});
            GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xFF03A9F4, 0xFF90CAF9});
            GradientDrawable drawable3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xFFFFEB3B, 0xffaaf400});
            GradientDrawable drawable4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xFF7ADCCF, 0xFF80CBC4});
            GradientDrawable drawable5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{0xf469a9, 0xFFF48FB1});
            ArrayList<HashTagDataModel> tagList = new ArrayList<>();
            tagList.add(new HashTagDataModel( drawable1));
            tagList.add(new HashTagDataModel( drawable2));
            tagList.add(new HashTagDataModel(drawable3));
            tagList.add(new HashTagDataModel(drawable4));
            tagList.add(new HashTagDataModel(drawable5));
            holder.parentTag.setBackground(tagDataModels.get(position).getBackgroundTag());


        }
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
