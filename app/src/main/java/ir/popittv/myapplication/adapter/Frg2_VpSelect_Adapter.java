package ir.popittv.myapplication.adapter;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ContainerSelectVp2Binding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class Frg2_VpSelect_Adapter extends RecyclerView.Adapter<Frg2_VpSelect_Adapter.SelectedViewHolder> {


    private List<FunnyDataModel> funnyDataModelList;
    private ViewPager2 viewPager2;

    public Frg2_VpSelect_Adapter(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ContainerSelectVp2Binding binding= DataBindingUtil.inflate(inflater, R.layout.container_select_vp2,
                parent,false);
        return new SelectedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedViewHolder holder, int position) {

        Glide.with(holder.binding.getRoot()).load(funnyDataModelList.get(position).getPoster())
                .into(holder.binding.ivContainerSelectVp2);


    }

    @Override
    public int getItemCount() {
        if (funnyDataModelList!=null){
            return funnyDataModelList.size();
        }else {
            return 0;
        }
    }


    public void setFunnyDataModelList(List<FunnyDataModel> funnyDataModelList){
        this.funnyDataModelList=funnyDataModelList;
        notifyDataSetChanged();
    }

    public class SelectedViewHolder extends RecyclerView.ViewHolder{

       private ContainerSelectVp2Binding binding;

        public SelectedViewHolder(@NonNull ContainerSelectVp2Binding binding) {
            super(binding.getRoot());

            this.binding=binding;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            funnyDataModelList.addAll(funnyDataModelList);
            notifyDataSetChanged();
        }
    };
}
