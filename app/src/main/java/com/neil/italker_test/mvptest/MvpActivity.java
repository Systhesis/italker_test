package com.neil.italker_test.mvptest;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.neil.italker_test.R;
import com.neil.italker_test.common.app.Activity;

import butterknife.BindView;
import butterknife.OnClick;


public class MvpActivity extends Activity implements IView {

    @BindView(R.id.textView_result)
    TextView mTextViewResult;

    @BindView(R.id.edit_query)
    EditText mEditTextQuery;

    @BindView(R.id.submit)
    Button mButton;

    private IPresenter mIPresenter;

    @Override
    protected void initData() {
        super.initData();
        mIPresenter = new Presenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTextViewResult.setText("hello");
    }


    @OnClick(R.id.submit)
    void onSubmit() {
        mIPresenter.search();
    }

    @Override
    public String getInputString() {
        return mEditTextQuery.getText().toString();
    }

    @Override
    public void setInputString(String str) {
        mTextViewResult.setText(str);
    }
}