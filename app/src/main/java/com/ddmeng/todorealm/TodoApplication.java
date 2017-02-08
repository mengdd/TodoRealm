package com.ddmeng.todorealm;

import android.app.Application;

import io.realm.Realm;

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
