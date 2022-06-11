package com.example.moneyaah_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moneyaah_system.fragment.ExspenseFragment;
import com.example.moneyaah_system.fragment.IncomeFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitle = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new IncomeFragment();
            case 1:
                return new ExspenseFragment();
            default:
                return new IncomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Income";
                break;
            case 1:
                title = "Expense";
                break;

        }
        return title;
    }
    public void AddFragment (Fragment fragment, String title){
        listFragment.add(fragment);
        listTitle.add(title);
    }
}
