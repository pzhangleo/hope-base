package com.zhp.base.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.zhp.base.R;

/**
 * loading dialog
 * Created by zhangpeng on 16/7/15.
 */
public class IndeterminateProgressDialog extends Dialog {

    public IndeterminateProgressDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_progress_dialog, null);
        setContentView(v);
        try {
            int titleId = getContext().getResources().getIdentifier("android:id/title", null, null);
            int dividerId = getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View title = findViewById(titleId);
            View divider = findViewById(dividerId);
            if (title != null) {
                title.setVisibility(View.GONE);
            }
            if (divider != null) {
                divider.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}