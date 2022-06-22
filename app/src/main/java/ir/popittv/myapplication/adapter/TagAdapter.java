package ir.popittv.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.HashTagDataModel;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {

    private List<HashTagDataModel> tagList;

    public TagAdapter(List<HashTagDataModel> tagList) {
        this.tagList = tagList;
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
        holder.titleTag.setText(tagList.get(position).getTitleTag());
        holder.iconTag.setImageResource(tagList.get(position).getIconTag());
        holder.parentTag.setBackground(tagList.get(position).getBackgroundTag());

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class TagHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentTag;
        TextView titleTag;
        ImageView iconTag;

        public TagHolder(@NonNull View itemView) {
            super(itemView);

            parentTag=itemView.findViewById(R.id.tagParent_itemTag);
            titleTag=itemView.findViewById(R.id.tagTitle_itemTag);
            iconTag=itemView.findViewById(R.id.tagImage_itemTag);

        }
    }
}
