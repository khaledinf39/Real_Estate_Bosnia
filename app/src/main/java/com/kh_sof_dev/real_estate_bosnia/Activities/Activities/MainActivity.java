package com.kh_sof_dev.real_estate_bosnia.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.kh_sof_dev.real_estate_bosnia.Activities.Adapters.Gover_adapter;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate;
import com.kh_sof_dev.real_estate_bosnia.Activities.Fragments.Appartement;
import com.kh_sof_dev.real_estate_bosnia.Activities.Fragments.Fila;
import com.kh_sof_dev.real_estate_bosnia.Activities.Fragments.earth;
import com.kh_sof_dev.real_estate_bosnia.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , OnRangeChangedListener {
    private final String[] PAGE_TITLES = new String[] {

            "شقة",

            "فلة",
            "أرض"

    };
Double p1=0.0,p2=0.0,e1=0.0,e2=0.0,b1=0.0,b2=0.0;
    Double ba1=0.0,ba2=0.0,r1=0.0,r2=0.0;
    @Override
    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        switch (view.getId()){
            case R.id.seek_bar_bath:
                bath1.setText(Math.round(leftValue)+"");
                bath2.setText(Math.round(rightValue)+"");
                ba1=(double)leftValue;
                ba2=(double)rightValue;
                break;

            case R.id.seek_bar_bulding:
                bulding1.setText(Math.round(leftValue)+"");
                bulding2.setText(Math.round(rightValue)+"");
                b1= (double)leftValue;
                b2= (double)rightValue;

                break;

            case R.id.seek_bar_earth:
                earth1.setText(Math.round(leftValue)+"");
                earth2.setText(Math.round(rightValue)+"");

                e1= Double.valueOf(leftValue);
                e2= Double.valueOf(rightValue);

                break;

            case R.id.seek_bar_price:
                price1.setText(Math.round(leftValue)+"");
                price2.setText(Math.round(rightValue)+"");

                p1= Double.valueOf(leftValue);
                p2= Double.valueOf(rightValue);

                break;

            case R.id.seek_bar_room:
                room1.setText(Math.round(leftValue)+"");
                room2.setText(Math.round(rightValue)+"");

                r1= (double)leftValue;
                r2= (double)rightValue;
                break;

        }

    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }
    public class items{
        public String name;
        public Double value1=0.0,value2=0.0;
public int value3=0;
        public items(String name, Double value1, Double value2) {
            this.name = name;
            this.value1 = value1;
            this.value2 = value2;
        }

        public items(String name, int value3) {
            this.name = name;
            this.value3 = value3;
        }
    }
    public  interface onsleactGoverment{
        void  onselectItem(String gov,String mun);
        void  onfilterItem(List<items> items);

        void  onfiltertype(int type);
    }
    public static onsleactGoverment mLisstenner_earth,mLisstenner_fella,mLisstenner_Appart;
    @Override
    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }


    Fragment appartement=new Appartement();
    Fragment Filla=new Fila();
    Fragment earth=new earth();
    private final Fragment[] PAGES = new Fragment[] {

            appartement,
            Filla,
           earth,
    };

    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    public static Context context;

    private ViewPager mViewPager;
    EditText room1,price1,bath1,bulding1,earth1,room2,price2,bath2,bulding2,earth2;
    Button type1,type2,save;
