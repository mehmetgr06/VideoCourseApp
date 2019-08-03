package com.mehmet.kursdunyasi.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mehmet.kursdunyasi.LoginActivity;
import com.mehmet.kursdunyasi.Models.UploadImagePojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {

    TextView userNameTextview;
    Button accountInformationsButton,personalInformationsButton,personalSettingsButton;
    View view;
    SharedPreferences sharedPreferences;
    CircleImageView profile_image_first;
    String token;
    ImageView logouticon;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        define();


        return view;

    }

    public void define(){

        userNameTextview=view.findViewById(R.id.nameSurnameTextView);
        accountInformationsButton =view.findViewById(R.id.accountInformationsButton);
        personalInformationsButton=view.findViewById(R.id.personalInformationsButton);
        profile_image_first=view.findViewById(R.id.profile_image_first);
        personalSettingsButton=view.findViewById(R.id.personalSettingsButton);
        logouticon=view.findViewById(R.id.logouticon);

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        String name=sharedPreferences.getString("name",null);
        String surname=sharedPreferences.getString("surname",null);
        token=sharedPreferences.getString("token",null);

        userNameTextview.setText(name+" "+surname);

        accountInformationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new AcountinformationsFragment());
            }
        });

        personalInformationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new PersonalInformationsFragment());
            }
        });

        personalSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new SettingsFragment());
            }
        });

        logouticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                LoginActivity.state=true;
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);

            }
        });


        getProfilePhoto();
    }

    public void getProfilePhoto(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://192.168.1.120:8000")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        RestApi client=retrofit.create(RestApi.class);

        Call<UploadImagePojo> call=client.getProfileImage(token);
        call.enqueue(new Callback<UploadImagePojo>() {
            @Override
            public void onResponse(Call<UploadImagePojo> call, Response<UploadImagePojo> response) {

                if(response.isSuccessful()) {

                    if(isAdded()) {
                        Glide.with(getContext()).load(BaseUrl.URL + "" + response.body().getPicture()).into(profile_image_first);
                    }
                }

            }

            @Override
            public void onFailure(Call<UploadImagePojo> call, Throwable t) {

            }
        });

    }


}
