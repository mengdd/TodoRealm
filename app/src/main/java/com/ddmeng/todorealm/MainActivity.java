package com.ddmeng.todorealm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.detail.list.ListDetailFragment;
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment homeListFragment = fragmentManager.findFragmentByTag(HomeListFragment.TAG);
        if (homeListFragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_content, new HomeListFragment(), HomeListFragment.TAG)
                    .commit();
        }
    }

    public void showListDetailFragment(long todoListId) {
        ListDetailFragment listDetailFragment = ListDetailFragment.newInstance(todoListId);
        showFragment(listDetailFragment, ListDetailFragment.TAG);
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment foundFragment = fragmentManager.findFragmentByTag(tag);
        if (foundFragment == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        todoRepository.close();
    }
}
