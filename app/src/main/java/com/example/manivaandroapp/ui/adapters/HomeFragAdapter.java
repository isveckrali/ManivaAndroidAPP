package com.example.manivaandroapp.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.utils.Helper;
import com.example.manivaandroapp.controllers.utils.InternetController;
import com.example.manivaandroapp.ui.home.HomeViewModel;
import com.example.manivaandroapp.ui.homeDetails.HomeDetailFragment;
import com.google.rpc.Help;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.PostViewHolder>{

    private ArrayList<String> titles, images, descriptions;
    private Context context;


    public HomeFragAdapter(ArrayList<String> titles, ArrayList<String> images, ArrayList<String> descriptions, Context context) {
        this.titles = titles;
        this.images = images;
        this.descriptions = descriptions;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_home_view,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        HomeViewModel homeViewModel = new HomeViewModel(images.get(position), titles.get(position), descriptions.get(position));
        holder.setCategory(homeViewModel.getImageUrl(),homeViewModel.getTitle(),homeViewModel.getSortDescription(), position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundedImageView imageView;
        TextView descriptionTV, titleTV;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.item_home_iV);
            descriptionTV = itemView.findViewById(R.id.item_home_desc_tV);
            titleTV = itemView.findViewById(R.id.item_home_title_tV);
        }

        public void setCategory(final String imagesUrl, final String title, final String description, final int position){
            Glide.with(context).load(imagesUrl).into(imageView);
            titleTV.setText(title);
            descriptionTV.setText(description);
        }

        @Override
        public void onClick(View view) {
            if (!InternetController.isNetworkConnected(context)) {
                Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
                return;
            }

            HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("images_url",images.get(getAdapterPosition()));
            bundle.putString("title",titles.get(getAdapterPosition()));
            bundle.putString("description",descriptions.get(getAdapterPosition()));
            homeDetailFragment.setArguments(bundle);
            Helper.loadFragment(new HomeDetailFragment(), context, R.id.fragment_container);
        }
    }
}
