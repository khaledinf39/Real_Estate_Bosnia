package com.kh_sof_dev.real_estate_bosnia.Activities.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Add_new;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Login_activity;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.MainActivity;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.appartement_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.earth_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.filla_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Appartement extends Fragment implements View.OnClickListener {


    public Appartement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_appartement, container, false);
        init(view);
        FetchAllData(null,null);
        MainActivity.mLisstenner=new MainActivity.onsleactGoverment() {
            @Override
            public void onselectItem(String gov, String mun) {
                FetchAllData(gov,mun);
            }

            @Override
            public void onfilterItem(List<MainActivity.items> items_) {
                real_estateList_filter.clear();
                real_estateList_filter.addAll(real_estateList);
                for (MainActivity.items i:items_
                ) {
                    switch (i.name){
                        case "r":
                            real_estateList_filter=filter(i.value1,i.value2,"room",real_estateList_filter);
                            break;
                        case "ba":
                            real_estateList_filter=filter(i.value1,i.value2,"bath",real_estateList_filter);
                            break;
                        case "b":
                            real_estateList_filter=filter(i.value1,i.value2,"bulding",real_estateList_filter);
                            break;



                    }
                }
                adapter=new appartement_adapter(getContext(),real_estateList_filter);
                RV.setAdapter(adapter);
            }
            @Override
            public void onfiltertype(int type) {
//
            }
        };
        return view;
    }
    private  List<Real_estate> filter(Double v1,Double v2,String cod, List<Real_estate> real_estateList){
        List<Real_estate> mlist=new ArrayList<>();

        switch (cod){
            case "room":
                for (Real_estate r:real_estateList
                ) {
                    if (r.getRoom()<= v2 &&  r.getRoom()>=v1){
                        mlist.add(r);
                    }
                }
                break;
            case "bath":
                for (Real_estate r:real_estateList
                ) {
                    if (r.getBath()<= v2 &&  r.getBath()>=v1){
                        mlist.add(r);
                    }
                }
                break;
            case "bulding":
                for (Real_estate r:real_estateList
                ) {
                    if (r.getBuilding()<= v2 &&  r.getBuilding()>=v1){
                        mlist.add(r);
                    }
                }
                break;
            case "earth":
                for (Real_estate r:real_estateList
                ) {
                    if (r.getEarth()<= v2 &&  r.getEarth()>=v1){
                        mlist.add(r);
                    }
                }
                break;
            case "price":
                for (Real_estate r:real_estateList
                ) {
                    if (r.getPrice1()<= v2 && r.getPrice()>= v1){
                        mlist.add(r);
                    }
                }
                break;
        }
        Toast.makeText(getContext(),mlist.size()+"   size  ",Toast.LENGTH_SHORT).show();
        if (mlist.size()==0){
            return real_estateList;
        }else {
            return mlist;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////*
    appartement_adapter adapter;
    List<Real_estate> real_estateList;
    List<Real_estate> real_estateList_filter;
    private void FetchAllData(String gov, String mun) {

        real_estateList=new ArrayList<>();
        real_estateList_filter=new ArrayList<>();
        adapter=new appartement_adapter(getContext(),real_estateList);
        RV.setAdapter(adapter);
        if (gov==null){
            GetAll();
        }else if (gov!=null && mun !=null){
            GetBay(gov,mun);
        }

    }
    private void GetBay(final String gov, final String mun){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mRef=database.getReference("Governorate")
                .child(gov).child("Municipality").child(mun).child("Apartment");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Real_estate real_estate=dataSnapshot.getValue(Real_estate.class);
                real_estate.setUid(dataSnapshot.getKey());
                real_estate.setGovkey(gov);
                real_estate.setMunkey(mun);
                real_estateList.add(real_estate);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                noitem.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GetAll(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mRef=database.getReference("Governorate");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot ds:dataSnapshot.child("Municipality").getChildren()
                ) {
                    for (DataSnapshot dts: ds.child("Apartment").getChildren()
                    ) {
                        Real_estate real_estate=dts.getValue(Real_estate.class);
                        real_estate.setUid(dts.getKey());
                        real_estate.setGovkey(dataSnapshot.getKey());
                        real_estate.setMunkey(ds.getKey());
                        real_estateList.add(real_estate);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        noitem.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    ////////////////////////init////////////////


    RecyclerView RV;
    ProgressBar progressBar;
    TextView noitem;
    private void init(View view) {


        FloatingActionButton fab=view.findViewById(R.id.fab);

        fab.setOnClickListener(this);
        progressBar=view.findViewById(R.id.progress);
        noitem=view.findViewById(R.id.noItem);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(real_estateList.size()==0){
                    progressBar.setVisibility(View.GONE);
                    noitem.setVisibility(View.VISIBLE);
                }


            }
        }, 50000);


        RV=view.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Intent intent=new Intent(getActivity(), Login_activity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getActivity(), Add_new.class);
                    startActivity(intent);
                }

                break;


        }}








}
