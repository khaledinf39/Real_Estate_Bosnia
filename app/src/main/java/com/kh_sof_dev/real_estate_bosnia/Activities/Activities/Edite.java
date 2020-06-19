package com.kh_sof_dev.real_estate_bosnia.Activities.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.Gover_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.Imag_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.ResizePickedImage;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.location;
import com.kh_sof_dev.real_estate_bosnia.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class Edite extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, OnMapReadyCallback {
    String TAG="uploaded";
    private static final int PICK_IMAGE_REQUEST =1 ;
    private GoogleMap mMap;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite);
        init();
        GetData();

    }
int RealNB=100;
    @Override
    protected void onStart() {
        super.onStart();

        /*************************************get number of real estate*****************************************/
        database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Realestate").child("NB");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    RealNB =dataSnapshot.getValue(Integer.class) +1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ////////////////////////init////////////////
    private Real_estate real_estate;
    SeekBar seek_bath,seek_price,seek_room,seek_bulding,seek_earth;
    EditText nb_bath,nb_price,nb_room,nb_bulding,nb_earth,nb_price1;
          TextView  location_tv;
    RadioButton radio_building,radio_filla,radio_earth;
    Button type1,type2,save;
    EditText youtup;
ImageView back_btn;

    LinearLayout types_lay,info1,earth_lay,building_lay,info_lay;
    CheckBox solde;
    Button   Governorate_sp,municipality_sp,zomin,zomout,take_img;
    EditText dis,locationurl;
    RecyclerView RV_img;
    private void init() {
        dis=findViewById(R.id.discription);
        locationurl=findViewById(R.id.location);
RV_img=findViewById(R.id.RV);
        seek_bath=findViewById(R.id.seek_bar_bath);
        seek_room=findViewById(R.id.seek_bar_room);
        seek_price=findViewById(R.id.seek_bar_price);
        seek_bulding=findViewById(R.id.seek_bar_bulding);
        seek_earth=findViewById(R.id.seek_bar_earth);
        seek_bath.setOnSeekBarChangeListener(this);
        seek_bulding.setOnSeekBarChangeListener(this);
        seek_earth.setOnSeekBarChangeListener(this);
        seek_price.setOnSeekBarChangeListener(this);
        seek_room.setOnSeekBarChangeListener(this);


        take_img=findViewById(R.id.take_img);
        take_img.setOnClickListener(this);


        nb_bath=findViewById(R.id.bath_nb);
        nb_room=findViewById(R.id.room_nb);
        nb_bulding=findViewById(R.id.bulding_nb);
        nb_price=findViewById(R.id.price_nb);
        nb_price1=findViewById(R.id.price_nb);
        nb_earth=findViewById(R.id.earth_nb);

        radio_earth=findViewById(R.id.earth);
        radio_filla=findViewById(R.id.filla);
        radio_building=findViewById(R.id.appartement);


        radio_building.setOnClickListener(this);
        radio_filla.setOnClickListener(this);
        radio_earth.setOnClickListener(this);

        //button
        type1=findViewById(R.id.type1);
        type2=findViewById(R.id.type2);

        save=findViewById(R.id.save);



        type1.setOnClickListener(this);
        type2.setOnClickListener(this);

        save.setOnClickListener(this);

        youtup=findViewById(R.id.youtup);
        location_tv=findViewById(R.id.city);


        solde=findViewById(R.id.is_solde);
        solde.setOnClickListener(this);

        types_lay=findViewById(R.id.type_lay);
        earth_lay=findViewById(R.id.earth_lay);
        building_lay=findViewById(R.id.bulding_lay);
        info1=findViewById(R.id.info1);

        types_lay=findViewById(R.id.type_lay);
        info_lay=findViewById(R.id.info);
        info_lay.setVisibility(View.GONE);
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
///Map
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        // Spinner
        Governorate_sp=findViewById(R.id.governomo_sp);
        municipality_sp=findViewById(R.id.municipality_sp);
        Governorate_sp.setOnClickListener(this);
        municipality_sp.setOnClickListener(this);

        zomin=findViewById(R.id.zomin);
        zomout=findViewById(R.id.zomout);
        zomin.setOnClickListener(this);
        zomout.setOnClickListener(this);
FetchAllData();
    }
    PopupWindow mypopupWindow_gov ,mypopupWindow_mun;
    private void FetchAllData() {


        mypopupWindow_gov=setPopUpWindow();



}
String Govern_key=null;
    String Mun_key=null;
    private PopupWindow setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.spinnerlistitems_layout, null);
        final List<Governorate> mlist=new ArrayList<>();
       final  Gover_adapter adapter=new Gover_adapter(Edite.this, mlist, new Gover_adapter.Onselected() {
            @Override
            public void onitemselect(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
                Govern_key=governorate.getUid();
                Mun_key=null;
                municipality_sp.setText(R.string.um);
                mypopupWindow_mun= setPopUpWindowmun();
                Governorate_sp.setText(governorate.getName());
                mypopupWindow_gov.dismiss();
            }

           @Override
           public void onEdite(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {

           }
       });
        RecyclerView RV=view1.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(Edite.this,LinearLayoutManager.VERTICAL,false));
        RV.setAdapter(adapter);

       final ProgressBar progressBar= view1.findViewById(R.id.progress);
       final TextView noitem= view1.findViewById(R.id.noItem);
       database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("Governorate");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Governorate governorate=dataSnapshot.getValue(Governorate.class);
                governorate.setUid(dataSnapshot.getKey());
                mlist.add(governorate);
                adapter.notifyDataSetChanged();
                noitem.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
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

        LinearLayout add1=view1.findViewById(R.id.add);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog=new BottomSheetDialog(Edite.this);
                dialog.setContentView(R.layout.popup_add_new);
                final TextView name=dialog.findViewById(R.id.name);
                LinearLayout add_ = dialog.findViewById(R.id.add);
                add_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().isEmpty()){
                            name.setError(name.getHint());
                        }
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("Governorate");
                        Governorate governorate=new Governorate();
                        governorate.setName(name.getText().toString());
                        reference.push().setValue(governorate);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mlist.size()==0){
                    progressBar.setVisibility(View.GONE);
                    noitem.setVisibility(View.VISIBLE);
                }


            }
        }, 50000);
        return new PopupWindow(view1,200, RelativeLayout.LayoutParams.WRAP_CONTENT, true);


    }
    private PopupWindow setPopUpWindowmun() {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.spinnerlistitems_layout, null);
        final List<Governorate> mlist=new ArrayList<>();
        final  Gover_adapter adapter=new Gover_adapter(Edite.this, mlist, new Gover_adapter.Onselected() {
            @Override
            public void onitemselect(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
                Mun_key=governorate.getUid();
                municipality_sp.setText(governorate.getName());
                mypopupWindow_mun.dismiss();
            }

            @Override
            public void onEdite(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {

            }
        });
        RecyclerView RV=view1.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(Edite.this,LinearLayoutManager.VERTICAL,false));
        RV.setAdapter(adapter);

        final ProgressBar progressBar= view1.findViewById(R.id.progress);
        final TextView noitem= view1.findViewById(R.id.noItem);
        database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("Governorate").child(Govern_key).child("Municipality");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Governorate governorate=dataSnapshot.getValue(Governorate.class);
                governorate.setUid(dataSnapshot.getKey());
                mlist.add(governorate);
                adapter.notifyDataSetChanged();
                noitem.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
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

        LinearLayout add1=view1.findViewById(R.id.add);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog=new BottomSheetDialog(Edite.this);
                dialog.setContentView(R.layout.popup_add_new);
                final TextView name=dialog.findViewById(R.id.name);
                LinearLayout add_ = dialog.findViewById(R.id.add);
                add_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().isEmpty()){
                            name.setError(name.getHint());
                        }
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("Governorate").child(Govern_key).child("Municipality");
                        Governorate governorate=new Governorate();
                        governorate.setName(name.getText().toString());
                        reference.push().setValue(governorate);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mlist.size()==0){
                    progressBar.setVisibility(View.GONE);
                    noitem.setVisibility(View.VISIBLE);
                }


            }
        }, 50000);
        return new PopupWindow(view1,200, RelativeLayout.LayoutParams.WRAP_CONTENT, true);


    }
    int _earth_type=1;
    int img=1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.type1:
              change_selector(type1,type2);
                _earth_type=1;
                break;
            case R.id.type2:
                change_selector(type2,type1);
                _earth_type=2;
                break;
            case R.id.take_img:
                img=real_estate.getImagesURL().size();
                if ((img+1)>10){
                    Toast.makeText(Edite.this,
                            " عدد الصور لا يمكن تن يتجاوز ال 10  ..!"
                            , Toast.LENGTH_LONG).show();
                    return;
                }
                imageBrowse(img+1);
                break;
