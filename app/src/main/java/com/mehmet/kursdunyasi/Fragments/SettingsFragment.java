package com.mehmet.kursdunyasi.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.R;


public class SettingsFragment extends Fragment {

    View view;

    ImageView settingsCancel;
    Button passwordSettingsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_settings, container, false);

        define();

        return view;
    }

    private void define(){

        settingsCancel=view.findViewById(R.id.settingsCancel);
        passwordSettingsButton=view.findViewById(R.id.passwordSettingsButton);

        settingsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new ProfileFragment());
            }
        });

        passwordSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new PasswordSettingsFragment());
            }
        });

    }

}
