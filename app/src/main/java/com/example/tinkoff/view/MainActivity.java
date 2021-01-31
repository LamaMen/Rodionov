package com.example.tinkoff.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tinkoff.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    public final static String[] chancels = {"latest", "top"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, GifViewerFragment.newInstance(chancels[0]), chancels[0].toUpperCase())
                .commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment = GifViewerFragment.newInstance(chancels[tab.getPosition()]);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}