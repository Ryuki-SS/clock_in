package com.example.clock;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.clock.bean.ReportHistoryBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HistoryAdapter extends BaseQuickAdapter<ReportHistoryBean.DataBean.ItemsBean, BaseViewHolder> {

    public HistoryAdapter(int layoutResId,
            @Nullable List<ReportHistoryBean.DataBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReportHistoryBean.DataBean.ItemsBean itemsBean) {
        baseViewHolder.setText(R.id.text_name,itemsBean.getName())
                .setText(R.id.text_location,itemsBean.getAddress())
                .setText(R.id.text_time,itemsBean.getTime());
    }

}
