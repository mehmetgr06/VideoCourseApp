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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.Models.ProfileInfoPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalInformationsFragment extends Fragment {

    View view;
    TextView profilePersonalBackText,profilePersonalUpdateText;
    EditText profileJobEdittext,profileExpertiseEdittext,profileWebSiteEdittext,profileBioEdiitext;
    EditText profileLocationEdittext,profileFacebookEdittext,profileLinkedinEdittext,profileTwitterEdittext;
    SharedPreferences sharedPreferences,sharedPreferences2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_personal_informations, container, false);

        define();
        getSharedValues();

        return view;
    }

    public void define(){

        profilePersonalBackText=view.findViewById(R.id.profilePersonalBackText);
        profilePersonalUpdateText=view.findViewById(R.id.profilePersonalUpdateText);
        profileJobEdittext=view.findViewById(R.id.profileJobEdittext);
        profileExpertiseEdittext=view.findViewById(R.id.profileExpertiseEdittext);
        profileWebSiteEdittext=view.findViewById(R.id.profileWebSiteEdittext);
        profileBioEdiitext=view.findViewById(R.id.profileBioEdiitext);
        profileLocationEdittext=view.findViewById(R.id.profileLocationEdittext);
        profileFacebookEdittext=view.findViewById(R.id.profileFacebookEdittext);
        profileLinkedinEdittext=view.findViewById(R.id.profileLinkedinEdittext);
        profileTwitterEdittext=view.findViewById(R.id.profileTwitterEdittext);


        sharedPreferences2=getContext().getSharedPreferences("giris",0);
        final String token=sharedPreferences2.getString("token",null);

        profilePersonalUpdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences=getContext().getSharedPreferences("personal",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("job",profileJobEdittext.getText().toString());
                editor.putString("expertise",profileExpertiseEdittext.getText().toString());
                editor.putString("website",profileWebSiteEdittext.getText().toString());
                editor.putString("biography",profileBioEdiitext.getText().toString());
                editor.putString("location",profileLocationEdittext.getText().toString());
                editor.putString("facebook",profileFacebookEdittext.getText().toString());
                editor.putString("linkedin",profileLinkedinEdittext.getText().toString());
                editor.putString("twitter",profileTwitterEdittext.getText().toString());
                editor.commit();

                String job=profileJobEdittext.getText().toString();
                String expertise=profileExpertiseEdittext.getText().toString();
                String website=profileWebSiteEdittext.getText().toString();
                String biography=profileBioEdiitext.getText().toString();
                String location=profileLocationEdittext.getText().toString();
                String facebook=profileFacebookEdittext.getText().toString();
                String linkedin=profileLinkedinEdittext.getText().toString();
                String twitter=profileTwitterEdittext.getText().toString();

                updateInfos(job,expertise,website,biography,location,facebook,twitter,linkedin,token);

            }
        });

        profilePersonalBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new ProfileFragment());
            }
        });

    }

    public void getSharedValues(){

        sharedPreferences=getContext().getSharedPreferences("personal",0);
        String job=sharedPreferences.getString("job",null);
        String expertise=sharedPreferences.getString("expertise",null);
        String website=sharedPreferences.getString("website",null);
        String biography=sharedPreferences.getString("biography",null);
        String location=sharedPreferences.getString("location",null);
        String facebook=sharedPreferences.getString("facebook",null);
        String linkedin=sharedPreferences.getString("linkedin",null);
        String twitter=sharedPreferences.getString("twitter",null);

        profileJobEdittext.setText(job);
        profileExpertiseEdittext.setText(expertise);
        profileWebSiteEdittext.setText(website);
        profileBioEdiitext.setText(biography);
        profileLocationEdittext.setText(location);
        profileFacebookEdittext.setText(facebook);
        profileLinkedinEdittext.setText(linkedin);
        profileTwitterEdittext.setText(twitter);
    }

    public void updateInfos(String job,String expert,String web,String bio,String location,String face,String twtr,String linkedn,String token){

        Call<ProfileInfoPojo> request= ManagerAll.getOurInstance().updateProfileInfos(job,expert,web,bio,location,face,twtr,linkedn,token);
        request.enqueue(new Callback<ProfileInfoPojo>() {
            @Override
            public void onResponse(Call<ProfileInfoPojo> call, Response<ProfileInfoPojo> response) {

                Toast.makeText(getContext(), "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new ProfileFragment());

            }

            @Override
            public void onFailure(Call<ProfileInfoPojo> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
