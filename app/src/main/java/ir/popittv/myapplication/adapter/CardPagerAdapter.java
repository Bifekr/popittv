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
import ir.popittv.myapplication.models.FunnyDataModel;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private List<FunnyDataModel> funnyDataModels;
    private float mBaseElevation;
    Context context;

    public CardPagerAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        funnyDataModels = new ArrayList<>();
        this.context=context;
    }

   public void addCardItem(FunnyDataModel item) {
       mViews.add(null);
       funnyDataModels.add(item);
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
        if (funnyDataModels!=null){
            return funnyDataModels.size();
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
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(funnyDataModels.get(position), view);
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

    private void bind(FunnyDataModel item, View view) {
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        ImageView contentImageView =  view.findViewById(R.id.contentImageView);
        titleTextView.setText(item.getTitle_en());
       // contentImageView.setImageResource(item.getTitle());
        Glide.with(context).load(item.getPoster()).into(contentImageView);
    }

}
