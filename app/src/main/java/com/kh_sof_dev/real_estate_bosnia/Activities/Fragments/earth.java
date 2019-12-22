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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Add_new;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.MainActivity;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.earth_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.filla_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.FetchData;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class earth extends Fragment implements View.OnClickListener {


    public earth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_earth, container, false);
        init(view);
        FetchAllData(null,null);
        MainActivity.mLisstenner=new MainActivity.onsleactGoverment() {
            @Override
            public void onselectItem(String gov, String mun) {
                FetchAllData(gov,mun);
            }

            @Override
            public void onfilterPrice(Double p1, Double p2) {
                if (p2==0.0){
                    return;
                }

                real_estateList_filter.clear();
                real_estateList_filter.addAll(filter(p1,p2,"price",real_estateList));
                adapter=new earth_adapter(getContext(),real_estateList_filter);
                RV.setAdapter(adapter);
            }

            @Override
            public void onfilterRoom(Double r1, Double r2) {

            }

            @Override
            public void onfilterBath(Double b1, Double b2) {

            }


            @Override
            public void onfilterearth(Double e1, Double e2) {
                if (e2==0.0){
                    return;
                }

                real_estateList_filter.clear();
                real_estateList_filter.addAll(filter(e1,e2,"earth",real_estateList));
                adapter=new earth_adapter(getContext(),real_estateList_filter);
                RV.setAdapter(adapter);
            }

            @Override
            public void onfilterbulding(Double p1, Double p2) {

            }

            @Override
            public void onfiltertype(int type) {

                real_estateList_filter.clear();
                real_estateList_filter.addAll(filter_type(type,real_estateList));
                adapter=new earth_adapter(getContext(),real_estateList_filter);
                RV.setAdapter(adapter);
            }
        };
        return view;
    }
//////////////////////////////////////////////////////////////////////////////////////////*
earth_adapter adapter;
    List<Real_estate> real_estateList;
    List<Real_estate> real_estateList_filter;
    private void FetchAllData(String gov, String mun) {
        real_estateList_filter=new ArrayList<>();
        real_estateList=new ArrayList<>();
        adapter=new earth_adapter(getContext(),real_estateList);
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
                .child(gov).child("Municipality").child(mun).child("Earth");
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
                    for (DataSnapshot dts: ds.child("Earth").getChildren()
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
    private  List<Real_estate> filter_type(int type, List<Real_estate> real_estateList){
        List<Real_estate> mlist=new ArrayList<>();

        for (Real_estate r:real_estateList
        ) {
            if (r.getEarth_type()==type){
                mlist.add(r);
            }
        }
        if (mlist.size()==0){
            return real_estateList;
        }else {
            return mlist;
        }
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
                    if (r.getPrice()<= v2 && r.getPrice()>=v1){
                        mlist.add(r);
                    }
                }
                break;
        }
       // Toast.makeText(getContext(),mlist.size()+"   size  ",Toast.LENGTH_SHORT).show();
        if (mlist.size()==0){
            return real_estateList;
        }else {
            return mlist;
        }
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
                Intent intent=new Intent(getActivity(), Add_new.class);
                startActivity(intent);
                break;


        }}






}
