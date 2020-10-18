package com.example.manivaandroapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.manivaandroapp.R;
import com.example.manivaandroapp.controllers.utils.Child;
import com.example.manivaandroapp.controllers.utils.InternetController;
import com.example.manivaandroapp.models.HomeModel;
import com.example.manivaandroapp.ui.adapters.HomeFragAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //Define properties
    private HomeViewModel homeViewModel;
    private HomeModel homeModel;
    private ProgressBar progressBar;
    private HomeFragAdapter homeFragAdapter;
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = inflater.getContext();
        init();
        checkConnectAndGetData();
        return view;
    }

    //initialize components and bind them
    private void init(){

        progressBar = view.findViewById(R.id.home_frag_pB);
        recyclerView = view.findViewById(R.id.home_frag_rV);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(Child.PAGES).child(Child.MAIN_PAGE);

    }

    //Check Reachability  by internet connection
    private void checkConnectAndGetData(){
        if (InternetController.isNetworkConnected(context)) {
            getData();
        } else {
            Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    //Get data and create own generic
    private void getData(){
        homeModel = new HomeModel();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                List<String> imagesURL =  snapshot.child(Child.IMAGES).getValue(t);
                List<String> titles =  snapshot.child(Child.TITLES).getValue(t);
                List<String> descriptions =  snapshot.child(Child.DESCRIPTIONS).getValue(t);

                homeModel = new HomeModel(new ArrayList<String>(imagesURL),new ArrayList<String>(titles),new ArrayList<String>(descriptions));
                homeFragAdapter = new HomeFragAdapter(homeModel.getTitles(), homeModel.getImagesURL(), homeModel.getDescriptions(), context);

                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Set and reaload my recyler view by Data
    private void setData(){
        recyclerView.setAdapter(homeFragAdapter);
        homeFragAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}