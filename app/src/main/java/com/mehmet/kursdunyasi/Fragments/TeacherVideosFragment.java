package com.mehmet.kursdunyasi.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.mehmet.kursdunyasi.Adapters.TeacherVideosAdapter;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.TeacherVideoPojo;
import com.mehmet.kursdunyasi.Models.VideoUploadPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TeacherVideosFragment extends Fragment {

    View view;
    VideoView teacherVideoView;
    ProgressBar teacherVideoProgress;
    ImageButton teacherPlayButton;
    SharedPreferences sharedPreferences;
    String token,username,folder,videoURL;
    int education_id;
    ListView teacherVideosListView;
    TeacherVideosAdapter adapter;
    List<TeacherVideoPojo> list;
    Button teacherAddVideoButton;
    ImageView teacherVideosCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teacher_videos, container, false);

        define();


        return view;
    }


    public void define(){

        teacherVideoView =view.findViewById(R.id.teacherVideoPlayer);
        teacherVideoProgress=view.findViewById(R.id.teacherVideoProgress);
        teacherPlayButton=view.findViewById(R.id.teacherPlayButton);
        teacherVideosListView=view.findViewById(R.id.teacherVideosListView);
        teacherAddVideoButton=view.findViewById(R.id.teacherAddVideoButton);
        teacherVideosCancel=view.findViewById(R.id.teacherVideosCancel);

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);
        username=sharedPreferences.getString("userName",null);

        if(getArguments()!=null){

             education_id =getArguments().getInt("data");
             folder=getArguments().getString("folder");
        }

            getVideos();

        teacherAddVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.sendData(new TeacherAddVideoFragment(),education_id,"");
            }
        });

        teacherVideosCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new TeacherCoursesFragment());
            }
        });

       deleteorUpdateVideo();

    }

    public void getVideos(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
        RestApi client=retrofit.create(RestApi.class);

        Call<List<TeacherVideoPojo>> call=client.getTeacherVideoLink(token, education_id);
        call.enqueue(new Callback<List<TeacherVideoPojo>>() {
            @Override
            public void onResponse(Call<List<TeacherVideoPojo>> call, final Response<List<TeacherVideoPojo>> response) {

                if(response.isSuccessful()){

                    teacherVideosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            videoURL=BaseUrl.URL+"/storage/uploads/video/"+username+"/"+folder+"/"+list.get(position).getName();

                            try {

                                if(!teacherVideoView.isPlaying()) {

                                    teacherVideoProgress.setVisibility(View.VISIBLE);

                                    Uri uri = Uri.parse(videoURL);
                                    teacherVideoView.setVideoURI(uri);
                                    teacherVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            teacherVideoProgress.setVisibility(View.INVISIBLE);
                                            teacherPlayButton.setImageResource(R.drawable.pausebutton32black);

                                        }
                                    });
                                }
                                else {
                                    teacherVideoProgress.setVisibility(View.INVISIBLE);
                                    teacherVideoView.pause();
                                    Toast.makeText(getContext(), teacherVideoView.getDuration()+"", Toast.LENGTH_SHORT).show();
                                    teacherPlayButton.setImageResource(R.drawable.playbutton32black);
                                }

                            }catch (Exception e){

                            }

                            teacherVideoView.requestFocus();
                            teacherVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {

                                    teacherVideoProgress.setVisibility(View.INVISIBLE);
                                    //mp.setLooping(true);
                                    teacherVideoView.start();
                                    teacherPlayButton.setImageResource(R.drawable.pausebutton32black);
                                }
                            });

                        }
                    });

                    teacherPlayButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(videoURL==null){

                                videoURL=BaseUrl.URL+"/storage/uploads/video/"+username+"/"+folder+"/"+response.body().get(0).getName();
                            }

                          Log.i("videourl",videoURL+" ?");

                            try {

                                if(!teacherVideoView.isPlaying()) {

                                    teacherVideoProgress.setVisibility(View.VISIBLE);

                                    Uri uri = Uri.parse(videoURL);
                                    teacherVideoView.setVideoURI(uri);
                                    teacherVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            teacherVideoProgress.setVisibility(View.INVISIBLE);
                                            teacherPlayButton.setImageResource(R.drawable.pausebutton32black);

                                        }
                                    });
                                }
                                else {
                                    teacherVideoProgress.setVisibility(View.INVISIBLE);
                                    teacherVideoView.pause();
                                    Toast.makeText(getContext(), teacherVideoView.getDuration()+"", Toast.LENGTH_SHORT).show();
                                    teacherPlayButton.setImageResource(R.drawable.playbutton32black);
                                }

                            }catch (Exception e){

                            }

                            teacherVideoView.requestFocus();
                            teacherVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {

                                    teacherVideoProgress.setVisibility(View.INVISIBLE);
                                    //mp.setLooping(true);
                                    teacherVideoView.start();
                                    teacherPlayButton.setImageResource(R.drawable.pausebutton32black);
                                }
                            });
                        }
                    });

                    list=new ArrayList<>();

                    list=response.body();
                    adapter=new TeacherVideosAdapter(getContext(),list);
                    teacherVideosListView.setAdapter(adapter);


                   // Log.i("videoinf",response.body().get(0).getName());

                }
                else {

                    Toast.makeText(getContext(), "İstek Atılamadı "+response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("allrequest",response.message()+" "+response.code());

                }
            }



            @Override
            public void onFailure(Call<List<TeacherVideoPojo>> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteorUpdateVideo(){

        teacherVideosListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());

                builderSingle.setTitle("İşlemi Seçiniz:");

                final ArrayAdapter<String> arrayAdapterDialog = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
                arrayAdapterDialog.add("Sil");
                arrayAdapterDialog.add("Düzenle");

                builderSingle.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapterDialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());

                        Retrofit.Builder builder=new Retrofit.Builder()
                                .baseUrl(BaseUrl.URL)
                                .addConverterFactory(GsonConverterFactory.create());

                        Retrofit retrofit=builder.build();
                        final RestApi client=retrofit.create(RestApi.class);

                        final int videoId=list.get(position).getId();

                        if(which==0) {
                            builderInner.setTitle("Videoyu Gerçekten Silmek İstiyor musunuz ?");

                            builderInner.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    Call<VideoUploadPojo> call=client.deleteTeacherVideo(token,videoId);
                                    call.enqueue(new Callback<VideoUploadPojo>() {
                                        @Override
                                        public void onResponse(Call<VideoUploadPojo> call, Response<VideoUploadPojo> response) {

                                            if(response.isSuccessful()){

                                                Toast.makeText(getContext(), "Video Silindi"+response.code(), Toast.LENGTH_SHORT).show();
                                                ChangeFragment changeFragment=new ChangeFragment(getContext());
                                                changeFragment.change(new TeacherAccountFragment());

                                            }
                                            else {
                                                Toast.makeText(getContext(), "Video Silinemedi"+response.code(), Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<VideoUploadPojo> call, Throwable t) {

                                            Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    dialog.dismiss();
                                }
                            });

                            builderInner.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                        }
                        else if(which==1){

                            final EditText editText=new EditText(getContext());
                            builderInner.setTitle("Videoyu Düzenle");
                            builderInner.setMessage(""+list.get(position).getTitle());
                            editText.setHint("Yeni Video Başlığını Giriniz");
                            builderInner.setView(editText);

                            builderInner.setPositiveButton("Düzenle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String title=editText.getText().toString();

                                    Call<VideoUploadPojo> call=client.updateTeacherVideo(token,videoId,title);
                                    call.enqueue(new Callback<VideoUploadPojo>() {
                                        @Override
                                        public void onResponse(Call<VideoUploadPojo> call, Response<VideoUploadPojo> response) {

                                            if(response.isSuccessful()){

                                                Toast.makeText(getContext(), "Video Düzenlendi", Toast.LENGTH_SHORT).show();
                                                ChangeFragment changeFragment=new ChangeFragment(getContext());
                                                changeFragment.change(new TeacherAccountFragment());
                                            }
                                            else {

                                                Toast.makeText(getContext(), "Hata"+response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<VideoUploadPojo> call, Throwable t) {

                                            Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    dialog.dismiss();
                                }
                            });

                            builderInner.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                        }

                        builderInner.show();
                    }
                });
                builderSingle.show();

                return false;
            }
        });

    }


}
