package com.mehmet.kursdunyasi.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mehmet.kursdunyasi.Adapters.TeacherCoursesAdapter;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.TeacherCoursesPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TeacherCoursesFragment extends Fragment {

    View view;
    TeacherCoursesAdapter adapter;
    String token;
    GridView gridView;
    SharedPreferences sharedPreferences,sharedPreferences2;
    List<TeacherCoursesPojo> list;
    ImageView teacherCoursesCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_courses, container, false);

        define();

        return view;
    }

    public void define(){

        gridView=view.findViewById(R.id.gridviewTeacherCourses);
        teacherCoursesCancel=view.findViewById(R.id.teacherCoursesCancel);

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);

        getTeacherCourses();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int videoID=list.get(position).getEducation_id();
                String folder=list.get(position).getFolder();

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.sendData(new TeacherVideosFragment(),videoID,folder);

                sharedPreferences2=getContext().getSharedPreferences("coursetitle",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("title",list.get(position).getTitle());
                editor.commit();

            }
        });

        teacherCoursesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherAccountFragment());
            }
        });


    }

    public void getTeacherCourses(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
        RestApi client=retrofit.create(RestApi.class);

        Call<List<TeacherCoursesPojo>> call=client.getTeacherCourses(token);
        call.enqueue(new Callback<List<TeacherCoursesPojo>>() {
            @Override
            public void onResponse(Call<List<TeacherCoursesPojo>> call, Response<List<TeacherCoursesPojo>> response) {

                if(response.isSuccessful()){

                    list=response.body();
                    adapter=new TeacherCoursesAdapter(getContext(),list);
                    gridView.setAdapter(adapter);

                }
                else {

                    Toast.makeText(getContext(), "İstek Atılamadı"+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<TeacherCoursesPojo>> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("conecctionerror",t.getMessage());
            }
        });

    }


}
