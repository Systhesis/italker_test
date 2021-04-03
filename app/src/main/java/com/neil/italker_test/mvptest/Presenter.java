package com.neil.italker_test.mvptest;

import android.text.TextUtils;

public class Presenter implements IPresenter {
    private IView mIView;

    public Presenter(IView view){
        mIView = view;
    }

    @Override
    public void search() {
        //...开启界面loading
        String inputString = mIView.getInputString();
        if(TextUtils.isEmpty(inputString)) {
            //为空直接返回
            return;
        }
        IUserService iUserService = new UserService();
        String result = "Result:" + iUserService.search(inputString.hashCode());
        //...关闭界面loading
        mIView.setInputString(result);
    }
}
