package ir.popittv.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.models.CardItem;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.FunnyDataModel;

public class CardPagerAdapter2 extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private List<ChannelDataModel> channelDataModels;
    private float mBaseElevation;
    Context context;

    public CardPagerAdapter2(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        channelDataModels = new ArrayList<>();
        this.context=context;
    }

   public void addCardItem(ChannelDataModel item) {
       mViews.add(null);
       channelDataModels.add(item);
       notifyDataSetChanged();

   }

    /*public void setData(List<FunnyDataModel> funnyDataModels) {
        mViews.add(null);
        this.funnyDataModels = funnyDataModels;
        notifyDataSetChanged();
    }*/

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        if (channelDataModels!=null){
            return channelDataModels.size();
        }else {
            return 0;
        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_channel_profile, container, false);
        container.addView(view);
        bind(channelDataModels.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
       // mViews.set(position, null);
    }

    private void bind(ChannelDataModel item, View view) {
        ImageView itemChann_profile =  view.findViewById(R.id.itemChann_profile);
       // contentImageView.setImageResource(item.getTitle());
        Glide.with(context).load(item.getProfile_chann()).into(itemChann_profile);
    }

}
