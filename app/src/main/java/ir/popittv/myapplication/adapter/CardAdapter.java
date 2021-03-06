package ir.popittv.myapplication.adapter;

import androidx.cardview.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 5;

    float getBaseElevation();
    CardView getCardViewAt(int position);

    int getCount();
}
