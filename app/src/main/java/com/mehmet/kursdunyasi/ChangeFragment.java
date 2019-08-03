package com.mehmet.kursdunyasi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by MEHMET on 26.06.2019.
 */

public class ChangeFragment {

    private Context context;

    public ChangeFragment(Context context){

        this.context=context;
    }

    public void change(Fragment fragment){

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void sendData(Fragment fragment,int data,String folder){

        Bundle bundle=new Bundle();
        bundle.putInt("data",data);
        bundle.putString("folder",folder);
        fragment.setArguments(bundle);

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()

                .replace(R.id.frameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }



}
