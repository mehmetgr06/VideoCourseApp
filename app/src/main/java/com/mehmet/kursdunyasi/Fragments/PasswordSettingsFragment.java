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
import com.mehmet.kursdunyasi.Models.UpdatePasswordPojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.RestApi;

import java.security.MessageDigest;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PasswordSettingsFragment extends Fragment {

    View view;

    TextView passwordChangeCancel,passwordChangeUpdateText;
    EditText oldPasswordEdittext,newPasswordEdittext,newPasswordAgainEdittext;
    SharedPreferences sharedPreferences;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_password_settings, container, false);

        define();

        return view;
    }

    private void define(){

        passwordChangeCancel=view.findViewById(R.id.passwordChangeCancel);
        passwordChangeUpdateText=view.findViewById(R.id.passwordChangeUpdateText);
        oldPasswordEdittext=view.findViewById(R.id.oldPasswordEdittext);
        newPasswordEdittext=view.findViewById(R.id.newPasswordEdittext);
        newPasswordAgainEdittext=view.findViewById(R.id.newPasswordAgainEdittext);

        sharedPreferences=getContext().getSharedPreferences("giris",0);
        token=sharedPreferences.getString("token",null);

        passwordChangeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new SettingsFragment());
            }
        });

        passwordChangeUpdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPasword=oldPasswordEdittext.getText().toString();
                String newPassword=newPasswordEdittext.getText().toString();
                String newPasswordAgain=newPasswordAgainEdittext.getText().toString();

                updatePassword(oldPasword,newPassword,newPasswordAgain);
            }
        });

    }

    private void updatePassword(String oldPassword,String newPassword,String newPasswordAgain){

        if(newPassword.length()<6){
            Toast.makeText(getContext(), "Yeni şifre en az 6 karakterli olmalıdır", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPassword.equalsIgnoreCase(newPasswordAgain)){

            Toast.makeText(getContext(), "Şifre tekrarı Yanlış!", Toast.LENGTH_SHORT).show();
        }
        else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RestApi client = retrofit.create(RestApi.class);

            Call<UpdatePasswordPojo> call=client.updatePassword(token,oldPassword,newPasswordAgain);
            call.enqueue(new Callback<UpdatePasswordPojo>() {
                @Override
                public void onResponse(Call<UpdatePasswordPojo> call, Response<UpdatePasswordPojo> response) {

                    if(response.isSuccessful()){

                        Toast.makeText(getContext(), "Şifreniz Güncellendi", Toast.LENGTH_SHORT).show();

                        ChangeFragment changeFragment=new ChangeFragment(getContext());
                        changeFragment.change(new SettingsFragment());
                    }
                    else{

                        Toast.makeText(getContext(), "Mevcut Şifrenizi Hatalı Girdiniz", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<UpdatePasswordPojo> call, Throwable t) {

                    Toast.makeText(getContext(), "Bağlantı Hatası"+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }




}
