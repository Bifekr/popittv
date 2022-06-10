package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ItemFrg1Rv1Binding;
import ir.popittv.myapplication.models.FunnyDataModel;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context context;
    private List<FunnyDataModel> funnyDataModels;

    public PagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        if (funnyDataModels!=null) {
            return funnyDataModels.size();
        }else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
      return   view.equals(object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view=LayoutInflater.from(context).inflate(R.layout.item_cafie,container,false);
        ImageView imageView=view.findViewById(R.id.ivPoster_itemVideo);
        TextView textView=view.findViewById(R.id.tvTitle_En_itemVideo);

        Glide.with(context).load(funnyDataModels.get(position).getPoster()).into(imageView);
        textView.setText(funnyDataModels.get(position).getTitle_en());

        container.addView(view);

        return view;


    }
    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }

}