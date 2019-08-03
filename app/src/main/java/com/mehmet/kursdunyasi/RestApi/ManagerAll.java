package com.mehmet.kursdunyasi.RestApi;

import com.mehmet.kursdunyasi.Models.LoginPojo;
import com.mehmet.kursdunyasi.Models.ProfileInfoPojo;
import com.mehmet.kursdunyasi.Models.RegisterPojo;
import com.mehmet.kursdunyasi.Models.UploadImagePojo;
import com.mehmet.kursdunyasi.Models.VideoUploadPojo;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by MEHMET on 27.06.2019.
 */

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance=new ManagerAll();

    public static synchronized ManagerAll getOurInstance(){

        return ourInstance;
    }

    public Call<LoginPojo> login(String mail, String password){

        Call<LoginPojo> request=getRestApi().login(mail,password);
        return request;
    }

    public Call<RegisterPojo> register(String mail,String userName,String name,String surName,String password){

        Call<RegisterPojo> requestRegister=getRestApi().register(mail,userName,name,surName,password);
        return requestRegister;
    }

    public Call<LoginPojo> updateProfile(String mail, String name,String surname,String token){

        Call<LoginPojo> request=getRestApi().updateProfile(mail,name,surname,token);
        return request;
    }

    public Call<ProfileInfoPojo> updateProfileInfos(String job,String location,String company,String web,String bio,String twitter,String facebook,String linkedn,String token){

        Call<ProfileInfoPojo> request=getRestApi().updateProfileInfos(job,location,company,web,bio,twitter,facebook,linkedn,token);
        return request;
    }




}
