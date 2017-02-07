package com.ddmeng.todorealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ddmeng.todorealm.home.HomeListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showHomeList();
    }

    private void showHomeList() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, new HomeListFragment())
                .commit();
    }
}
