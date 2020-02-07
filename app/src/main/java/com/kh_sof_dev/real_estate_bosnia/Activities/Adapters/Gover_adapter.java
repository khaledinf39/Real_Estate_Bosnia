package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Gover_adapter extends RecyclerView.Adapter<Gover_adapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Governorate> mItems;

    private Context mContext;
    Onselected mlissener;
public interface Onselected{
    void onitemselect(Governorate governorate);
    void onEdite(Governorate governorate);

}
    public Gover_adapter(Context context, List<Governorate> names,Onselected mlissener) {
        mItems = names;
        mContext = context;
        this.mlissener=mlissener;

    }
    private View mView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_governomont, parent, false);
        mView=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        if (!mItems.get(position).getName().isEmpty()){
            holder.name.setText(mItems.get(position).getName()+"");

        }

        holder.all.setVisibility(View.GONE);

//        if (position==0){
//  holder.all.setVisibility(View.VISIBLE);
//  holder.all.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//          mlissener.onitemselect(null);
//      }
//  });
//}
mView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mlissener.onitemselect(mItems.get(position));
    }
});


        if (!isAdmin()){
            holder.edite.setVisibility(View.GONE);
        }
        holder.edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mlissener.onEdite(mItems.get(position));
            }
        });

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
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,edite,all;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            all=itemView.findViewById(R.id.all);
             edite=(TextView) itemView.findViewById(R.id.edit);


        }
    }
}