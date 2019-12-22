package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Details;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Real_estate;
import com.kh_sof_dev.real_estate_bosnia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class appartement_adapter extends RecyclerView.Adapter<appartement_adapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Real_estate> mItems;

    private Context mContext;

    public appartement_adapter(Context context, List<Real_estate> names) {
        mItems = names;
        mContext = context;

    }
    private View mView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_fila, parent, false);
        mView=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.id.setText(mItems.get(position).getNb()+"");
        holder.price.setText(mItems.get(position).getPrice().toString()+" €");

        holder.room.setText(mItems.get(position).getRoom()+"");
        holder.bath.setText(mItems.get(position).getBath()+"");

        holder.earth.setVisibility(View.GONE);
        holder.earth_txt.setVisibility(View.GONE);

        holder.bulding.setText(mItems.get(position).getBuilding()+" m2");
        holder.place.setText(mItems.get(position).getLocation().getCity()+"");
        Picasso.with(mContext)
                .load(mItems.get(position).getImg1())
                .placeholder(mContext.getDrawable(R.drawable.img))
        .into(holder.img);
        if (mItems.get(position).getSolde()){
            holder
                    .solde.setVisibility(View.VISIBLE);
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

        TextView id,price,room,bath,earth,earth_txt,bulding,place;
ImageView img,solde;
        public ViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.nb);
            room=itemView.findViewById(R.id.room_nb);
            price=itemView.findViewById(R.id.price_nb);
            place=itemView.findViewById(R.id.place);

            bath=itemView.findViewById(R.id.bath_nb);
            bulding=itemView.findViewById(R.id.bulding_nb);
            earth=itemView.findViewById(R.id.earth_nb);
            earth_txt=itemView.findViewById(R.id.earth_txt);
            img=itemView.findViewById(R.id.img);
            solde=itemView.findViewById(R.id.is_solde);



        }
    }
}