package com.example.clock;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.clock.bean.ReportWarningBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class WarningAdapter extends BaseQuickAdapter<ReportWarningBean.DataBean.ItemsBean, BaseViewHolder> {


    public WarningAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReportWarningBean.DataBean.ItemsBean itemsBean) {
        baseViewHolder.setText(R.id.text_name,itemsBean.getName())
                .setText(R.id.text_location,itemsBean.getAddress())
                .setText(R.id.text_temperature,itemsBean.getTemperature())
                .setText(R.id.text_ishealthy,"是");
    }
}
