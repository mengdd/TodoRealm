package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.TodoList;

import io.realm.RealmResults;

interface HomeListContract {
    interface View extends BaseView {
        void updateLists(RealmResults<TodoList> lists);
    }

    interface Presenter extends BasePresenter<HomeListContract.View> {
        void loadAllLists();

        void onDestroy();
    }
}
