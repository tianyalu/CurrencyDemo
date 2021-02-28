package com.sty.currencydemo.app;

import android.app.Application;

import com.sty.currencydemo.db.ObjectBox;

/**
 * @Author: tian
 * @Date: 2021/2/28 15:14
 */
public class MyApplication extends Application {
    private static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ObjectBox.init(this);
    }

    public static MyApplication getApp() {
        return app;
    }
}
