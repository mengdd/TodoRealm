package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class HomeListPresenter implements HomeListContract.Presenter {
    private HomeListContract.View view;
    private TodoRepository todoRepository;
    private RealmResults<TodoList> allLists;

    HomeListPresenter() {
        todoRepository = TodoRepository.getInstance();
    }

    @Override
    public void loadAllLists() {
        allLists = todoRepository.getAllLists();
        allLists.addChangeListener(new RealmChangeListener<RealmResults<TodoList>>() {
            @Override
            public void onChange(RealmResults<TodoList> element) {
                LogUtils.d("onChange: " + element.size());
                if (view != null) {
                    view.updateLists(allLists);
                }

            }
        });

        view.updateLists(allLists);

    }

    @Override
    public void attachView(HomeListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        allLists.removeChangeListeners();
    }
}