int type;

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.youtup:

                String url="https://www.youtube.com/user/craterco";

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                try {
                    MainActivity.this.startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                }
                break;

            case R.id.fb:
                Intent intent;
                String pageId="bosniaforgulf@";
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + pageId));
                } catch (Exception e) {
                    intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + pageId));
                }
                startActivity(intent);
                break;
            case R.id.twiter:
                Intent intent1;
                try {
                    // Check if the Twitter app is installed on the phone.
                   getPackageManager().getPackageInfo("com.twitter.android", 0);

                    intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://khaledbinkulai1?s=08"));
                } catch (Exception e) {
                    intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/khaledbinkulai1?s=08"));
                }
                try {
                    startActivity(intent1);
                } catch (Exception e) {
                       Toast.makeText(this,"error "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.instagrame:
                String urlins="https://instagram.com/bosnia_crater_realstate";
                Uri uri = Uri.parse(urlins);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(urlins)));
                }
                break;
        }
        switch (v.getId()){


            case R.id.menu:
                final BottomSheetDialog dialog=new BottomSheetDialog(this);
                dialog.setContentView(R.layout.popup_menu);
                TextView logout=dialog.findViewById(R.id.logout);
                ImageView ins=dialog.findViewById(R.id.instagrame);
                ImageView youtup=dialog.findViewById(R.id.youtup);
                ImageView twiter=dialog.findViewById(R.id.twiter);
                ImageView fb=dialog.findViewById(R.id.fb);
                ins.setOnClickListener(this);
                fb.setOnClickListener(this);
                twiter.setOnClickListener(this);
                youtup.setOnClickListener(this);
                logout.setOnClickListener(this);

                dialog.show();
                break;
            case R.id.filter:
                show_filterPop();
                filter.show();
                break;
            case R.id.type1:
                change_selector(type1,type2);
                type=1;
                mLisstenner_earth.onfiltertype(type);
                mLisstenner_fella.onfiltertype(type);
                mLisstenner_Appart.onfiltertype(type);
                break;
            case R.id.type2:
                change_selector(type2,type1);
                type=2;
                mLisstenner_earth.onfiltertype(type);
                mLisstenner_fella.onfiltertype(type);
                mLisstenner_Appart.onfiltertype(type);
                break;
            case R.id.save:
                List<items> items =new ArrayList<>();
                try{
                    p1=Double.parseDouble(price1.getText().toString());
                    p2=Double.parseDouble(price2.getText().toString());
                    items.add(new items("p",p1,p2));
                }catch (Exception e){
                    e.printStackTrace();
                }
               try{
                   e1=Double.parseDouble(earth1.getText().toString());
                   e2=Double.parseDouble(earth2.getText().toString());

                   items.add(new items("e",e1,e2));

               }catch (Exception e){
                   e.printStackTrace();
               }

               try{
                   ba1=Double.parseDouble(bath1.getText().toString());
                   ba2=Double.parseDouble(bath2.getText().toString());

                   items.add(new items("ba",ba1,ba2));
               }catch (Exception e){
                   e.printStackTrace();
               }

               try {
                   r1=Double.parseDouble(room1.getText().toString());
                   r2=Double.parseDouble(room2.getText().toString());

                   items.add(new items("r",r1,r2));

               }catch (Exception e){
                   e.printStackTrace();
               }
                try {
                    b1=Double.parseDouble(bulding1.getText().toString());
                    b2=Double.parseDouble(bulding2.getText().toString());

                    items.add(new items("b",b1,b2));
                }catch (Exception e){
                    e.printStackTrace();
                }

                items.add(new items("t",type));
                mLisstenner_earth.onfilterItem(items);
                mLisstenner_fella.onfilterItem(items);
                mLisstenner_Appart.onfilterItem(items);
                filter.dismiss();
                break;
            case R.id.governomo_sp:
                mypopupWindow_gov.showAsDropDown(municipality_sp,0,0);
                break;
            case R.id.municipality_sp:
                if (Govern_key==null){
                    Toast.makeText(this,"لم تختار المحافظة بعد",Toast.LENGTH_LONG).show();
                    break ;
                }
                mypopupWindow_mun.showAsDropDown(municipality_sp,0,0);
                break;
            case R.id.logout:

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("تسجيل الخروج")
                        .setMessage("هل تريد تسجيل الخروج")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth=FirebaseAuth.getInstance();
                                auth.signOut();
                                startActivity(new Intent(MainActivity.this,Login_activity.class));
                                finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("إلغاء", null)
                        .setIcon(R.drawable.ic_logout)
                        .show();

                break;

        }
    }

     BottomSheetDialog filter;
    private void show_filterPop() {
        filter=new BottomSheetDialog(this);
        filter.setContentView(R.layout.popup_filter);
        int pagenb=mViewPager.getCurrentItem();
        ConstraintLayout price_lay,room_lay,bath_lay,bulding_lay,earth_lay;
LinearLayout type_lay;
        RangeSeekBar roomsb,bathsb,buldingsb,earthsb,pricesb;
        type_lay=filter.findViewById(R.id.type_lay);
        price_lay=filter.findViewById(R.id.price_lay);
        room_lay=filter.findViewById(R.id.room_lay);
        bath_lay=filter.findViewById(R.id.bath_lay);
        bulding_lay=filter.findViewById(R.id.bulding_lay);
        earth_lay=filter.findViewById(R.id.earth_lay);

        room1=filter.findViewById(R.id.room_nb);
        price1=filter.findViewById(R.id.price_nb);
        bath1=filter.findViewById(R.id.bath_nb);
        bulding1=filter.findViewById(R.id.bulding_nb);
        earth1=filter.findViewById(R.id.earth_nb);

        room2=filter.findViewById(R.id.room_nb2);
        price2=filter.findViewById(R.id.price_nb2);
        bath2=filter.findViewById(R.id.bath_nb2);
        bulding2=filter.findViewById(R.id.bulding_nb2);
        earth2=filter.findViewById(R.id.earth_nb2);

        type1=filter.findViewById(R.id.type1);
        type2=filter.findViewById(R.id.type2);
        save=filter.findViewById(R.id.save);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        save.setOnClickListener(this);

        earthsb=filter.findViewById(R.id.seek_bar_earth);
        roomsb=filter.findViewById(R.id.seek_bar_room);
        bathsb=filter.findViewById(R.id.seek_bar_bath);
        buldingsb=filter.findViewById(R.id.seek_bar_bulding);
        pricesb=filter.findViewById(R.id.seek_bar_price);
        earthsb.setOnRangeChangedListener(this);
        roomsb.setOnRangeChangedListener(this);
        bathsb.setOnRangeChangedListener(this);
        buldingsb.setOnRangeChangedListener(this);
        pricesb.setOnRangeChangedListener(this);
        if (pagenb==1){
            type_lay.setVisibility(View.GONE);

        }
        if (pagenb==2){//earth
            room_lay.setVisibility(View.GONE);
            bulding_lay.setVisibility(View.GONE);
            bath_lay.setVisibility(View.GONE);
        }
        if (pagenb==0){
            type_lay.setVisibility(View.GONE);
            earth_lay.setVisibility(View.GONE);
        }


    }


    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }
    Button Governorate_sp,  municipality_sp;
   public static String Profider_email="",Profider_address="";
    private void getProvider_inf() {
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            return;
        }
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("Users");
        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Profider_email=dataSnapshot.child("email").getValue(String.class);
                            Profider_address=dataSnapshot.child("address").getValue(String.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lissenner_inti();
        getProvider_inf();
        /****************************definitions*****************************/
        fragmentManager = getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
//        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#56ce8b"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#504E4E"), Color.parseColor("#131313"));

        tabLayout.setupWithViewPager(mViewPager);


        ImageView menu=findViewById(R.id.menu);
        menu.setOnClickListener(this);
        ImageView filter=findViewById(R.id.filter);
        filter.setOnClickListener(this);
         Governorate_sp=findViewById(R.id.governomo_sp);
         municipality_sp=findViewById(R.id.municipality_sp);
        Governorate_sp.setOnClickListener(this);
        municipality_sp.setOnClickListener(this);
        FetchAllData();



    }

    private void Lissenner_inti() {
        mLisstenner_earth=new onsleactGoverment() {

            @Override
            public void onselectItem(String gov, String mun) {

            }

            @Override
            public void onfilterItem(List<items> items) {

            }


            @Override
            public void onfiltertype(int type) {

            }
        };
        mLisstenner_fella=new onsleactGoverment() {

            @Override
            public void onselectItem(String gov, String mun) {

            }

            @Override
            public void onfilterItem(List<items> items) {

            }


            @Override
            public void onfiltertype(int type) {

            }
        };

        mLisstenner_Appart=new onsleactGoverment() {

            @Override
            public void onselectItem(String gov, String mun) {

            }

            @Override
            public void onfilterItem(List<items> items) {

            }


            @Override
            public void onfiltertype(int type) {

            }
        };

    }

    PopupWindow mypopupWindow_gov ,mypopupWindow_mun;
    private void FetchAllData() {


        mypopupWindow_gov=setPopUpWindow();



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLisstenner_earth.onselectItem(null,null);
        mLisstenner_fella.onselectItem(null,null);
        mLisstenner_Appart.onselectItem(null,null);
    }

   public static String Govern_key=null;
    public static  String Mun_key=null;
    FirebaseDatabase database;
    private PopupWindow setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.spinnerlistitems_layout, null);
        final List<Governorate> mlist=new ArrayList<>();
        final  Gover_adapter adapter=new Gover_adapter(MainActivity.this, mlist, new Gover_adapter.Onselected() {
            @Override
            public void onitemselect(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
               if (governorate==null){
                   Govern_key=null;
                   Governorate_sp.setText(R.string.all);

               }else {
                   Govern_key=governorate.getUid();
                   Governorate_sp.setText(governorate.getName());
                   mypopupWindow_mun= setPopUpWindowmun();

                 //  Toast.makeText(getApplicationContext(),governorate.getName(),Toast.LENGTH_SHORT).show();

               }

                Mun_key=null;
                mLisstenner_earth.onselectItem(Govern_key,Mun_key);
                mLisstenner_fella.onselectItem(Govern_key,Mun_key);
                mLisstenner_Appart.onselectItem(Govern_key,Mun_key);

                municipality_sp.setText(R.string.um);


                mypopupWindow_gov.dismiss();
            }

            @Override
            public void onEdite(final com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
                final BottomSheetDialog dialog=new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(R.layout.popup_add_new);
                final TextView name=dialog.findViewById(R.id.name);
                name.setText(governorate.getName());
                LinearLayout edit_ = dialog.findViewById(R.id.add);
                edit_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().isEmpty()){
                            name.setError(name.getHint());
                        }
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("Governorate");

                        reference.child(governorate.getUid()).child("name")
                                .setValue(name.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        RecyclerView RV=view1.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
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

                try {
                    Governorate governorate=dataSnapshot.getValue(Governorate.class);
                    governorate.setUid(dataSnapshot.getKey());
                    for (Governorate g:mlist
                    ) {
                        if (g.getUid().equals(governorate.getUid()))
                        {
                            mlist.remove(g);
                            mlist.add(governorate);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }catch (Exception e){

                }


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

        TextView all=view1.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Govern_key=null;
                Governorate_sp.setText(R.string.all);
                Mun_key=null;
                mLisstenner_earth.onselectItem(Govern_key,Mun_key);
                mLisstenner_fella.onselectItem(Govern_key,Mun_key);
                mLisstenner_Appart.onselectItem(Govern_key,Mun_key);

                municipality_sp.setText(R.string.um);


                mypopupWindow_gov.dismiss();



            }
        });
        LinearLayout add1=view1.findViewById(R.id.add);
        if (!isAdmin()){
            add1.setVisibility(View.GONE);
        }
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog=new BottomSheetDialog(MainActivity.this);
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
        return new PopupWindow(view1,400, RelativeLayout.LayoutParams.WRAP_CONTENT, true);


    }
    private boolean isAdmin() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if (FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals("+213672886642")
                    || FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals("+971505555017")){  //
                return true;
            }
        }
        return false;
    }
    private PopupWindow setPopUpWindowmun() {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.spinnerlistitems_layout, null);
        final List<Governorate> mlist=new ArrayList<>();
        final  Gover_adapter adapter=new Gover_adapter(MainActivity.this, mlist, new Gover_adapter.Onselected() {
            @Override
            public void onitemselect(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
                if (governorate==null){
                    Mun_key=null;
                    municipality_sp.setText(R.string.all);

                }else {
                    Mun_key=governorate.getUid();
                    municipality_sp.setText(governorate.getName());

                }

                mLisstenner_earth.onselectItem(Govern_key,Mun_key);
                mLisstenner_fella.onselectItem(Govern_key,Mun_key);
                mLisstenner_Appart.onselectItem(Govern_key,Mun_key);

                mypopupWindow_mun.dismiss();
            }

            @Override
            public void onEdite(final com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate governorate) {
                final BottomSheetDialog dialog=new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(R.layout.popup_add_new);
                final TextView name=dialog.findViewById(R.id.name);
                name.setText(governorate.getName());
                LinearLayout edit_ = dialog.findViewById(R.id.add);
                edit_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().isEmpty()){
                            name.setError(name.getHint());
                        }
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("Governorate")
                                .child(Govern_key).child("Municipality");

                        reference.child(governorate.getUid()).child("name")
                                .setValue(name.getText().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        RecyclerView RV=view1.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
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
                Governorate governorate=dataSnapshot.getValue(Governorate.class);
                governorate.setUid(dataSnapshot.getKey());
                for (Governorate g:mlist
                ) {
                    if (g.getUid().equals(governorate.getUid()))
                    {
                        mlist.remove(g);
                        mlist.add(governorate);
                        adapter.notifyDataSetChanged();
                    }
                }
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

        TextView all=view1.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mun_key=null;
                municipality_sp.setText(R.string.all);

                mLisstenner_earth.onselectItem(Govern_key,Mun_key);
                mLisstenner_fella.onselectItem(Govern_key,Mun_key);
                mLisstenner_Appart.onselectItem(Govern_key,Mun_key);

                mypopupWindow_mun.dismiss();



            }
        });


        LinearLayout add1=view1.findViewById(R.id.add);
        if (!isAdmin()){
            add1.setVisibility(View.GONE);
        }
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog=new BottomSheetDialog(MainActivity.this);
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
        return new PopupWindow(view1,400, RelativeLayout.LayoutParams.WRAP_CONTENT, true);


    }
}
