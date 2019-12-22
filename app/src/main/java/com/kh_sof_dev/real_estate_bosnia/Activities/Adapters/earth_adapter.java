package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Details;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class earth_adapter extends RecyclerView.Adapter<earth_adapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Real_estate> mItems;

    private Context mContext;

    public earth_adapter(Context context, List<Real_estate> names) {
        mItems = names;
        mContext = context;

    }
    private View mView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_earth, parent, false);
        mView=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.id.setText(mItems.get(position).getNb()+"");
        holder.price.setText(mItems.get(position).getPrice().toString()+" €");

        holder.earth.setText(mItems.get(position).getEarth()+" m2");
       holder.place.setText(mItems.get(position).getLocation().getCity()+"");
        try {
            Picasso.with(mContext)
                    .load(mItems.get(position).getImagesURL().get(0))
                    .placeholder(mContext.getDrawable(R.drawable.img))
                    .into(holder.img);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (mItems.get(position).getSolde()){
            holder
                    .solde.setVisibility(View.VISIBLE);
        }
if (mItems.get(position).getEarth_type()==1){
    holder.type.setText(mContext.getString(R.string.type1));

}else {
    holder.type.setText(mContext.getString(R.string.type2));

}
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.real_estate=mItems.get(position);
                mContext.startActivity(new Intent(mContext,Details.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id,price,earth,place;
ImageView img,solde;
Button type;
        public ViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.nb);
            price=itemView.findViewById(R.id.price_nb);
            place=itemView.findViewById(R.id.place);
            earth=itemView.findViewById(R.id.earth_nb);
            img=itemView.findViewById(R.id.img);
            type=itemView.findViewById(R.id.type);
            solde=itemView.findViewById(R.id.is_solde);



        }
    }
}