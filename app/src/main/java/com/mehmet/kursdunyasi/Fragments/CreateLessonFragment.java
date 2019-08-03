package com.mehmet.kursdunyasi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.CategoriesPojo;
import com.mehmet.kursdunyasi.Models.CourseLevelPojo;
import com.mehmet.kursdunyasi.Models.VideoUploadPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.ManagerAll;
import com.mehmet.kursdunyasi.RestApi.RestApi;
import com.mehmet.kursdunyasi.Utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;


public class CreateLessonFragment extends Fragment {

    View view;
    EditText courseTitleEdittext,courseDescriptionEdittext,courseKeywordsEdittext,videoTitleEdittext;
    Spinner courseLevelSpinner,courseCategorySpinner;
    Button selectCoverImageButton,selectCourseVideoButton;
    ImageView courseCoverImage,videoCoverImageView;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 2;
    Uri filePath,videoPath;
    Bitmap bitmap;
    TextView createLessonAcceptButton,createLessonCancel;
    String title,category,decription,keywords,level,token;
    SharedPreferences sharedPreferences;
    ProgressBar progresBarCreateLesson;
    LinearLayout createLessonLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_create_lesson, container, false);

        define();
        getCategorySniperValues();
        getLevelSnipperValues();

        return view;
    }

    private void define(){

        courseTitleEdittext=view.findViewById(R.id.courseTitleEdittext);
        courseDescriptionEdittext=view.findViewById(R.id.courseDescriptionEdittext);
        courseKeywordsEdittext=view.findViewById(R.id.courseKeywordsEdittext);
        courseLevelSpinner=view.findViewById(R.id.courseLevelSpinner);
        courseCategorySpinner=view.findViewById(R.id.courseCategorySpinner);
        selectCoverImageButton=view.findViewById(R.id.selectCoverImageButton);
        selectCourseVideoButton=view.findViewById(R.id.selectCourseVideoButton);
        courseCoverImage=view.findViewById(R.id.courseCoverImage);
        videoCoverImageView=view.findViewById(R.id.videoCoverImageView);
        createLessonAcceptButton=view.findViewById(R.id.createLessonAcceptButton);
        createLessonCancel=view.findViewById(R.id.createLessonCancel);
        progresBarCreateLesson=view.findViewById(R.id.progresBarCreateLesson);
        createLessonLinearLayout=view.findViewById(R.id.createLessonLinearLayout);
        videoTitleEdittext=view.findViewById(R.id.videoTitleEdittext);


        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);

        createLessonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherAccountFragment());
            }
        });


        selectCoverImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        selectCourseVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideoGallery();
            }
        });

        createLessonAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nullValuesControls()){

                    progresBarCreateLesson.setVisibility(View.VISIBLE);
                    courseTitleEdittext.setEnabled(false);
                    courseDescriptionEdittext.setEnabled(false);
                    courseKeywordsEdittext.setEnabled(false);
                    courseLevelSpinner.setEnabled(false);
                    courseCategorySpinner.setEnabled(false);
                    selectCoverImageButton.setEnabled(false);
                    selectCourseVideoButton.setEnabled(false);
                    createLessonAcceptButton.setEnabled(false);
                    videoTitleEdittext.setEnabled(false);


                    uploadVideo();
                }
                else{
                    nullValuesControls();
                }


            }
        });

    }


    public void openGallery(){

        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openVideoGallery(){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_FROM_GALLERY && resultCode== RESULT_OK ){
            if(data !=null)
            {
                try {

                    filePath=data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                    courseCoverImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(requestCode==REQUEST_TAKE_GALLERY_VIDEO && resultCode==RESULT_OK && data!=null){

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
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {

                    Toast.makeText(getContext(), "Galeri Erişim İzni Verilmedi!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void uploadVideo(){


        File file= FileUtils.getFile(getContext(),videoPath);

        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), file);

        MultipartBody.Part vFile = MultipartBody.Part.createFormData("video", file.getName(), videoBody);

        File imageFile= FileUtils.getFile(getContext(),filePath);

        RequestBody imagePath=RequestBody.create(MediaType.parse("image/*"), imageFile);

        MultipartBody.Part imageBody =
                MultipartBody.Part.createFormData("cover", imageFile.getName(), imagePath);

       /* final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build();*/

        Log.i("values","FilePath: "+filePath+"\nVideoPath "+videoPath+"\nİmageSend: "+imageBody+"\n VideoBody"+vFile);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        RestApi client=retrofit.create(RestApi.class);

        RequestBody tokenSend=RequestBody.create(MultipartBody.FORM,token);
        RequestBody categoryBody=RequestBody.create(MultipartBody.FORM,courseCategorySpinner.getSelectedItem().toString());
        RequestBody levelBody=RequestBody.create(MultipartBody.FORM,courseLevelSpinner.getSelectedItem().toString());
        RequestBody titleBody=RequestBody.create(MultipartBody.FORM,courseTitleEdittext.getText().toString());
        RequestBody descriptionBody=RequestBody.create(MultipartBody.FORM,courseDescriptionEdittext.getText().toString());
        RequestBody keywordsBody=RequestBody.create(MultipartBody.FORM,courseKeywordsEdittext.getText().toString());
        RequestBody videoTitleBody=RequestBody.create(MultipartBody.FORM,videoTitleEdittext.getText().toString());

        Call<VideoUploadPojo> call=client.uploadVideo(tokenSend,titleBody,categoryBody,descriptionBody,levelBody,keywordsBody,videoTitleBody,imageBody,vFile);
        call.enqueue(new Callback<VideoUploadPojo>() {
            @Override
            public void onResponse(Call<VideoUploadPojo> call, Response<VideoUploadPojo> response) {

                if(response.isSuccessful()){

                    progresBarCreateLesson.setVisibility(View.INVISIBLE);

                    Toast.makeText(getContext(), "Kurs Oluşturuldu: ", Toast.LENGTH_SHORT).show();
                    ChangeFragment changeFragment=new ChangeFragment(getContext());
                    changeFragment.change(new TeacherAddVideoFragment());

                }
                else{

                    progresBarCreateLesson.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Atılamadı: "+response.code()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VideoUploadPojo> call, Throwable t) {

                progresBarCreateLesson.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Bağlantı Hatası"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("errorwhat",t.getMessage());
            }
        });

    }

    public void getCategorySniperValues(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
        RestApi client=retrofit.create(RestApi.class);

        Call<List<CategoriesPojo>> call=client.getspinnerValues();
        call.enqueue(new Callback<List<CategoriesPojo>>() {
            @Override
            public void onResponse(Call<List<CategoriesPojo>> call, Response<List<CategoriesPojo>> response) {

                if(response.isSuccessful()){

                    List<CategoriesPojo> list=response.body();
                    List<String> categoryList=new ArrayList<>();
                    categoryList.add("Kategori Seç");

                    for(CategoriesPojo c:list){

                        categoryList.add(c.getCategories());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_item, categoryList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        courseCategorySpinner.setAdapter(adapter);

                    }

                }

            }

            @Override
            public void onFailure(Call<List<CategoriesPojo>> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getLevelSnipperValues(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
        RestApi client=retrofit.create(RestApi.class);

        Call<List<CourseLevelPojo>> call=client.getLevelspinnerValues();
        call.enqueue(new Callback<List<CourseLevelPojo>>() {
            @Override
            public void onResponse(Call<List<CourseLevelPojo>> call, Response<List<CourseLevelPojo>> response) {

                if(response.isSuccessful()){

                    List<CourseLevelPojo> list=response.body();
                    List<String> courseLevelList=new ArrayList<>();
                    courseLevelList.add("Seviye Seç");

                    for(CourseLevelPojo c:list){

                        courseLevelList.add(c.getLevel());

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, courseLevelList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        courseLevelSpinner.setAdapter(adapter);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<CourseLevelPojo>> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean nullValuesControls(){

        if(courseTitleEdittext.getText().toString().equals("")){
            Toast.makeText(getContext(), "Kurs Başlığı Boş Girilemez.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(courseDescriptionEdittext.getText().toString().equals("")){
            Toast.makeText(getContext(), "Kurs Tanımı Boş Girilemez.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(courseKeywordsEdittext.getText().toString().equals("")){
            Toast.makeText(getContext(), "Anahtar Kelimeler Boş Girilemez.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(courseCategorySpinner.getSelectedItemPosition()==0){
            Toast.makeText(getContext(), "Lütfen Kurs Kategorisini Giriniz.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(courseLevelSpinner.getSelectedItemPosition()==0)
        {
            Toast.makeText(getContext(), "Lütfen Kurs Seviyesini Giriniz.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(filePath==null){
            Toast.makeText(getContext(), "Lütfen Kapak Resmini Seçiniz.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(videoPath==null){
            Toast.makeText(getContext(), "Lütfen Video Seçiniz.", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }


    }


}
