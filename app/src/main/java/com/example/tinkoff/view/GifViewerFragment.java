package com.example.tinkoff.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.tinkoff.R;
import com.example.tinkoff.repository.Repository;
import com.example.tinkoff.repository.RepositoryImpl;
import com.example.tinkoff.repository.model.Gif;


public class GifViewerFragment extends Fragment implements Observer<Gif> {
    public final static String TAG = "VIEWER";

    private final Repository repository;

    private ImageView gifViewer;
    private TextView name;
    private Button previousButton;
    private Button nextButton;
    private ProgressBar progressBar;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public GifViewerFragment() {
        repository = RepositoryImpl.getInstance();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GifViewwerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GifViewerFragment newInstance(String param1, String param2) {
        GifViewerFragment fragment = new GifViewerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository.updateGifs();
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_viewwer, container, false);

        gifViewer = view.findViewById(R.id.gif_viewer);
        name = view.findViewById(R.id.gif_name);
        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);
        progressBar = view.findViewById(R.id.progress_bar);

        LiveData<Gif> liveData = repository.getGif();
        liveData.observe(this, this);

        nextButton.setOnClickListener(v -> repository.nextGif());
        previousButton.setOnClickListener(v -> repository.previousGif());

        return view;
    }

    @Override
    public void onChanged(Gif gif) {
        name.setText(gif.getName());
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(gif.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .fitCenter()
                .into(gifViewer);

        if (repository.isFirst()) {
            previousButton.setClickable(false);
            previousButton.setBackgroundColor(Color.parseColor("#e4e4e4"));
        } else {
            previousButton.setClickable(true);
            previousButton.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
}