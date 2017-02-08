package com.ddmeng.todorealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.home.HomeListFragment;

public class MainActivity extends AppCompatActivity {

    private TodoRepository todoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showHomeList();

        todoRepository = TodoRepository.getInstance();
        todoRepository.getRealm();
    }

    private void showHomeList() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, new HomeListFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        todoRepository.close();
    }
}
