package com.example.mymis.base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymis.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LYH   2017/8/28
 * <p>
 * 邮箱：945131558@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity  {
   protected List<Activity> ActivityShed ;
   static final int code = 8080;
   protected PermissionsResult mOnResult;
  private TextView mTitle;
    private ImageView mBack,mSign;
    private View mTitleView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//取消标题
      // requestWindowFeature(Window.FEATURE_NO_TITLE);
//取消状态;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.titlecolor));
        }
        setContentView(getlayout());
        intitle();
        if(ActivityShed ==null){
            ActivityShed = new ArrayList<>();
        }
        ActivityShed.add(getAtvity());
        Log.e("LYH","长度"+ActivityShed.size());
        initview(savedInstanceState);
    }

    @SuppressLint("WrongViewCast")
    protected  void intitle(){
        mTitleView  = findViewById(R.id.mtitle);
        mTitle = (TextView) findViewById(R.id.pre_tv_title);
        mBack = (ImageView) findViewById(R.id.pre_v_back);
        mSign = (ImageView) findViewById(R.id.tv_member);
    };
    /**
     * 设置标题名
     * */
   public abstract String gettitle();

    /**
     * 是否显示标题
     * */
    public abstract boolean isshowtitle();

    /**
     * 设置标志 是否显示或更改
     * */

    public abstract void setMsign(ImageView v);
    @Override
    protected void onResume() {
        super.onResume();
        if(isshowtitle()){
            mTitleView.setVisibility(View.VISIBLE);
        }else {
            mTitleView.setVisibility(View.GONE);
        }

        if(mTitleView!=null){
            if(isshowback()){
                mBack.setVisibility(View.VISIBLE);
            }else {
                mBack.setVisibility(View.GONE);
            }
            // 返回点击
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ActivityShed.get(ActivityShed.size()-1).finish();
                }
            });
            setMsign(mSign);
            mSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("点击标志");
                }
            });
            mTitle.setText(gettitle());
        }

    }

    /**
     * 初始化view 数据
     * */
   public abstract void initview(Bundle savedInstanceState);
    /**
     * 获取布局layoutID
     * */
    public abstract int getlayout();

    /**
     * 普通跳转
     * */

    public void startActivity(Class<?> clss) {
        if (ActivityShed != null&&!ActivityShed.isEmpty()){
            Intent intent = new Intent(ActivityShed.get(ActivityShed.size()-1),clss);
             startActivity(intent);
        }
    }
    /**
     * 带参数跳转
     * */
    public void startActivity(Bundle bundle,Class<?> clss) {
        if (ActivityShed != null&&!ActivityShed.isEmpty()){
            Intent intent = new Intent(ActivityShed.get(ActivityShed.size()),clss);
             intent.putExtra("bundle",bundle);
            startActivity(intent);
        }
    }
    public void showToast(String msg){
        if(!TextUtils.isEmpty(msg)){
            Toast.makeText(getAtvity(),msg,Toast.LENGTH_SHORT).show();

        }
    }


/**
 * 添加这个Activity
 * */
    public abstract Activity getAtvity();
   /**
    * 获取栈顶Activity
    * */
    protected Activity getShedCrown(){
        return ActivityShed.get(ActivityShed.size()-1);
    }
 /**
  * 移除当前销毁的Activity
  * */
  public abstract Activity remoActivity();
   /**
    *  重写finish 让每次执行这个方法都销毁执行这个的avtivity
    * */




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityShed != null&&!ActivityShed.isEmpty()) {
            Activity activity = remoActivity();
            String className = activity.getLocalClassName();
            for (int i =0;i<ActivityShed.size();i++){
                if(ActivityShed.get(i).getLocalClassName().equals(className))
                    ActivityShed.remove(i);
            }
        }
    }

    /**
     * 运行权限申请
     * */
    protected void requestmPermissions(String[] arr ,@NonNull PermissionsResult onresult ){
        List<String> nopermissions = new ArrayList<>();
        List<String> yespermissions = new ArrayList<>();
        if(onresult!=null){
            mOnResult = onresult;
        }else {
            throw new NullPointerException("PermissionsResult不能为null");
        }
        //大于等6.0  需要权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (arr!=null&&arr.length>0){
                for(int i =0;i<arr.length;i++){
                    int permission = ActivityCompat.checkSelfPermission(this, arr[i]);
                    if(PackageManager.PERMISSION_GRANTED ==permission){
                   //  有这个权限
                      yespermissions.add(arr[i]);
                 }else {
                    //无这个权限
                        nopermissions.add(arr[i]);
                 }
                }  //循环检查完
                if(!yespermissions.isEmpty()){
                    String[] strings = yespermissions.toArray(new String[yespermissions.size()]);
                    mOnResult.OnListenerExistingPermissiond(strings);
                }
                if(nopermissions.size()>0) {
                    String[] strings = nopermissions.toArray(new String[nopermissions.size()]);
                    requestPermissions(strings ,code);
                }
            }
        }else {
                      mOnResult.OnListenerSmallSDK23();
        }
    }
    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public  Activity getTopActivity() {
        if (ActivityShed != null&&!ActivityShed.isEmpty()) {
            return ActivityShed.get(ActivityShed.size());
        }else {
        return null;
        }
    }
     /**
      * 申请权限回调
      * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
             if(requestCode ==code&&mOnResult!=null){
                 //权限结果回调
                 mOnResult.OnlistenerPermissiond(permissions,grantResults);
             }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public abstract boolean  isshowback();
}
