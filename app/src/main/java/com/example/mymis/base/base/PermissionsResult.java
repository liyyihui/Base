package com.example.mymis.base.base;

/**
 * 作者：LYH   2017/8/29
 * <p>
 * 邮箱：945131558@qq.com
 */

public interface PermissionsResult {
    public void OnlistenerPermissiond(String[] permissions, int[] grantResults);
    public void  OnListenerExistingPermissiond(String[] permissions);
    public void  OnListenerSmallSDK23();
}
