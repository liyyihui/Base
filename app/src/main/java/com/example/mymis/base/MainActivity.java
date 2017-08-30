package com.example.mymis.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mymis.base.base.BaseActivity;

public class MainActivity extends BaseActivity {


  public void myclick(View view){
      switch (view.getId()){
          case R.id.camera:
          //        requestmPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE},null);
              Log.e("LYH","相机");
             break;
          case R.id.phone:
              Log.e("LYH","电话");
              break;
          case R.id.src:
              Log.e("LYH","相册");
              startActivity(TestActivity.class);
              break;
      }
  }

   /**
    * 启动相机
    * */
    private void firingcamera(){
        Intent in = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(in);
    }

   /**
    * 拨打电话
    * */
   private void dialphone(){
       Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:18081745066"));
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
   }
   /**
    * 启动相册
    * */
   private void firingphoto(){
       Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
       albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
       startActivityForResult(albumIntent, 1);
   }

    @Override
    public String gettitle() {
        return "验证标题";
    }

    @Override
    public boolean isshowtitle() {
        return true;
    }

    @Override
    public void setMsign(ImageView v) {
        v.setVisibility(View.GONE);
    }

    @Override
    public void initview(Bundle savedInstanceState) {

    }

    @Override
    public int getlayout() {
        return R.layout.activity_main;
    }

    @Override
    public Activity getAtvity() {
        return MainActivity.this;
    }

    @Override
    public Activity remoActivity() {
        return MainActivity.this;
    }

    @Override
    public boolean isshowback() {
        return false;
    }


}
