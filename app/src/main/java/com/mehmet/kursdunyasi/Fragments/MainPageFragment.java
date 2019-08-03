package com.mehmet.kursdunyasi.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mehmet.kursdunyasi.R;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment

        View view= inflater.inflate(R.layout.fragment_main_page, container, false);

        return view;
    }


}
