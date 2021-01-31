package com.example.tinkoff.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tinkoff.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    // Список каналов, из которых будут запрашиваться Gif изображения
    public final static String[] chancels = {"latest", "top", "hot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация tab layout и добавление обработки собыий у него
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(this);

        // Устанавливаем начальный фрагмент при старте приложения
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, GifViewerFragment.newInstance(chancels[0]))
                .commit();
    }

    // При смене таба, меняем фрагмент
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