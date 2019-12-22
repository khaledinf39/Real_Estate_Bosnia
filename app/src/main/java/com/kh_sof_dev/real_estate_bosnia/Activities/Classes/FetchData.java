package com.kh_sof_dev.real_estate_bosnia.Activities.Classes;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FetchData {
    FirebaseDatabase database;
OnFetch onFetch;

    public FetchData() {
        database=FirebaseDatabase.getInstance();

    }
    public int getRealestateNB(){
        DatabaseReference reference=database.getReference("Realestate").child("NB");
        final int[] nb = {0};
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nb[0] =dataSnapshot.getValue(Integer.class) +1;
                }else {
                    nb[0]=100;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return nb[0];
    }
    public interface OnFetch {
        void ongetdata(List<Governorate> list);
    }
    public   List<Governorate> getGovernoment(final OnFetch onFetch){
        this.onFetch=onFetch;
        final List<Governorate> list=new ArrayList<>();

        DatabaseReference ref=database.getReference("Governorate");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Governorate governorate=dataSnapshot.getValue(Governorate.class);
                    governorate.setUid(dataSnapshot.getKey());
                    list.add(governorate);
                    onFetch.ongetdata(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

return list;
    }
    public   List<Governorate> getmun(final OnFetch onFetch,String key){
        this.onFetch=onFetch;
        final List<Governorate> list=new ArrayList<>();
        list.add(new Governorate());
        list.add(new Governorate());
        DatabaseReference ref=database.getReference("Governorate").child(key).child("Municipality");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Governorate governorate=dataSnapshot.getValue(Governorate.class);
                    list.add(governorate);
                    onFetch.ongetdata(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return list;
    }
}
