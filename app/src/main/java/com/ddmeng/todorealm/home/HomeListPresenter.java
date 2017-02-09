package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class HomeListPresenter implements HomeListContract.Presenter {
    private HomeListContract.View view;
    private TodoRepository todoRepository;
    private RealmResults<TodoList> allLists;
    private boolean isInActionMode;
    private List<TodoList> selectedLists;

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
    public void onListItemClicked(TodoList list) {
        if (isInActionMode) {
            if (!selectedLists.contains(list)) {
                selectedLists.add(list);
            } else {
                selectedLists.remove(list);
            }
        } else {
            view.showListDetail(list);
        }
    }


    @Override
    public void enterActionMode(TodoList list) {
        isInActionMode = true;
        if (selectedLists == null) {
            selectedLists = new ArrayList<>();
        }
        selectedLists.add(list);
    }

    @Override
    public void exitActionMode() {
        isInActionMode = false;
        selectedLists.clear();
        view.notifyDataChanged();
    }

    @Override
    public void deleteSelectedItems() {
        todoRepository.deleteLists(selectedLists);
    }
}