//            case R.id.take_img2:
//                imageBrowse(2);
//                break;
//            case R.id.take_img3:
//                imageBrowse(3);
//                break;
            case R.id.save:
                save_info();
                break;
            case R.id.back_btn:
                startActivity(new Intent(Edite.this,MainActivity.class));
                finish();
                break;
            case R.id.governomo_sp:
                mypopupWindow_gov.showAsDropDown(v,0,0);
                break;
            case R.id.municipality_sp:
                if (Govern_key==null){
                    Toast.makeText(this,"لم تختار المحافظة بعد",Toast.LENGTH_LONG).show();
                    break ;
                }
                mypopupWindow_mun.showAsDropDown(v,0,0);
                break;

            case R.id.zomin:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zomout:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
        }




    }

    Imag_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void GetData( ){
        real_estate=Details.real_estate;
        List<View> seekBarList=new ArrayList<>();
        List<View> seekBarList2=new ArrayList<>();

        switch (real_estate.getType()){

            case 3://tableName="Apartment";
                earth_lay.setVisibility(View.GONE);
                info_lay.setVisibility(View.VISIBLE);
                types_lay.setVisibility(View.GONE);
                info1.setVisibility(View.VISIBLE);
                building_lay.setVisibility(View.VISIBLE);


                radio_building.setChecked(true);
                radio_earth.setEnabled(false);
                radio_filla.setEnabled(false);

                seekBarList.add(nb_bath);
                seekBarList.add(nb_room);
                seekBarList.add(nb_bulding);
                setEnabel(seekBarList,true);
                seekBarList2.add(nb_earth);
                setEnabel(seekBarList2,false);
                break;

            case 1:// tableName="earth";
                earth_lay.setVisibility(View.VISIBLE);
                types_lay.setVisibility(View.VISIBLE);
                info_lay.setVisibility(View.VISIBLE);
                info1.setVisibility(View.GONE);
                building_lay.setVisibility(View.GONE);

                radio_filla.setEnabled(false);
                radio_building.setEnabled(false);

                radio_earth.setChecked(true);


                seekBarList2.add(nb_bath);
                seekBarList2.add(nb_room);
                seekBarList2.add(nb_bulding);
                setEnabel(seekBarList2,false);

                seekBarList.add(nb_earth);
                setEnabel(seekBarList,true);
                break;
            case 2://tableName="Fella";
                earth_lay.setVisibility(View.VISIBLE);
                info1.setVisibility(View.VISIBLE);
                building_lay.setVisibility(View.VISIBLE);


                types_lay.setVisibility(View.VISIBLE);
                info_lay.setVisibility(View.VISIBLE);

radio_filla.setChecked(true);
                radio_earth.setEnabled(false);
                radio_building.setEnabled(false);
                seekBarList.add(nb_bath);
                seekBarList.add(nb_room);
                seekBarList.add(nb_bulding);
                seekBarList.add(nb_earth);
                setEnabel(seekBarList,true);

                break;


        }

        img=real_estate.getImagesURL().size();

        locationurl.setText(real_estate.getAddress());
        dis.setText(real_estate.getDescription());
        youtup.setText(real_estate.getYoutup());
        location_tv.setText(real_estate.getLocation().getCity());
GetGov_mun();
        if (real_estate.getSolde()){
            isSolde=solde.isChecked();
        }

        double data = real_estate.getBuilding();
        int value = (int)data;
        nb_bath.setText(real_estate.getBath()+"");

        nb_room.setText(real_estate.getRoom()+"");
        nb_bulding.setText(value+"");

        double data1 = real_estate.getEarth();
        int value1 = (int)data1;
        nb_earth.setText(value1+"");

        double data2 = real_estate.getPrice();
        int value2 = (int)data2;
        nb_price.setText(value2+"");


        switch (real_estate.getEarth_type()) {
            case 1:
                change_selector(type1, type2);
                _earth_type = 1;
                break;
            case 2:
                change_selector(type2, type1);
                _earth_type = 2;
                break;
        }

       adapter=new Imag_adapter(this, real_estate.getImagesURL(), new Imag_adapter.Onselected() {
            @Override
            public void onitemselect(int potions) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference ref = null;
                if (real_estate.getType()==1){
                    ref= database.getReference()
                            .child("Governorate")
                            .child(real_estate.getGovkey())
                            .child("Municipality")
                            .child(real_estate.getMunkey())
                            .child("Earth");
                }
                if (real_estate.getType()==2){
                    ref= database.getReference()
                            .child("Governorate")
                            .child(real_estate.getGovkey())
                            .child("Municipality")
                            .child(real_estate.getMunkey())
                            .child("Fella");
                }
                if (real_estate.getType()==3){
                    ref= database.getReference()
                            .child("Governorate")
                            .child(real_estate.getGovkey())
                            .child("Municipality")
                            .child(real_estate.getMunkey())
                            .child("Apartment");
                }

                ref.child(real_estate.getUid()).child("imagesURL").child(potions+"").removeValue();
            }
        });
        RV_img.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        RV_img.setAdapter(adapter);
