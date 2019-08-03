package com.mehmet.kursdunyasi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.VideoUploadPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;
import com.mehmet.kursdunyasi.Utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class TeacherAddVideoFragment extends Fragment {

    View view;
    TextView teacherAddVideoCancelText,teacherAddVideoAcceptText,teacherAddVideoTitleText;
    EditText teacherAddVideoTitleEdittext;
    Button selectCourseVideoButton;
    ImageView videoCoverImageView;
    Uri videoPath;
    SharedPreferences sharedPreferences,sharedPreferences2;
    String token,courseTitle;
    int education_id;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_add_video, container, false);

        define();

        return view;
    }

    private void define(){

        teacherAddVideoCancelText=view.findViewById(R.id.teacherAddVideoCancelText);
        teacherAddVideoAcceptText=view.findViewById(R.id.teacherAddVideoAcceptText);
        teacherAddVideoTitleText=view.findViewById(R.id.teacherAddVideoTitleText);
        teacherAddVideoTitleEdittext=view.findViewById(R.id.teacherAddVideoTitleEdittext);
        selectCourseVideoButton=view.findViewById(R.id.selectCourseVideoButton);
        videoCoverImageView=view.findViewById(R.id.teacherAddvideoCoverImageView);

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);

        sharedPreferences2=getContext().getSharedPreferences("coursetitle",0);
        courseTitle=sharedPreferences.getString("title",null);

        if(getArguments()!=null){
            education_id =getArguments().getInt("data");
        }

        teacherAddVideoTitleText.setText(courseTitle);
        Log.i("courseToken",token+"----"+education_id);

        selectCourseVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openVideoGallery();
            }
        });

        teacherAddVideoAcceptText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addVideo();
            }
        });

        teacherAddVideoCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherVideosFragment());
            }
        });

    }

    private void addVideo() {


        File file = FileUtils.getFile(getContext(), videoPath);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), file);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("video", file.getName(), videoBody);

        OkHttpClient.Builder httpbuilder = new OkHttpClient.Builder()
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(50, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpbuilder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        RestApi client = retrofit.create(RestApi.class);

        RequestBody tokenSend = RequestBody.create(MultipartBody.FORM, token);
        RequestBody videoTitleBody = RequestBody.create(MultipartBody.FORM, teacherAddVideoTitleEdittext.getText().toString());
        RequestBody educationIdBody = RequestBody.create(MultipartBody.FORM, education_id + "");

        Log.i("datasadd", token + "--" + "--" + teacherAddVideoTitleEdittext.getText().toString() + "--" + education_id + "\n" + vFile);


        Call<VideoUploadPojo> call = client.teacherAddVideo(tokenSend, videoTitleBody, educationIdBody, vFile);
        call.enqueue(new Callback<VideoUploadPojo>() {
            @Override
            public void onResponse(Call<VideoUploadPojo> call, Response<VideoUploadPojo> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), "Video Eklendi ", Toast.LENGTH_SHORT).show();
                    ChangeFragment changeFragment = new ChangeFragment(getContext());
                    changeFragment.change(new TeacherCoursesFragment());
                } else {

                    Toast.makeText(getContext(), "Video Atılamadı" + response.message() + "" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VideoUploadPojo> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void openVideoGallery(){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==REQUEST_TAKE_GALLERY_VIDEO && resultCode==RESULT_OK && data!=null){

            videoPath=data.getData();

            Glide.with(getContext())
                    .load(videoPath) // or URI/path
                    .into(videoCoverImageView); //imageview to set thumbnail to

        }
        else {

            Toast.makeText(getContext(), "Hiçbir Medya Seçilmedi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_TAKE_GALLERY_VIDEO:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);

                } else {

                    Toast.makeText(getContext(), "Galeri Erişim İzni Verilmedi!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
