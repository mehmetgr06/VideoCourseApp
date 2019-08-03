package com.mehmet.kursdunyasi.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mehmet.kursdunyasi.ChangeFragment;
import com.mehmet.kursdunyasi.LoginActivity;
import com.mehmet.kursdunyasi.Models.LoginPojo;
import com.mehmet.kursdunyasi.Models.UploadImagePojo;
import com.mehmet.kursdunyasi.R;
import com.mehmet.kursdunyasi.RestApi.BaseUrl;
import com.mehmet.kursdunyasi.RestApi.ManagerAll;
import com.mehmet.kursdunyasi.RestApi.RestApi;
import com.mehmet.kursdunyasi.RestApi.RestApiClient;
import com.mehmet.kursdunyasi.Utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class AcountinformationsFragment extends Fragment {

    View view;
    EditText profileNameEdittext,profileSurnameEdittext,profileMailEdittext;
    SharedPreferences sharedPreferences,sharedPreferences2,sharedPreferences3,sharedPreferences4;
    TextView profileUserNameTextview,profileUpdateText,profileAccountCancel,profileImageChanceText;
    CircleImageView profileImage;
    private static final int PICK_FROM_GALLERY = 1;
    Bitmap bitmap;
     String token;
    Uri filePath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the exams_list_layout for this fragment
        view= inflater.inflate(R.layout.fragment_accountinformations, container, false);

        define();
        //getProfilePhoto();

        //cekixConfigAPI = RestApiClient.RestApiClient();

        return view;
    }

    public void define(){

        profileNameEdittext=view.findViewById(R.id.profileNameEdittext);
        profileSurnameEdittext=view.findViewById(R.id.profileSurnameEdittext);
        profileMailEdittext=view.findViewById(R.id.profileMailEdittext);
        profileUserNameTextview=view.findViewById(R.id.profileUserName);
        profileUpdateText=view.findViewById(R.id.profileUpdateText);
        profileAccountCancel=view.findViewById(R.id.profileAccountCancel);
        profileImage =view.findViewById(R.id.profile_change_image);
        profileImageChanceText =view.findViewById(R.id.profileImageChanceText);


        sharedPreferences=getContext().getSharedPreferences("giris",0);
         String name=sharedPreferences.getString("name",null);
         final String surname=sharedPreferences.getString("surname",null);
         String userName=sharedPreferences.getString("userName",null);
         String mail=sharedPreferences.getString("email",null);
         token=sharedPreferences.getString("token",null);

        profileNameEdittext.setText(name);
        profileSurnameEdittext.setText(surname);
        profileMailEdittext.setText(mail);
        profileUserNameTextview.setText(userName);

        profileUpdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameEdt=profileNameEdittext.getText().toString();
                String surnameEdt=profileSurnameEdittext.getText().toString();
                String mailEdt=profileMailEdittext.getText().toString();

                sharedPreferences2=getContext().getSharedPreferences("account",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name",profileNameEdittext.getText().toString());
                editor.putString("surname",profileSurnameEdittext.getText().toString());
                editor.putString("mail",profileMailEdittext.getText().toString());
                editor.commit();

                updateDatas(mailEdt,nameEdt,surnameEdt,token);


            }
        });

        profileAccountCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment changeFragment=new ChangeFragment(getContext());
                changeFragment.change(new ProfileFragment());
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        profileImageChanceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage(filePath);
            }
        });

        getProfilePhoto();

        //profileImageChanceText.setVisibility(View.INVISIBLE);
        profileImageChanceText.setTextColor(Color.parseColor("#95a5a6"));
        profileImageChanceText.setClickable(false);

    }

    public void updateDatas(String mail,String name,String surname,String token){

        Call<LoginPojo> request= ManagerAll.getOurInstance().updateProfile(mail, name, surname,token);
        request.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {

                if(response.isSuccessful()){

                    Toast.makeText(getContext(), "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                    ChangeFragment changeFragment=new ChangeFragment(getContext());
                    changeFragment.change(new ProfileFragment());

                }
                else{

                    Toast.makeText(getContext(), "Güncelleme Başarısız"+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {

                Toast.makeText(getContext(), "Bağlantı Hatası!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadImage(Uri uri){

        RequestBody tokenSend=RequestBody.create(MultipartBody.FORM,token);

        File file= FileUtils.getFile(getContext(),uri);

        RequestBody filePath=RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(uri)), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), filePath);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://192.168.1.120:8000")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        RestApi client=retrofit.create(RestApi.class);

            Call<UploadImagePojo> call = client.uploadImage(tokenSend, body);
            call.enqueue(new Callback<UploadImagePojo>() {
                @Override
                public void onResponse(Call<UploadImagePojo> call, Response<UploadImagePojo> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Profil Resmi Değiştirildi", Toast.LENGTH_SHORT).show();
                        getProfilePhoto();

                    } else {
                        Toast.makeText(getContext(), "Profil Resmi Değiştirilemedi", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<UploadImagePojo> call, Throwable t) {

                    Toast.makeText(getContext(), "Bağlantı Hatası", Toast.LENGTH_SHORT).show();
                }
            });


    }

    public void getProfilePhoto(){

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        RestApi client=retrofit.create(RestApi.class);

        Call<UploadImagePojo> call=client.getProfileImage(token);
        call.enqueue(new Callback<UploadImagePojo>() {
            @Override
            public void onResponse(Call<UploadImagePojo> call, Response<UploadImagePojo> response) {

                if(response.isSuccessful()) {

                   // Picasso.get().load(BaseUrl.URL+""+response.body().getPicture()).into(profileImage);
                    Glide.with(getContext()).load(BaseUrl.URL+""+response.body().getPicture()).into(profileImage);
                    Log.i("profilres",BaseUrl.URL+""+response.body().getPicture()+"-->"+response.code());
                }

            }

            @Override
            public void onFailure(Call<UploadImagePojo> call, Throwable t) {

            }
        });





    }

    public void openGallery(){

       // Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       // startActivityForResult(intent,1);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== RESULT_OK){
            if(data !=null)
            {
                try {

                    filePath=data.getData();
                    //profileImageChanceText.setVisibility(View.VISIBLE);
                    profileImageChanceText.setTextColor(Color.parseColor("#2980b9"));
                    profileImageChanceText.setClickable(true);

                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                    profileImage.setImageBitmap(bitmap);
                    profileImage.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else {

            Toast.makeText(getContext(), "Resim Seçilmedi", Toast.LENGTH_SHORT).show();
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
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }


}


  /*  public void onActivityResult(int requestCode, int resultCode, Intent education_id) { //Berk'in Kodları
        super.onActivityResult(requestCode, resultCode, education_id);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK && null != education_id) {


                selectedImage = education_id.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                cursor.close();

                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                profileImage.setImageBitmap(bitmap);
                profileImage.setVisibility(View.VISIBLE);

                uploadImageNew();
            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }*/


     /*   Callback<LogoutModel> listCallBack = new Callback<LogoutModel>() {
@Override
public void onResponse(Call<LogoutModel> call, UpdatePasswordPojo<LogoutModel> response) {
        if (response.isSuccessful()) {
        if (response.body().getProgressType().equals("02")) {
        profile_layout.setVisibility(View.GONE);
        profile_progress_loading.setVisibility(View.VISIBLE);
        Log.d("ProfileFragment", "" + response.body().getProgressMessage());
        SharedPreferencesSettings.DeleteKey(getContext(), "token");
        SharedPreferencesSettings.DeleteKey(getContext(), "userID");
        SharedPreferencesSettings.DeleteKey(getContext(), "userType");
        SharedPreferencesSettings.DeleteKey(getContext(), "userEmail");
        SharedPreferencesSettings.DeleteKey(getContext(), "userName");
        getActivity().finish();
        Intent ıntent = new Intent(getContext(), LoadingPage.class);
        ıntent.putExtra("page", "Logout");
        startActivity(ıntent);
        }
        } else {
        Log.d("ProfileFragment", "Error -> Code: " + response.code() + " Message: " + response.message());
        }
        }
@Override
public void onFailure(Call<LogoutModel> call, Throwable t) {
        t.printStackTrace();
        }
        };
        cekixConfigAPI.logout(CekixUtils.userToken).enqueue(listCallBack); */


