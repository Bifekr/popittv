package ir.popittv.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.AgeDataModel;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.utils.OnClickAllChannel;

public class AgeMenu_Adapter extends RecyclerView.Adapter<AgeMenu_Adapter.AgeMenu_Holder> {

    private ArrayList<AgeDataModel> ageDataModels;
   private OnClickAllChannel onClickAllChannel;
   private int row_index=0;

    public AgeMenu_Adapter(ArrayList<AgeDataModel> ageDataModels, OnClickAllChannel onClickAllChannel) {
        this.ageDataModels = ageDataModels;
        this.onClickAllChannel = onClickAllChannel;
    }

    @NonNull
    @Override
    public AgeMenu_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_age,parent,false);
        return new AgeMenu_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgeMenu_Holder holder, int position) {

        holder.tvAge.setText(ageDataModels.get(position).getAge_name());
        holder.parentAge.setBackground(ageDataModels.get(position).getParent_age());

        holder.parentAge.setOnClickListener(v -> {
            row_index=holder.getAdapterPosition();
            onClickAllChannel.onClickAge(row_index);
        });

    }

    @Override
    public int getItemCount() {
       return ageDataModels.size();

    }

    public class AgeMenu_Holder extends RecyclerView.ViewHolder{

        LinearLayout parentAge;
        TextView tvAge;


        public AgeMenu_Holder(@NonNull View itemView) {
            super(itemView);

            parentAge=itemView.findViewById(R.id.parentAge_itemAge);
            tvAge=itemView.findViewById(R.id.tvAge_itemAge);

        }
    }
}
