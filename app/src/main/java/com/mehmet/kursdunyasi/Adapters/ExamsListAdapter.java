package com.mehmet.kursdunyasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehmet.kursdunyasi.Models.ExamsListModel;
import com.mehmet.kursdunyasi.R;

import java.util.List;

public class ExamsListAdapter extends BaseAdapter {

    List<ExamsListModel> list;
    Context context;

    public ExamsListAdapter(List<ExamsListModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view= LayoutInflater.from(context).inflate(R.layout.exams_list_layoutlayout,viewGroup,false);

        TextView examNameText=view.findViewById(R.id.examNameText);
        ImageView examIconImage=view.findViewById(R.id.examIconImage);

        examNameText.setText(list.get(i).getExamName());
        examIconImage.setImageResource(list.get(i).getImageResource());

        return view;
    }
}
