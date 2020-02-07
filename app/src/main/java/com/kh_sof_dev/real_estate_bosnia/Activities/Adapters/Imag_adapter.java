package com.kh_sof_dev.real_estate_bosnia.Activities.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.Details;
import com.kh_sof_dev.real_estate_bosnia.Activities.Activities.MainActivity;
import com.kh_sof_dev.real_estate_bosnia.Activities.Classes.Governorate;
import com.kh_sof_dev.real_estate_bosnia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Imag_adapter extends RecyclerView.Adapter<Imag_adapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<String> mItems;

    private Context mContext;
    Onselected mlissener;
public interface Onselected{
    void onitemselect(int potions);


}
    public Imag_adapter(Context context, List<String> names, Onselected mlissener) {
        mItems = names;
        mContext = context;
        this.mlissener=mlissener;

    }
    private View mView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_image, parent, false);
        mView=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        try {
            Picasso.with(mContext)
                    .load(mItems.get(position))
                    .placeholder(mContext.getDrawable(R.drawable.img))
                    .into(holder.img);
        }catch (Exception e){
            e.printStackTrace();
        }


mView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    }
});


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("حذف  الصورة")
                        .setMessage("هل تريد حذف  الصورة ")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseStorage storage=FirebaseStorage.getInstance();

                                    final StorageReference refstr = storage.getReferenceFromUrl(mItems.get(position));
                                    refstr.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(mContext,
                                                        "تم حذف الصورة",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                mlissener.onitemselect(position);

                                mItems.remove(position);
                                    notifyDataSetChanged();



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
        });


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     ImageView img,delete;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            delete=itemView.findViewById(R.id.delete_btn);


        }
    }
}