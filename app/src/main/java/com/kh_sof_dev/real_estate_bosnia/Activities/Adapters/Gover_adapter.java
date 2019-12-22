package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

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

mView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mlissener.onitemselect(mItems.get(position));
    }
});



    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);



        }
    }
}