package com.example.manivaandroapp.ui.homeDetail;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manivaandroapp.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static  final String IMAGE = "image_url";
    public static  final String TITLE = "title";
    public static  final String DESCRIPTION = "description";

    private TextView titleTV, descriptionTV;
    private ImageView imageView;

    // TODO: Rename and change types of parameters
    private String imageUrl = "";
    private String title = "";
    private String description = "";

    private View view;
    private Context context;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_detail, container, false);
        context = inflater.getContext();
        init();
        getData();
        return view;
    }

    //İnitialize components
    private void init(){
        imageView = view.findViewById(R.id.frag_home_detail_iV);
        titleTV = view.findViewById(R.id.frag_home_detail_title_tV);
        descriptionTV = view.findViewById(R.id.frag_home_detail_desc_tV);
        progressBar = view.findViewById(R.id.frag_home_detail_pB);

    }

    //Get data from arguments which comes front page
    private void getData(){
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE);
            title = getArguments().getString(TITLE);
            description = getArguments().getString(DESCRIPTION);
            setData();
        }
        progressBar.setVisibility(View.GONE);
    }

    //Set arguments to components.
    private void setData(){
        Glide.with(context).load(imageUrl).into(imageView);
        titleTV.setText(title);
        descriptionTV.setText(description);
    }
}