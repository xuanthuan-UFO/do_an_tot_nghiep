package com.xuanthuan.nutritionforcow.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapterBottomNavi  extends FragmentPagerAdapter {
    ArrayList<Fragment> arrayList = new ArrayList<>();

    public ViewPagerAdapterBottomNavi(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fm){
        arrayList.add(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
