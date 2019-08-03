package com.mehmet.kursdunyasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehmet.kursdunyasi.Models.TeacherVideoPojo;
import com.mehmet.kursdunyasi.R;

import java.util.List;

public class TeacherVideosAdapter extends BaseAdapter {

    Context context;
    List<TeacherVideoPojo> list;

    public TeacherVideosAdapter(Context context, List<TeacherVideoPojo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.teacher_videos_layout,parent,false);

        ImageView teacherVideosPlayImage;
        TextView teacherVideosTextView,teacherVideosCounterTextView;

        teacherVideosPlayImage=convertView.findViewById(R.id.teacherVideosPlayImage);
        teacherVideosTextView=convertView.findViewById(R.id.teacherVideosTextView);
        teacherVideosCounterTextView=convertView.findViewById(R.id.teacherVideosCounterTextView);

        teacherVideosTextView.setText(list.get(position).getTitle());


            position+=1;
            teacherVideosCounterTextView.setText(""+position);


        return convertView;
    }
}
