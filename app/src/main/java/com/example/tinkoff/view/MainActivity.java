package com.example.tinkoff.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tinkoff.App;
import com.example.tinkoff.R;
import com.example.tinkoff.view.GifViewerFragment;

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