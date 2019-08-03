package com.mehmet.kursdunyasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mehmet.kursdunyasi.Models.TeacherCoursesPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;

import java.util.List;

public class TeacherCoursesAdapter extends BaseAdapter {

    Context context;
    List<TeacherCoursesPojo> list;

    public TeacherCoursesAdapter(Context context, List<TeacherCoursesPojo> list) {
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

        convertView= LayoutInflater.from(context).inflate(R.layout.teacher_courses_layout,parent,false);

        ImageView teacherCoursesImageView;
        TextView teachaerCourseNameText;

        teacherCoursesImageView=convertView.findViewById(R.id.teacherCoursesImageView);
        teachaerCourseNameText=convertView.findViewById(R.id.teachaerCourseNameText);

        Glide.with(context).load(BaseUrl.URL+"/storage/uploads/cover/"+list.get(position).getCover_name()).into(teacherCoursesImageView);
        teachaerCourseNameText.setText(list.get(position).getTitle());


        return convertView;
    }
}