//
//        String compresedImagePath;
//        try {
//           Uri returnUri=Uri.parse(real_estate.getImagesURL().get(0));
//
//            URL url = new URL(real_estate.getImagesURL().get(0));
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            BitmapDrawable background = new BitmapDrawable(bmp);
//            img1.setBackground(background);
//
//            Picasso.with(this)
//                    .load(url)
//                    .resize(width,height).noFade().into(img1);
//            ResizePickedImage resizePickedImage = new ResizePickedImage();
//            String realePath = resizePickedImage.getRealPathFromURI(returnUri, this);
//            compresedImagePath = resizePickedImage.resizeAndCompressImageBeforeSend
//                    (this, realePath, "image1");
//            if (mPaths.contains(compresedImagePath)) {
//                mPaths.remove(compresedImagePath);
//
//            }
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),returnUri);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            Uri returnUri=Uri.parse(real_estate.getImagesURL().get(1));
//            ResizePickedImage resizePickedImage = new ResizePickedImage();
//            String realePath = resizePickedImage.getRealPathFromURI(returnUri, this);
//             compresedImagePath = resizePickedImage.resizeAndCompressImageBeforeSend
//                    (this, realePath, "image2");
//            if (mPaths.contains(compresedImagePath)) {
//                mPaths.remove(compresedImagePath);
//
//            }
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),returnUri);
//            BitmapDrawable background = new BitmapDrawable(bitmap);
//            img2.setBackground(background);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        try {
//            Uri returnUri=Uri.parse(real_estate.getImagesURL().get(2));
//            ResizePickedImage resizePickedImage = new ResizePickedImage();
//            String realePath = resizePickedImage.getRealPathFromURI(returnUri, this);
//             compresedImagePath = resizePickedImage.resizeAndCompressImageBeforeSend
//                    (this, realePath, "image3");
//            if (mPaths.contains(compresedImagePath)) {
//                mPaths.remove(compresedImagePath);
//
//            }
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),returnUri);
//            BitmapDrawable background = new BitmapDrawable(bitmap);
//            img3.setBackground(background);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    private void GetGov_mun() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference()
                .child("Governorate")
                .child(real_estate.getGovkey());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name=dataSnapshot.child("name").getValue(String.class);

                    Governorate_sp.setText(name);
                    Governorate_sp.setEnabled(false);

                   dataSnapshot .child("Municipality")
                           .child(real_estate.getMunkey())
                           .getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()){
                               String name=dataSnapshot.child("name").getValue(String.class);

                               municipality_sp.setText(name);
                               municipality_sp.setEnabled(false);
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    String Governorate=null,municipality=null;
    private void save_info() {

//        if (Govern_key==null){
//            Toast.makeText(this,"لم تختار المحافظة بعد",Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (Mun_key==null){
//            Toast.makeText(this,"لم تختار البلدية بعد",Toast.LENGTH_LONG).show();
//            return;
//        }
//        FirebaseAuth auth=FirebaseAuth.getInstance();
//        Real_estate real=new Real_estate();
//        if (auth.getCurrentUser()!=null){
//
//        }

        if (dis.getText().toString().isEmpty()){
            dis.setError(dis.getHint());
            return;
        }
        if (locationurl.getText().toString().isEmpty()){
            locationurl.setError(locationurl.getHint());
            return;
        }

        if (nb_price.getText().toString().isEmpty()){
            nb_price.setError(nb_price.getHint());
            return;
        }

        final Real_estate real=new Real_estate();
        real.setProfider_phone(real_estate.getProfider_phone());
        int type = 0;
String   tableName="";
        if (radio_building.isChecked()){
            real.setBath(Integer.parseInt(nb_bath.getText().toString()));
            real.setRoom(Integer.parseInt(nb_room.getText().toString()));
            real.setBuilding(Double.parseDouble(nb_bulding.getText().toString()));
            real.setEarth(0.0);
            tableName="Apartment";
            type=3;

        }

        if (radio_filla.isChecked()){
            real.setBath(Integer.parseInt(nb_bath.getText().toString()));
            real.setRoom(Integer.parseInt(nb_room.getText().toString()));
            real.setBuilding(Double.parseDouble(nb_bulding.getText().toString()));
            real.setEarth(Double.parseDouble(nb_earth.getText().toString()));
            tableName="Fella";
            type=2;

        }

        if (radio_earth.isChecked()){
            real.setEarth(Double.parseDouble(nb_earth.getText().toString()));
            real.setBath(0);
            real.setRoom(0);
            real.setBuilding(0.0);
            real.setEarth(Double.parseDouble(nb_earth.getText().toString()));
            tableName="Earth";
            type=1;
        }
        real.setDescription(dis.getText().toString());
        real.setAddress(locationurl.getText().toString());
        real.setYoutup(youtup.getText().toString());
        real.setSolde(isSolde);
        real.setType(type);
        real.setEarth_type(_earth_type);
        real.setPrice(Double.parseDouble(nb_price.getText().toString()));
//        real.setPrice1(Double.parseDouble(nb_price1.getText().toString()));
        real.setLocation(new location(0.0,0.0,Governorate_sp.getText()+" / "+municipality_sp.getText()));

        if (mLatLng!=null){
            real.setLocation(new location(mLatLng.latitude,mLatLng.longitude,location_tv.getText().toString()));
        }
        real.setNb(real_estate.getNb());
        Map<String, Object> map = new HashMap<>();
        real.setImagesURL(real_estate.getImagesURL());
        //////*************************Loding*****************************/
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("تحديث العقار");
        final String msg="يتم تحديث معلومات العقار في قاعدة البيانات";
        dialog.setMessage(msg);
        dialog.show();



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference()
                .child("Governorate")
                .child(real_estate.getGovkey())
                .child("Municipality")
                .child(real_estate.getMunkey())
                .child(tableName);
        storage = FirebaseStorage.getInstance();
        final String key = real_estate.getUid();
        myRef.child(key).setValue(real);



        if (mPaths.size() != 0) {
            final List<String> imagesURL = new ArrayList<>();
            for (int i = 0; i < mPaths.size(); i++) {
                System.out.println("Start upload images");
                InputStream stream = null;
                try {
                    stream = new FileInputStream(new File(mPaths.get(i)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final String uuid = "image" + i;
                final StorageReference ref = storage.getReference().child(tableName).child(key).child(uuid + ".jpg");

                final UploadTask uploadTask = ref.putStream(stream);

                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
                        double progress = (-100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        Log.d(TAG, "onProgress: " + progress);
                        System.out.println("Upload is " + progress + "% done");
                        dialog.setMessage(msg+"\n"+
                                " تم تحميل  " + progress + "% ");

                    }
                });
                final String finalTableName = tableName;
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        // GET THE IMAGE DOWNLOAD URL

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                System.out.println("uri " + uri.toString());
//                                uploadingImagePB.setProgress(0);
//                                progressBarLayout.setVisibility(View.GONE);

                                Log.d(TAG, "onSuccess: the image uploded " + uri.toString());
                                Map<String, Object> map = new HashMap<>();
                                imagesURL.add(uri.toString());
                                map.put("imagesURL", imagesURL);
                                myRef   .child(key).updateChildren(map);
                                /********************** finsh****************/
                                dialog.dismiss();
                                Toast.makeText(Edite.this,
                                        "تم تحديث معلومات العقار بنجاح  ..!"
                                        , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                Details.real_estate.setBath(real.getBath());
                                Details.real_estate.setBuilding(real.getBuilding());
                                Details.real_estate.setEarth(real.getEarth());
                                Details.real_estate.setRoom(real.getRoom());
                                Details.real_estate.setPrice(real.getPrice());
                                finish();
//
             DatabaseReference reference1=database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
              reference1.child("My_Real_Estate").push().setValue(key);
                                System.out.println(" END OF THE ON OnSuccessListener");
                            }
//                            System.out.println(" END OF THE ON OnSuccessListener");
                        });
                        System.out.println(" END OF THE ON COMPLETE");
                    }
                });
            }
        }
        else {
            dialog.dismiss();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void change_selector(Button type_act, Button type_des) {
        type_act.setBackground(getDrawable(R.drawable.bk_type_btn2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            type_act.setTextColor(getColor(R.color.bk_white));
        }
        type_des.setBackground(getDrawable(R.drawable.bk_type_btn));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            type_des.setTextColor(getColor(R.color.bk_green));
        }
    }

///images
private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void imageBrowse(int PICK_IMAGE_REQUEST) {
    if (EasyPermissions.hasPermissions(getApplicationContext(), galleryPermissions)) {
        Intent pickerPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(pickerPhotoIntent, PICK_IMAGE_REQUEST);


    } else {
        EasyPermissions.requestPermissions(this, "Access for storage",
                1000, galleryPermissions);
    }


//    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//    // Start the Intent
//    startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
}
    Bitmap bitmap=null;
    List<String> mPaths = new ArrayList<>();
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {





            System.out.println("100000");
            Uri returnUri = data.getData();
            ResizePickedImage resizePickedImage = new ResizePickedImage();
            String realePath = resizePickedImage.getRealPathFromURI(returnUri, this);
            System.out.println(realePath);
            String compresedImagePath;
            Bitmap bitmapImage = null;
            try {
                Uri contentURI = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                BitmapDrawable background = new BitmapDrawable(bitmap);


                compresedImagePath = resizePickedImage.resizeAndCompressImageBeforeSend
                        (this, realePath, "image"+requestCode);
                if (mPaths.contains(compresedImagePath)) {
                    mPaths.remove(compresedImagePath);

                }
                mPaths.add(compresedImagePath);
               real_estate.getImagesURL().add(contentURI.toString());
               adapter.notifyDataSetChanged();



            } catch (IOException e) {
                e.printStackTrace();
            }


        }





    }

    Boolean isSolde=false;



    private void setEnabel(List<View> seekBarList, boolean b) {
        for (View s:seekBarList
             ) {
            s.setEnabled(b);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(44.641969, 17.867077);
//       // mMap.addMarker(new MarkerOptions().position(sydney).title("تسليتش"));
//       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
//        mLatLng=new LatLng(real_estate.getLocation().getLat(),real_estate.getLocation().getLng());
//        make_marke(mLatLng);
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mLatLng=latLng;
//                make_marke(latLng);
//            }
//        });

    }
    private LatLng mLatLng=null;
    private void make_marke(final LatLng latLng){
        mLatLng=latLng;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).draggable(true).title("المنزل");
        mMap.clear();
        mMap.addMarker(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        Geocoder geocoder;
        List<Address> addresses = null;


        try {
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }



        try{
            if (addresses.size()!=0){
                location_tv.setText(
//                        addresses.get(0).getAdminArea()+" ," +
//                        " "+addresses.get(0).getLocality()+" , "+
                        addresses.get(0).getAddressLine(0));
               }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      switch (seekBar.getId()){
          case R.id.seek_bar_bath:
              nb_bath.setText(progress+"");
              break;
          case R.id.seek_bar_bulding:
              nb_bulding.setText(progress+"");
              break;
          case R.id.seek_bar_room:
              nb_room.setText(progress+"");
              break;
          case R.id.seek_bar_price:
              nb_price.setText(progress+"");
              break;
          case R.id.seek_bar_earth:
              nb_earth.setText(progress+"");
              break;
      }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
