package com.kh_sof_dev.real_estate_bosnia.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class Details extends AppCompatActivity implements View.OnClickListener , OnMapReadyCallback
, CompoundButton.OnCheckedChangeListener {
public static Real_estate real_estate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
       Getdata();
    }
private void MyRealEstate(){
    solde_check.setVisibility(View.GONE);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("Users");
        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for (DataSnapshot ds:dataSnapshot.child("My_Real_Estate").getChildren()
                                 ) {
                                String id=ds.getValue(String.class);
                                if (id.equals(real_estate.getUid())){
                                    solde_check.setVisibility(View.VISIBLE);
                                    return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

}
    private void Getdata() {
        MyRealEstate();
        if (isAdmin()){
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        if (real_estate!=null){


            if (real_estate.getSolde()){
                is_solde.setVisibility(View.VISIBLE);
            }
            if (real_estate.getType()==1){
                room_lay.setVisibility(View.GONE);
                bulding_lay.setVisibility(View.GONE);
                bath_lay.setVisibility(View.GONE);

                type.setVisibility(View.VISIBLE);
                if (real_estate.getEarth_type()==1){
                    type.setText(getString(R.string.type1));

                }else {
                    type.setText(getString(R.string.type2));

                }
                earth_nb.setText(real_estate.getEarth().toString());
            }
            if (real_estate.getType()==2){
               room_nb.setText(real_estate.getRoom()+"");
               bath_nb.setText(real_estate.getBath()+"");
               bulding_nb.setText(real_estate.getBuilding().toString());

                earth_nb.setText(real_estate.getEarth().toString());
            }
            if (real_estate.getType()==3){
                earth_lay.setVisibility(View.GONE);


                room_nb.setText(real_estate.getRoom()+"");
                bath_nb.setText(real_estate.getBath()+"");
                bulding_nb.setText(real_estate.getBuilding().toString());


            }
            nb.setText(real_estate.getNb()+"");
            price.setText(real_estate.getPrice().toString()+" €");
            place.setText(real_estate.getLocation().getCity());
            solde_check.setChecked(real_estate.getSolde());
            solde_check.setOnCheckedChangeListener(this);
            ////images


        for(String name : real_estate.getImagesURL()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                   ;

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.app_indicator2));
        }
    }

    private boolean isAdmin() {
        if (FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals("+213672886642")
         || FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals("+971505555017")){  //
            return true;
        }
        return false;
    }

    ///////inint/////////
    ImageView back;
    CheckBox solde_check;
    private GoogleMap mMap;
    ImageView whatch_btn,is_solde;
    Button whats,type,email,zoomin,zoomout,edit,delete;
    TextView room_nb,earth_nb,bath_nb,bulding_nb,price,place,nb;
    private SliderLayout mDemoSlider;
    LinearLayout room_lay,earth_lay,bath_lay,bulding_lay;
    private void  init(){
       whatch_btn=findViewById(R.id.watch_btn);
       whatch_btn.setOnClickListener(this);
       is_solde=findViewById(R.id.is_solde);
       whats=findViewById(R.id.whatup_contacte);
       whats.setOnClickListener(this);

        edit=findViewById(R.id.edit_btn);
        edit.setOnClickListener(this);
        delete=findViewById(R.id.delete_btn);
        delete.setOnClickListener(this);
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

        email=findViewById(R.id.email_contacte);
        type=findViewById(R.id.type);
        type.setVisibility(View.GONE);
        email.setOnClickListener(this);
        zoomin=findViewById(R.id.zomin);
        zoomin.setOnClickListener(this);

        zoomout=findViewById(R.id.zomout);
        zoomout.setOnClickListener(this);

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(this);

        nb=findViewById(R.id.nb);
        room_nb=findViewById(R.id.room_nb);
        bath_nb=findViewById(R.id.bath_nb);
        bulding_nb=findViewById(R.id.bulding_nb);
        earth_nb=findViewById(R.id.earth_nb);
        price=findViewById(R.id.price);
        place=findViewById(R.id.place);

        room_lay=findViewById(R.id.room_lay);
        bath_lay=findViewById(R.id.bath_lay);
        bulding_lay=findViewById(R.id.bulding_lay);
        earth_lay=findViewById(R.id.earth_lay);

        solde_check=findViewById(R.id.solde_check);


        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
///Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    private void openWhatsApp(String numero,String mensaje){

        try{
            PackageManager packageManager =getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
              Toast.makeText(this, getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(this, getString(R.string.no_whatsapp), Toast.LENGTH_SHORT);
        }

    }
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.delete_btn:
        Delete_Real();
        break;
    case R.id.edit_btn:
        startActivity(new Intent(Details.this,Edite.class));

        break;
    case R.id.back_btn:
       startActivity(new Intent(Details.this,MainActivity.class));
       finish();
        break;
    case R.id.watch_btn:

String url="https://www.youtube.com/user/craterco";
        if (!real_estate.getYoutup().isEmpty()){
           url=real_estate.getYoutup();
        }
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            Details.this.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
        }
        break;

    case R.id.whatup_contacte:
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Login_activity.account_type=1;
            Intent intent=new Intent(Details.this, Login_activity.class);
            startActivity(intent);
            finish();
        }
        openWhatsApp(real_estate.getProfider_phone(),"Real estate nb="+real_estate.getNb());//+38761505555
        break;
    case R.id.email_contacte:
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Login_activity.account_type=1;
            Intent intent=new Intent(Details.this, Login_activity.class);
            startActivity(intent);
            finish();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        String[] TO ={real_estate.getProfider_email()};
                // {"Bosnia545@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,TO );//"Bosnia545@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Real estate nb="+real_estate.getNb());
        intent.putExtra(Intent.EXTRA_TEXT, "مرحبا اريد الاستفسار حول هذا العقار.");

        startActivity(Intent.createChooser(intent, "Send Email"));
        break;
    case R.id.zomin:
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        break;
    case R.id.zomout:
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
        break;
}
    }

    private void Delete_Real() {

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

        final DatabaseReference finalRef = ref;
        new AlertDialog.Builder(Details.this)
                .setTitle("حذف  العقار")
                .setMessage("هل تريد حذف  العقار ")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseStorage storage=FirebaseStorage.getInstance();
                        for (String img:real_estate.getImagesURL()
                             ) {
                            final StorageReference refstr = storage.getReferenceFromUrl(img);
                            refstr.delete();
                        }

                        finalRef.child(real_estate.getUid()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Details.this,
                                                    "تم حذف العقار",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Details.this,MainActivity.class));
                                            Details.this.finish();
                                        }
                                    }
                                });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.sold)
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(real_estate.getLocation().getLat(), real_estate.getLocation().getLng());
         mMap.addMarker(new MarkerOptions().position(location).title(real_estate.getLocation().getCity()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
//
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

        if (!isChecked){
            Map<String, Object> map = new HashMap<>();
            map.put("solde", false);
            ref.child(real_estate.getUid()).updateChildren(map)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Details.this,"تم تعديل حالة العقار",Toast.LENGTH_SHORT).show();
                        is_solde.setVisibility(View.GONE);

                    }
                }
            });

            return;
       }
        final DatabaseReference finalRef = ref;
        Log.d("ref",finalRef.toString());
        new AlertDialog.Builder(Details.this)
                .setTitle("تعديل حالة العقار")
                .setMessage("هل تريد تعديل حالة العقار الى تم البيع")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("solde", true);
                        finalRef.child(real_estate.getUid()).updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Details.this,
                                                    "تم تعديل حالة العقار",Toast.LENGTH_SHORT).show();
                                            is_solde.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        solde_check.setChecked(false);
                    }
                })
                .setIcon(R.drawable.sold)
                .show();
    }
}
