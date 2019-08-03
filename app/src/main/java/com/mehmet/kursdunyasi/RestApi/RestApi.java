package com.mehmet.kursdunyasi.RestApi;

import com.mehmet.kursdunyasi.Models.CategoriesPojo;
import com.mehmet.kursdunyasi.Models.CourseLevelPojo;
import com.mehmet.kursdunyasi.Models.LoginPojo;
import com.mehmet.kursdunyasi.Models.ProfileInfoPojo;
import com.mehmet.kursdunyasi.Models.RegisterPojo;
import com.mehmet.kursdunyasi.Models.TeacherCoursesPojo;
import com.mehmet.kursdunyasi.Models.TeacherVideoPojo;
import com.mehmet.kursdunyasi.Models.UpdatePasswordPojo;
import com.mehmet.kursdunyasi.Models.UploadImagePojo;
import com.mehmet.kursdunyasi.Models.VideoUploadPojo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by MEHMET on 27.06.2019.
 */

public interface RestApi {

    @FormUrlEncoded
    @POST("/api/login-mobile")
    Call<LoginPojo> login(@Field("email") String mail, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/register-mobile")
    Call<RegisterPojo> register(@Field("email") String mail, @Field("user_name") String userName, @Field("name") String name, @Field("surname") String surName, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/update-mobile")
    Call<LoginPojo> updateProfile(@Field("email") String email,@Field("name") String name,@Field("surname") String surname,@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/update-profile-mobile")
    Call<ProfileInfoPojo> updateProfileInfos(@Field("job_title") String job,@Field("location") String location,@Field("company") String company,@Field("personal_website") String personal_website,@Field("short_bio") String short_bio,@Field("twitter") String twitter,@Field("facebook") String facebook,@Field("linkedin") String linkedin,@Field("token") String token);

    @Multipart
    @POST("/api/upload-image-mobile")
    Call<UploadImagePojo> uploadImage(@Part("token")RequestBody token,@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/view-image-mobile")
    Call<UploadImagePojo> getProfileImage(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/update-password-mobile")
    Call<UpdatePasswordPojo> updatePassword(@Field("token") String token, @Field("password") String oldPassword, @Field("new_password") String newPassword);


    @Multipart
    @POST("/api/register-educations-mobile")
    Call<VideoUploadPojo> uploadVideo(@Part("token")RequestBody token,
                                      @Part("title") RequestBody title,
                                      @Part("categories") RequestBody categories,
                                      @Part("description") RequestBody description,
                                      @Part("level") RequestBody level,
                                      @Part("keywords") RequestBody keywords,
                                      @Part("video_title") RequestBody videoTitle,
                                      @Part MultipartBody.Part image,
                                      @Part MultipartBody.Part video);


    @GET("/api/categori-spinner-educations-mobile")
    Call<List<CategoriesPojo>> getspinnerValues();

    @GET("/api/level-spinner-educations-mobile")
    Call<List<CourseLevelPojo>> getLevelspinnerValues();


    @GET("/api/info-education-mobile")
    Call<List<TeacherCoursesPojo>> getTeacherCourses(@Query("token") String token);


    @GET("/api/info-education-video-mobile")
    Call<List<TeacherVideoPojo>> getTeacherVideoLink(@Query("token") String token, @Query("education_id") int education_id);

    @Multipart
    @POST("/api/add-education-video-mobile")
    Call<VideoUploadPojo> teacherAddVideo(@Part("token")RequestBody token,
                                      @Part("video_title") RequestBody videoTitle,
                                      @Part("education_id") RequestBody educationId,
                                      @Part MultipartBody.Part video);

    @DELETE("/api/delete-education-video-mobile")
    Call<VideoUploadPojo> deleteTeacherVideo(@Query("token") String token, @Query("id") int video_id);

    @GET("/api/update-education-video-mobile")
    Call<VideoUploadPojo> updateTeacherVideo(@Query("token") String token, @Query("id") int video_id,@Query("title") String title);


}
