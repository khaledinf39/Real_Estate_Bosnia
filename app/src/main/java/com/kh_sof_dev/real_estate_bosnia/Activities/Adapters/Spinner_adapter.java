package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;


import android.app.Activity;
import android.util.Log;
import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate;
import com.kh_sof_dev.real_estate_bosnia.R;

import java.util.List;

public class Spinner_adapter extends ArrayAdapter<Governorate> {

    LayoutInflater flater;

    public Spinner_adapter(Activity context,int resouceId, int textviewId, List<Governorate> list){

        super(context,resouceId,textviewId,list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Governorate rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.spinnerlistitems_layout,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.name);



        LinearLayout add_btn = (LinearLayout) rowview.findViewById(R.id.add);
       if (position==getCount()-1){
           Log.d("count",getCount()+"   " +position);
           add_btn.setVisibility(View.VISIBLE);
           add_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final BottomSheetDialog dialog=new BottomSheetDialog(getContext());
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
       }else {
           txtTitle.setText(rowItem.getName());
           add_btn.setVisibility(View.GONE);
       }


//        ImageView imageView = (ImageView) rowview.findViewById(R.id.icon);
//        imageView.setImageResource(rowItem.getImageId());

        return rowview;
    }
}