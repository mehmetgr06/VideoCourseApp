package com.mehmet.kursdunyasi.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.UploadImagePojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TeacherAccountFragment extends Fragment {

    View view;

    ImageView teacherAccountCancel;
    CircleImageView profile_image_teacher;
    TextView countStudentText,countLessonText,teacherRatingText,teacherNameText;
    Button createLessonButton,techerLessonsButton,teacherMessagesButton,teacherQuestionsButton,teacherSettingButton;
    SharedPreferences sharedPreferences;
    String token,name,surname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_account, container, false);

        define();
        getProfileImage();

        return view;
    }

    public void define(){

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);
        name=sharedPreferences.getString("name",null);
        surname=sharedPreferences.getString("surname",null);

        teacherAccountCancel=view.findViewById(R.id.teacherAccountCancel);
        profile_image_teacher=view.findViewById(R.id.profile_image_teacher);
        countStudentText=view.findViewById(R.id.countStudentText);
        countLessonText=view.findViewById(R.id.countLessonText);
        teacherRatingText=view.findViewById(R.id.teacherRatingText);
        createLessonButton=view.findViewById(R.id.createLessonButton);
        techerLessonsButton=view.findViewById(R.id.techerLessonsButton);
        teacherMessagesButton=view.findViewById(R.id.teacherMessagesButton);
        teacherQuestionsButton=view.findViewById(R.id.teacherQuestionsButton);
        teacherSettingButton=view.findViewById(R.id.teacherSettingButton);
        teacherNameText=view.findViewById(R.id.teacherNameText);

        teacherNameText.setText(name+" "+surname);

        teacherAccountCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new LessonsFragment());
            }
        });

        createLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new CreateLessonFragment());
            }
        });

        techerLessonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherCoursesFragment());
            }
        });

        teacherMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherVideosFragment());
            }
        });

    }

    private void getProfileImage(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        RestApi client=retrofit.create(RestApi.class);

        Call<UploadImagePojo> call=client.getProfileImage(token);
        call.enqueue(new Callback<UploadImagePojo>() {
            @Override
            public void onResponse(Call<UploadImagePojo> call, Response<UploadImagePojo> response) {

                if(response.isSuccessful()){

                    Glide.with(getContext()).load(BaseUrl.URL+""+response.body().getPicture()).into(profile_image_teacher);
                    Log.i("profilres",BaseUrl.URL+""+response.body().getPicture()+"-->"+response.code());

                    countLessonText.setText(response.body().getCount()+"");
                }
            }

            @Override
            public void onFailure(Call<UploadImagePojo> call, Throwable t) {

            }
        });

    }

}
