package com.example.clock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.gson.JsonObject;
import com.tencent.bugly.Bugly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.clock.base.BaseActivity;
import com.example.clock.bean.PostBackBean;
import com.example.clock.bean.ReportBean;
import com.example.clock.bean.ReportResultBean;
import com.example.clock.http.ReportService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.clock.utils.ToastUtil;

import org.litepal.LitePal;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    TextView idTextView;
    TextView nameTextView;
    EditText phoneEditText;
    TextView patientSelectTextView;
    EditText temperatureEditText;
    EditText detailLocEditText;
    EditText extraEditText;
    CardView reportContainer;
    TextView reportTextView;
    TextView historyTextView;
    CardView warningCardView;

    RelativeLayout phoneContainer;
    RelativeLayout detailLocContainer;
    RelativeLayout extrasContainer;

    TextView deadLineTextView;

    ReportBean reportBean;

    Retrofit retrofit ;
    ReportService reportService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bugly.init(this, "32d45c257c", true);
        initView();          /*初始化界面，设置点击事件。*/
        initOldData();       /*初始化老数据，检查本地存储是否有打卡后存储的数据，有就进行自动填写。*/
        initTimeText();      /*初始化时间显示组件，获取当天日期*/
        initNetWorkUtil();   /*初始化网络工具*/
        checkHasReported();  /*检查是否打卡*/
    }

    private void initView(){
        idTextView = findViewById(R.id.id_textView);
        nameTextView = findViewById(R.id.name_textView);
        phoneEditText = findViewById(R.id.phoneNum_Edittext);
        detailLocEditText = findViewById(R.id.detail_location_Edittext);
        patientSelectTextView = findViewById(R.id.patient_select_textView);
        temperatureEditText = findViewById(R.id.temperature_Edittext);
        extraEditText = findViewById(R.id.extras_edittext);
        reportContainer = findViewById(R.id.report_container_cardView);
        reportTextView = findViewById(R.id.report_textView);
        historyTextView = findViewById(R.id.history_textView);
        phoneContainer = findViewById(R.id.phoneNum_container);
        detailLocContainer = findViewById(R.id.detail_location_container);
        extrasContainer = findViewById(R.id.extras_container);
        deadLineTextView = findViewById(R.id.deadline_textView);
        warningCardView = findViewById(R.id.warning_cardview);

        phoneContainer.setOnClickListener( v -> showInputMethod(phoneEditText));
        detailLocContainer.setOnClickListener( v -> showInputMethod(detailLocEditText));
        extrasContainer.setOnClickListener( v -> showInputMethod(extraEditText));

        patientSelectTextView.setOnClickListener(this);
        reportContainer.setOnClickListener(this);
        historyTextView.setOnClickListener(this);
        warningCardView.setOnClickListener(this);
    }

    private void showInputMethod(EditText editText){
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
        InputMethodManager inputManager = (InputMethodManager)editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null){
            inputManager.showSoftInput(editText,0);
        }
    }

    private String getNowTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        return t1;
    }

    private void initTimeText(){
        String t1 = getNowTime();
        deadLineTextView.setText(t1);
    }

    private void initOldData(){
        reportBean = LitePal.find(ReportBean.class,1);
        if (reportBean != null && reportBean.name != null){
            String idText = reportBean.getUserId() + "";
            idTextView.setText(idText);
            nameTextView.setText(reportBean.getName());
            if (reportBean.getPhone() != null){
                phoneEditText.setText(reportBean.getPhone());
            }
            if (reportBean.getIshealthy() != null){
                patientSelectTextView.setText(reportBean.getIshealthy());
            }
            if (reportBean.getTemperature() != null){
                temperatureEditText.setText(reportBean.getTemperature());
            }
            if (reportBean.getAddress() != null){
                detailLocEditText.setText(reportBean.getAddress());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private String[] commonSelectItems = new String[]{"待选择", "是", "否"};

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.patient_select_textView) {
            showSingleChoiceDialog("", commonSelectItems, (pos, items) -> {
                patientSelectTextView.setText(items[pos]);
                if (pos == 1) {
                    patientSelectTextView.setTextColor(Color.RED);
                } else {
                    patientSelectTextView.setTextColor(Color.parseColor("#CA0f1f0f"));
                }
            });
        } else if (v.getId() == R.id.report_container_cardView) {
            String isSick = patientSelectTextView.getText().toString();
            if (isSick.equals("待选择")){
                ToastUtil.show("请选择健康情况！");
            }else {
                saveBeanAndReport();
            }
        } else if (v.getId() == R.id.history_textView) {
            turn2History();
        } else if (v.getId() == R.id.warning_cardview) {
            turn2Warning();
        }
    }

    private void turn2History() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        intent.putExtra("userId", idTextView.getText().toString());
        startActivity(intent);
    }

    private void turn2Warning() {
        if (idTextView.getText().toString().equals("2017213072")){
            Intent intent = new Intent(MainActivity.this, WarningActivity.class);
            intent.putExtra("nowTime", getNowTime());
            startActivity(intent);
        }else {
            ToastUtil.show("无权限！");
        }
    }

    private void saveBeanAndReport(){
        String idS = idTextView.getText().toString();
        int id = Integer.parseInt(idS);
        String name = nameTextView.getText().toString();
        String age = "18";
        String ishealthy = patientSelectTextView.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = detailLocEditText.getText().toString();
        String temperature = temperatureEditText.getText().toString();
        reportBean = new ReportBean();
        reportBean.setUserId(id);
        reportBean.setName(name);
        reportBean.setAge("18");
        reportBean.setAddress(address);
        reportBean.setIshealthy(ishealthy);
        reportBean.setTime(getNowTime());
        reportBean.setPhone(phone);
        reportBean.setTemperature(temperature);
        reportBean.save();
        reportBean.update(1);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",idS);
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("age",age);
        jsonObject.addProperty("ishealthy",ishealthy);
        jsonObject.addProperty("phone",phone);
        jsonObject.addProperty("address",address);
        jsonObject.addProperty("temperature",temperature);
        jsonObject.addProperty("time",getNowTime());
        String jsonStr = jsonObject.toString();
        System.out.println("TAGG "+jsonStr);
        report(jsonStr);
    }

    private void report(String jsonStr){
        reportService.report(jsonStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getRet() == 200){
                            if (postBackBean.getData().getErr_code() == 0){
                                ToastUtil.show("打卡成功！");
                                setReportState(true);
                            }else {
                                ToastUtil.show("请填写所有带*条目！");
                            }
                        }else {
                            ToastUtil.show("网络请求失败！");
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

    private void checkHasReported(){
        System.out.println("TAGG checkHasReported");
        reportService.getReportInfo(idTextView.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("TAGG checkHasReported|onSubscribe");
                    }

                    @Override
                    public void onNext(ReportResultBean reportResultBean) {
                        List<ReportResultBean.DataBean.ItemsBean> userList = reportResultBean.getData().getItems();
                        if (userList != null && userList.size() > 0){
                            ReportResultBean.DataBean.ItemsBean itemsBean = userList.get(userList.size() - 1);
                            if (itemsBean.getTime().equals(getNowTime())){
                                setReportState(true);
                            }
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

    private void initNetWorkUtil(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://hn216.api.yesapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        reportService = retrofit.create(ReportService.class);
    }

    private void setReportState(boolean reported){
        if (reported){
            reportContainer.setCardBackgroundColor(Color.GRAY);
            reportContainer.setClickable(false);
            reportTextView.setText("已完成今日打卡");
        }else {
            reportContainer.setCardBackgroundColor(Color.parseColor("#FF66EEB9"));
            reportContainer.setClickable(true);
            reportTextView.setText("打卡");
        }
    }

    private void showSingleChoiceDialog(String title, String[] items, SelectCallBack callBack) {
        int[] pos = new int[]{0};
        final AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle(title);
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                (dialog, which) -> pos[0] = which);
        singleChoiceDialog.setPositiveButton("确定",
                (dialog, which) -> callBack.onSelected(pos[0], items));
        singleChoiceDialog.show();
    }

    interface SelectCallBack {
        void onSelected(int pos, String[] items);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String userName = intent.getStringExtra("name");
        String userId = intent.getStringExtra("id");
        System.out.println("TAGGG username = " + userName);
        System.out.println("TAGGG username = " + userId);
    }
}
