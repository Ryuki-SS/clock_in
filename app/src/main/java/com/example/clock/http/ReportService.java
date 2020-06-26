package com.example.clock.http;

import com.example.clock.bean.PostBackBean;
import com.example.clock.bean.ReportHistoryBean;
import com.example.clock.bean.ReportResultBean;
import com.example.clock.bean.ReportWarningBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ReportService {
    @FormUrlEncoded
    @POST("/?s=App.SuperTable.Create&model_name=keshebiao&database=super&app_key=F2D114B6D91201B0033321E4B9B9B2B3&sign=A4279989F9FBA87FD4A3CD315C335BAE")
    Observable<PostBackBean> report(
            @Field("data") String jsonStr
    );

    @GET("/?s=App.SuperTable.GetMoreDataByMoreField&model_name=keshebiao&database=super&logic=and&field_name_list=id&select=id%2Cname%2Cage%2Cishealthy%2Cphone%2Caddress%2Ctemperature%2Ctime&app_key=F2D114B6D91201B0033321E4B9B9B2B3&sign=AC63AB137D61954E0B64046D99525B70")
    Observable<ReportResultBean> getReportInfo(
            @Query("field_value_list") String userId
    );

    @GET("/?s=App.SuperTable.GetMoreDataByMoreField&model_name=keshebiao&database=super&logic=and&field_name_list=id&select=id%2Cname%2Cage%2Cishealthy%2Cphone%2Caddress%2Ctemperature%2Ctime&app_key=F2D114B6D91201B0033321E4B9B9B2B3&sign=AC63AB137D61954E0B64046D99525B70")
    Observable<ReportHistoryBean> getReportHistory(
            @Query("field_value_list") String userId
    );

    @FormUrlEncoded
    @POST("/?s=App.SuperTable.GetMoreDataByMoreField&model_name=keshebiao&database=super&logic=and&field_name_list=time%2Cishealthy&select=id%2Cname%2Cage%2Cphone%2Caddress%2Ctemperature&app_key=F2D114B6D91201B0033321E4B9B9B2B3&sign=44D2320AF9255D9030B90DBEA742FB0F")
    Observable<ReportWarningBean> getWarningInfo(
            @Field("field_value_list") String timeAndIshealthy
    );

}
