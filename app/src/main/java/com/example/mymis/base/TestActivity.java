package com.example.mymis.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.mymis.base.base.BaseActivity;

public class TestActivity extends BaseActivity {


    @Override
    public String gettitle() {
        return "第一个页面";
    }

    @Override
    public boolean isshowtitle() {
        return true;
    }

    @Override
    public void setMsign(ImageView v) {

    }

    @Override
    public void initview(Bundle savedInstanceState) {
         Log.e("LYH",getShedCrown().getLocalClassName());
    }

    @Override
    public int getlayout() {
        return R.layout.activity_test;
    }

    @Override
    public Activity getAtvity() {
        return TestActivity.this;
    }

    @Override
    public Activity remoActivity() {
        return TestActivity.this;
    }

    @Override
    public boolean isshowback() {
        return true;
    }


}
