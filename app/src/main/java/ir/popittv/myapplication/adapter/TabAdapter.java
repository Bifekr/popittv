package ir.popittv.myapplication.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ir.popittv.myapplication.ui.FragmentMain1;
import ir.popittv.myapplication.ui.FragmentMain2;
import ir.popittv.myapplication.ui.FragmentMain3;
import ir.popittv.myapplication.ui.FragmentMain4;
import ir.popittv.myapplication.ui.FragmentMain5;
import ir.popittv.myapplication.ui.FragmentMain6;
import ir.popittv.myapplication.ui.FragmentMain7;

public class TabAdapter extends FragmentStateAdapter {

    private int tabCount;

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int tabCount) {
        super(fragmentManager, lifecycle);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0 :
                return new FragmentMain1();
            case 1 :
                return new FragmentMain2();
            case 2 :
                return new FragmentMain3();
            case 3 :
                return new FragmentMain4();
            case 4:
                return new FragmentMain5();
            case 5 :
                return new FragmentMain6();
            case 6 :
                return new FragmentMain7();
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
