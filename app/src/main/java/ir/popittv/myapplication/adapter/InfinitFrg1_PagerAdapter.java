package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.FunnyDataModel;
import ir.popittv.myapplication.utils.OnClickFunny;

public class InfinitFrg1_PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context context;
    private List<FunnyDataModel> funnyDataModels;
    private final OnClickFunny onClickFunny;
    public InfinitFrg1_PagerAdapter(Context context,OnClickFunny onClickFunny) {
        this.context = context;
        this.onClickFunny=onClickFunny;
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
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_infinit_frg1, container, false);
        ImageView imageView = view.findViewById(R.id.ivPoster_itemCoffie);
       // TextView textView = view.findViewById(R.id.titleEn_itemCofie);
      //  TextView titleFa = view.findViewById(R.id.titlefa_itemCofie);
       // TextView titleChann_en = view.findViewById(R.id.titleChannelEn_itemCofie);
        // RoundedImageView channel = view.findViewById(R.id.profileChannel_itemCofie);


        Glide.with(context).load(funnyDataModels.get(position).getPoster()).into(imageView);
      //  textView.setText(funnyDataModels.get(position).getTitle_en());
       // titleFa.setText(funnyDataModels.get(position).getTitle_fa());
      //  titleChann_en.setText(funnyDataModels.get(position).getTitle_en());

        imageView.setOnClickListener(v -> {

            int id_funny3 = funnyDataModels.get(position).getId_funny();
            int kind = funnyDataModels.get(position).getKind();
            int id_channel = funnyDataModels.get(position).getId_channel();

            onClickFunny.onClickPlayer(id_funny3,id_channel,kind);

            Toast.makeText(context, "OnClick"+id_channel, Toast.LENGTH_SHORT).show();
        });

        container.addView(view);

        return view;


    }
    public void setData(List<FunnyDataModel> funnyDataModels){
        this.funnyDataModels=funnyDataModels;
        notifyDataSetChanged();
    }

}
