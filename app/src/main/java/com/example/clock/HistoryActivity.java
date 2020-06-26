package com.example.clock;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clock.base.BaseActivity;
import com.example.clock.bean.ReportHistoryBean;
import com.example.clock.http.ReportService;

import java.util.Collections;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends BaseActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    ReportService reportService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recycker_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initNetWorkUtil();

        adapter = new HistoryAdapter(R.layout.item_history);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        getHistory(intent.getStringExtra("userId"));

    }

    private void initNetWorkUtil(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hn216.api.yesapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        reportService = retrofit.create(ReportService.class);
    }

    private void getHistory(String userId){
        reportService.getReportHistory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportHistoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReportHistoryBean reportResultBean) {
                        if (reportResultBean.getRet() == 200){
                            Collections.reverse(reportResultBean.getData().getItems());
                            adapter.addData(reportResultBean.getData().getItems());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
