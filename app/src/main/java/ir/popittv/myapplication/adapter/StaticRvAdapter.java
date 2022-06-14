package ir.popittv.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.StaticRvModel;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRvViewHolder>  {

    private ArrayList<StaticRvModel> items;
    int row_index = -1;

    public StaticRvAdapter(ArrayList<StaticRvModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public StaticRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_static_rv,parent,false);
        return new StaticRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvViewHolder holder, final int position) {


        holder.tvItemStatic.setText(items.get(position).getText());
        holder.ivItemStatic.setImageResource(items.get(position).getImage());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index =holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if (row_index == position){

            holder.linearLayout.setBackgroundResource(R.drawable.static_rv_select_bg);

            }else {

            holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);



        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticRvViewHolder extends RecyclerView.ViewHolder{

        TextView tvItemStatic;
        ImageView ivItemStatic;
        LinearLayout linearLayout;

        public StaticRvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemStatic=itemView.findViewById(R.id.tvItemStatic);
            ivItemStatic=itemView.findViewById(R.id.ivItemStatic);
            linearLayout=itemView.findViewById(R.id.linear);

        }
    }
}
