package com.kh_sof_dev.real_estate_bosnia.Activities.Classes;

import com.kh_sof_dev.real_estate_bosnia.R;

import java.util.ArrayList;
import java.util.List;

public class Bosnia {
    private String wilawa;
    private int city;



    public Bosnia(String wilawa, int city) {
        this.wilawa = wilawa;
        this.city = city;
    }





    public static List<Bosnia> Getwilawa(){
         List<Bosnia> bosniaList=new ArrayList<>();
         bosniaList.add(new Bosnia("a", R.array.markArray2));

        return bosniaList;
    }
}
