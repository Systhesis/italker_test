package com.neil.italker_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.neil.italker_test.common.app.Activity;

import butterknife.BindView;


public class MainActivity extends Activity {

    @BindView(R.id.textView_hello)
    TextView mTextViewHello;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTextViewHello.setText("hello");
    }
}