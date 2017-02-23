package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class HomeListPresenter implements HomeListContract.Presenter {
    private HomeListContract.View view;
    private TodoRepository todoRepository;
    private RealmResults<TodoList> allLists;
    private boolean isInActionMode;

    HomeListPresenter(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void init() {
        view.initViews();
    }

    @Override
    public void loadAllLists() {
        allLists = todoRepository.getAllLists();
        allLists.addChangeListener(new RealmChangeListener<RealmResults<TodoList>>() {
            @Override
            public void onChange(RealmResults<TodoList> element) {
                LogUtils.d("onChange: " + element.size());
                if (view != null) {
                    view.notifyDataChanged();
                }
            }
        });

        view.bindListData(allLists);
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

    @Override
    public void onCreateListItemClicked() {
        if (isInActionMode) {
            view.finishActionMode();
        }
        view.showAddNewList();
    }

    @Override
    public void onFloatingActionButtonClicked() {
        view.showAddNewTask();
    }

    @Override
    public void onListItemClicked(TodoList list) {
        view.showListDetail(list);
    }

    @Override
    public void onListItemLongClicked(TodoList list) {
        if (!isInActionMode) {
            isInActionMode = true;
            view.startActionMode();
        }

    }

    @Override
    public void onDestroyActionMode() {
        isInActionMode = false;
        view.onExitActionMode();
    }

    @Override
    public void deleteSelectedItems(List<Long> itemIds) {
        todoRepository.deleteLists(itemIds);
    }
}
