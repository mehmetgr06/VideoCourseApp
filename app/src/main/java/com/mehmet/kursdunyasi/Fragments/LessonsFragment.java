package com.mehmet.kursdunyasi.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.R;


public class LessonsFragment extends Fragment {

    View view;

    Button teacher_account_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_lessons, container, false);

        define();

        return view;
    }


    public void define(){

        teacher_account_button=view.findViewById(R.id.teacher_account_button);



        teacher_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherAccountFragment());
            }
        });
    }


}
