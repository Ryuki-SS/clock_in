package com.example.clock.http;



import com.example.clock.bean.UserBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {


    @POST("/?s=App.SuperTable.GetMoreDataByMoreField&model_name=denglu&database=super&logic=and&field_name_list=id%2Cpassword&select=id%2Cname&app_key=F2D114B6D91201B0033321E4B9B9B2B3&sign=0CAE40E1283D8D5E8A9317FE3CD4B793")
    @FormUrlEncoded
    Observable<UserBean> login(@Field("field_value_list") String nameAndPassword);
}
