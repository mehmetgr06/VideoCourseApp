package com.mehmet.kursdunyasi.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mehmet.kursdunyasi.Adapters.ExamsListAdapter;
import com.mehmet.kursdunyasi.Models.ExamsListModel;
import com.mehmet.kursdunyasi.R;

import java.util.ArrayList;
import java.util.List;


public class ExamsFragment extends Fragment {

    View view;
    SearchView exams_search_view;
    ListView examsListview;
    List<ExamsListModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_exams, container, false);

        define();
        addlist();

        return view;
    }

    public void define(){

        exams_search_view=view.findViewById(R.id.exams_search_view);
        examsListview=view.findViewById(R.id.examsListview);


    }

    public void addlist(){

        list=new ArrayList<>();
        ExamsListModel yds=new ExamsListModel("YDS",R.drawable.exam256);
        ExamsListModel ales=new ExamsListModel("Ales",R.drawable.exam256);
        ExamsListModel uds=new ExamsListModel("ÜDS",R.drawable.exam256);
        ExamsListModel lgs=new ExamsListModel("LGS",R.drawable.exam256);
        ExamsListModel dgs=new ExamsListModel("DGS",R.drawable.exam256);

        list.add(yds);
        list.add(ales);
        list.add(uds);
        list.add(lgs);
        list.add(dgs);

        ExamsListAdapter adapter=new ExamsListAdapter(list,getContext());
        examsListview.setAdapter(adapter);

    }

}
