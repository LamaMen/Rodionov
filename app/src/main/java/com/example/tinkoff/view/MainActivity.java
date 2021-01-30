package com.example.tinkoff.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinkoff.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, GifViewerFragment.newInstance("bla", "true"), GifViewerFragment.TAG)
                .commit();
    }
}