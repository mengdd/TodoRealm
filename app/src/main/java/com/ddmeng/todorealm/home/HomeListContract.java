package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.TodoList;

import io.realm.RealmResults;

interface HomeListContract {
    interface View extends BaseView {
        void bindListData(RealmResults<TodoList> lists);

        void notifyDataChanged();

        void showListDetail(TodoList list);
    }

    interface Presenter extends BasePresenter<HomeListContract.View> {
        void loadAllLists();

        void onListItemClicked(TodoList list);

        void enterActionMode(TodoList list);

        void exitActionMode();

        void deleteSelectedItems();

        void onDestroy();
    }
}
