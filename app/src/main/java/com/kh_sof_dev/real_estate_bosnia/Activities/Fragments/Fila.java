package com.kh_sof_dev.real_estate_bosnia.Activities.Fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fila extends Fragment implements View.OnClickListener {


    public Fila() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fila, container, false);
        init(view);

        FetchAllData(MainActivity.Govern_key,MainActivity.Mun_key);
        MainActivity.mLisstenner_fella=new MainActivity.onsleactGoverment() {
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
                        case "e":
                            real_estateList_filter=filter(i.value1,i.value2,"earth",real_estateList_filter);
                            break;
                        case "p":
                            real_estateList_filter=filter(i.value1,i.value2,"price",real_estateList_filter);
                            break;
                        case "t":
                            real_estateList_filter=filter_type(i.value3,real_estateList_filter);
                            break;
                    }
                }
                adapter=new filla_adapter(getContext(),real_estateList_filter);
                RV.setAdapter(adapter);
            }
            @Override
            public void onfiltertype(int type) {
//
            }
        };
        return view;
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
            noitem.setVisibility(View.VISIBLE);
        }
        return mlist;

//        }
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
                    if (r.getPrice()<= v2 && r.getPrice()>= v1){
                        mlist.add(r);
                    }
                }
                break;
        }
//        Toast.makeText(getContext(),mlist.size()+"   filla size  ",Toast.LENGTH_SHORT).show();
        if (mlist.size()==0){
            noitem.setVisibility(View.VISIBLE);
        }
        return mlist;
    }

    //////////////////////////////////////////////////////////////////////////////////////////*
    filla_adapter adapter;
    List<Real_estate> real_estateList;
    List<Real_estate> real_estateList_filter;
    private void FetchAllData(String gov, String mun) {

        real_estateList_filter=new ArrayList<>();
        real_estateList=new ArrayList<>();
        real_estateList.clear();
        adapter=new filla_adapter(getContext(),real_estateList);

        RV.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(real_estateList.size()==0){
                    progressBar.setVisibility(View.GONE);
                    noitem.setVisibility(View.VISIBLE);
                }


            }
        }, 50000);
        if (gov==null){
            GetAll();
        }else if (gov!=null && mun !=null){
            GetBay(gov,mun);
        }
        else if (gov!=null && mun ==null){
            GetAllMun(gov);
        }

    }
    private void GetAllMun(final String gov){

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mRef=database.getReference("Governorate").child(gov).child("Municipality");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    for (DataSnapshot dts: dataSnapshot.child("Fella").getChildren()
                    ) {
                        Real_estate real_estate=dts.getValue(Real_estate.class);
                        real_estate.setUid(dts.getKey());
                        real_estate.setGovkey(gov);
                        real_estate.setMunkey(dataSnapshot.getKey());
                        real_estateList.add(real_estate);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        noitem.setVisibility(View.GONE);
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
    private void GetBay(final String gov, final String mun){
    //    Toast.makeText(getContext(),"bay filla"+gov+"  "+mun,Toast.LENGTH_SHORT).show();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mRef=database.getReference("Governorate")
                .child(gov).child("Municipality").child(mun).child("Fella");
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
                    for (DataSnapshot dts: ds.child("Fella").getChildren()
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
    Button email,whats;
    private void init(View view) {
        email=view.findViewById(R.id.email_contacte);
        whats=view.findViewById(R.id.whatup_contacte);

        email.setOnClickListener(this);
        whats.setOnClickListener(this);

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

    private void openWhatsApp(String numero,String mensaje){

        try{
            PackageManager packageManager =getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
//            String url="https://chat.whatsapp.com/HRDgK1vOui361nWu3H9u9f";

            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                getActivity().startActivity(i);
            }else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(getActivity(), getActivity().getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.whatup_contacte:
                openWhatsApp("+38761505555","مرحبا اريد الاستفسار حول  العقارات البوسنة ( التطبيق )");//

                break;
            case R.id.email_contacte:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                String[] TO ={"Bosnia545@gmail.com"};
                // {"Bosnia545@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL,TO );//"Bosnia545@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "استفسار");
                intent.putExtra(Intent.EXTRA_TEXT, "مرحبا اريد الاستفسار حول  العقارات البوسنة ( التطبيق )");

                getActivity().startActivity(Intent.createChooser(intent, "Send Email"));
                break;
            case R.id.fab:
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Intent i=new Intent(getActivity(), Login_activity.class);
                    startActivity(i);
                }else {
                    Intent i=new Intent(getActivity(), Add_new.class);
                    startActivity(i);
                }

                break;


        }}



}
