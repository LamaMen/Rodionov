package com.example.tinkoff.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tinkoff.App;
import com.example.tinkoff.R;
import com.example.tinkoff.repository.model.Gif;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class GifViewerFragment extends Fragment {
    public final static String TAG = "VIEWER";

    private ImageView gifViewer;
    private TextView name;
    private Button previousButton;
    private Button nextButton;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public GifViewerFragment() {
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

        LiveData<Gif> liveData = App.getInstance().repository.getLiveData();
        liveData.observe(this, gif -> {
            name.setText(gif.getName());
            Glide.with(getContext())
                    .load(gif.getUrl())
                    .fitCenter()
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(15, 0)))
                    .into(gifViewer);
        });

        return view;
    }
}