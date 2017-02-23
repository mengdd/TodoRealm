package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.TodoList;

import java.util.List;

import io.realm.RealmResults;

interface HomeListContract {
    interface View extends BaseView {
        void initViews();

        void bindListData(RealmResults<TodoList> lists);

        void notifyDataChanged();

        void showAddNewList();

        void showAddNewTask();

        void showListDetail(TodoList list);

        void startActionMode();

        void finishActionMode();

        void onExitActionMode();
    }

    interface Presenter extends BasePresenter<HomeListContract.View> {
        void init();

        void loadAllLists();

        void onCreateListItemClicked();

        void onListItemClicked(TodoList list);

        void onListItemLongClicked(TodoList list);

        void onDestroyActionMode();

        void deleteSelectedItems(List<Long> itemIds);

        void onFloatingActionButtonClicked();

        void onDestroy();
    }
}
