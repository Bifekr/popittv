package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemFrg1Rv1Binding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class Frg2Rv1_Adapter extends RecyclerView.Adapter<Frg2Rv1_Adapter.Rv2ViewHolder> {

    private List<FunnyDataModel> cafeModelList;
    private Context context;

    public Frg2Rv1_Adapter(Context context) {
        this.context = context;
    }

    public void getDataCafe(List<FunnyDataModel> cafeModelList){
        this.cafeModelList=cafeModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Rv2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemFrg1Rv1Binding binding= DataBindingUtil.inflate(inflater, R.layout.item_frg1_rv1,
                parent,false);

        return new Rv2ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Rv2ViewHolder holder, int position) {

        holder.binding.tvTitleItemFrg1Rv1.setText(cafeModelList.get(position).getTitle_en());
       /* Glide.with(context).load(cafeModelList.get(position).getPoster())
                .into(holder.binding.ivPosterItemFrg1Rv1);*/

        Picasso.get().load(cafeModelList.get(position).getPoster())
                .into(holder.binding.ivPosterItemFrg1Rv1);

    }

    @Override
    public int getItemCount() {
        if (cafeModelList!=null){
            return cafeModelList.size();
        }else {
            return 0;
        }

    }

    public class Rv2ViewHolder extends RecyclerView.ViewHolder{

        private ItemFrg1Rv1Binding binding;

        public Rv2ViewHolder(@NonNull ItemFrg1Rv1Binding binding) {
            super(binding.getRoot());

            this.binding=binding;
        }
    }
}
