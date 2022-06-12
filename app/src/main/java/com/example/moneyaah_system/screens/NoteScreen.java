package com.example.moneyaah_system.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.moneyaah_system.R;
import com.example.moneyaah_system.ViewPagerAdapter;
import com.example.moneyaah_system.fragment.ExspenseFragment;
import com.example.moneyaah_system.fragment.IncomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteScreen extends AppCompatActivity {
    private TabLayout mtabLayout;
    private ViewPager mviewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mtabLayout = findViewById(R.id.tabLayout);
        mviewPager = findViewById(R.id.viewpager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Bundle extras = this.getIntent().getExtras();
        Bundle bundle= new Bundle();;
        ExspenseFragment expenseFragment = new ExspenseFragment();
        IncomeFragment incomeFrag = new IncomeFragment();
        if( extras !=null ) {
            String getMoney = extras.getString("money");
            String getNote = extras.getString("note");
            bundle.putString("money",getMoney);
            bundle.putString("note",getNote);
            incomeFrag.setArguments(bundle);
            expenseFragment.setArguments(bundle);
        }



        viewPagerAdapter.AddFragment(incomeFrag,"INCOME");
        viewPagerAdapter.AddFragment(expenseFragment,"EXPENSE");

        mviewPager.setAdapter(viewPagerAdapter);
        mtabLayout.setupWithViewPager(mviewPager);







    }

}
