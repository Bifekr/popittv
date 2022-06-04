package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemVp1Frg3Binding;
import ir.popittv.myapplication.models.ModelTestVp;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ImageHolder> {

    List<ModelTestVp> modelTestVpList;
    private Context context;
    private ViewPager2 viewPager2;

    public TestAdapter(Context context, ViewPager2 viewPager2,List<ModelTestVp> modelTestVpList) {
        this.context = context;
        this.viewPager2 = viewPager2;
        this.modelTestVpList = modelTestVpList;
    }



    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemVp1Frg3Binding binding=DataBindingUtil.inflate(inflater,R.layout.item_vp1_frg3,parent,false);
        return new  ImageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

        holder.binding.ivItemVpFrg3.setImageResource(modelTestVpList.get(position).getPoster());

    }

    @Override
    public int getItemCount() {
        if (modelTestVpList!=null){
            return modelTestVpList.size();
        }else {
            return 0;
        }


    }

    public class ImageHolder extends RecyclerView.ViewHolder{


        ItemVp1Frg3Binding binding;

        public ImageHolder(@NonNull ItemVp1Frg3Binding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

}
