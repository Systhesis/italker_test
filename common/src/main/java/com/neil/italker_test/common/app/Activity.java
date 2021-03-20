package com.neil.italker_test.common.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前初始化的数据
        initWindows();
        if(initArgs(getIntent().getExtras())) {
            //得到界面id并设置到界面之中
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     * @name initWindows
     * @author nan2.zhong
     * @date 3/16/21 3:31 PM
     []
     * @return void
     */
    protected void initWindows(){

    }

    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件的id
     * @name getContentLayoutId
     * @author nan2.zhong
     * @date 3/16/21 3:20 PM
     []
     * @return int
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @name initWidget
     * @author nan2.zhong
     * @date 3/16/21 3:19 PM
     []
     * @return void
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     * @name initData
     * @author nan2.zhong
     * @date 3/16/21 3:19 PM
     []
     * @return void
     */
    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments.size() > 0) {
            for(Fragment fragment : fragments) {
                if(fragment instanceof com.neil.italker_test.common.app.Fragment) {
                    //判断师傅拦截了返回按钮
                    if(((com.neil.italker_test.common.app.Fragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
