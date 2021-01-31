package com.example.tinkoff.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.tinkoff.R;
import com.example.tinkoff.repository.Repository;
import com.example.tinkoff.repository.RepositoryImpl;
import com.example.tinkoff.repository.model.Gif;


public class GifViewerFragment extends Fragment implements androidx.lifecycle.Observer<Gif>, RepositoryImpl.Observer, View.OnClickListener {
    public final static String TAG = "VIEWER";
    public static final String CHANEL_KEY = "CHANEL";

    private final Repository repository;

    private ImageView viewer;
    private TextView name;
    private Button previousButton;
    private Button nextButton;
    private ProgressBar progressBar;

    private View errorIcon;
    private View errorText;
    private Button errorButton;

    private View lastGifText;


    public GifViewerFragment() {
        repository = RepositoryImpl.getInstance();
    }


    public static GifViewerFragment newInstance(String chanel) {
        GifViewerFragment fragment = new GifViewerFragment();
        Bundle args = new Bundle();
        args.putString(CHANEL_KEY, chanel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        String chanel = getArguments().getString(CHANEL_KEY);
        repository.connect(this, chanel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_viewwer, container, false);

        viewer = view.findViewById(R.id.gif_viewer);
        name = view.findViewById(R.id.gif_name);
        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);
        progressBar = view.findViewById(R.id.progress_bar);

        errorIcon = view.findViewById(R.id.error_icon);
        errorText = view.findViewById(R.id.error_text);
        errorButton = view.findViewById(R.id.error_button);

        lastGifText = view.findViewById(R.id.last_gif);

        errorButton.setOnClickListener(this);

        LiveData<Gif> liveData = repository.getLiveData();
        liveData.observe(this, this);

        nextButton.setOnClickListener(v -> repository.nextGif());
        previousButton.setOnClickListener(v -> repository.previousGif());

        repository.updateGif();

        return view;
    }

    @Override
    public void onChanged(Gif gif) {
        if (gif.isLast()) {
            deactivateButton(nextButton);
            viewer.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            lastGifText.setVisibility(View.VISIBLE);
        } else {
            lastGifText.setVisibility(View.GONE);
            name.setText(gif.getName());
            loading();
            Glide.with(getContext())
                    .load(gif.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            done();
                            error();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            done();
                            closeErrorPage();
                            return false;
                        }
                    })
                    .fitCenter()
                    .into(viewer);
        }


        if (repository.isFirst()) {
            deactivateButton(previousButton);
        } else {
            activateButton(previousButton);
        }
    }

    @Override
    public void error() {
        deactivateButton(previousButton);
        deactivateButton(nextButton);
        viewer.setVisibility(View.GONE);
        name.setVisibility(View.GONE);

        errorIcon.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    private void closeErrorPage() {
        if (!repository.isFirst()) {
            activateButton(previousButton);
        }
        activateButton(nextButton);
        viewer.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);

        errorIcon.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void loading() {
        viewer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void done() {
        progressBar.setVisibility(View.GONE);
        viewer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnectedOrConnecting()) {
            return;
        }

        closeErrorPage();
        repository.updateGif();
    }

    private void activateButton(Button button) {
        button.setClickable(true);
        button.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void deactivateButton(Button button) {
        button.setClickable(false);
        button.setBackgroundColor(Color.parseColor("#e4e4e4"));
    }
}