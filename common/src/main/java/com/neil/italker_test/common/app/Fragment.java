package com.neil.italker_test.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends androidx.fragment.app.Fragment {

    protected View mRoot;
    protected Unbinder mRootUnBinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRoot == null) {
            int layId = getContentLayoutId();

            //初始化当前的根布局，但是不在创建时就添加到container中去
            View root = inflater.inflate(layId, container, false);

            initWidget(root);

            mRoot = root;
        } else {
            if(mRoot.getParent() != null) {
                //把当前root从其父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }


        return mRoot;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当view创建完成后 初始化数据

        initDate();
    }

    /**
     * 得到当前界面的资源文件id
     * @name getContentLayoutId
     * @author nan2.zhong
     * @date 3/16/21 3:34 PM
     []
     * @return int
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化控件
     * @name initWidget
     * @author nan2.zhong
     * @date 3/16/21 3:52 PM
     []
     * @return void
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     * @name initDate
     * @author nan2.zhong
     * @date 3/16/21 3:53 PM
     []
     * @return void
     */
    protected void initDate() {

    }

    /**
     * 初始化参数
     * @name initArgs
     * @author nan2.zhong
     * @date 3/16/21 10:56 PM
     [bundle]
     * @return void
     */
    protected void initArgs(Bundle bundle) {

    }

    /**
     * 返回按键触发时调用
     * @name onBackPressed
     * @author nan2.zhong
     * @date 3/16/21 10:57 PM
     []
     * @return boolean
     * 返回true表示我已处理返回逻辑 Activity不用自己finish
     * 返回false代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }


}
