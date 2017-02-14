package com.ddmeng.todorealm.detail.list;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class ListDetailPresenter implements ListDetailContract.Presenter {
    private ListDetailContract.View view;
    private final TodoRepository todoRepository;
    private final long listId;
    private RealmResults<TodoList> listResults;

    ListDetailPresenter(TodoRepository todoRepository, long listId) {
        this.todoRepository = todoRepository;
        this.listId = listId;
    }

    @Override
    public void init() {
        listResults = todoRepository.queryList(listId);
        TodoList list = listResults.get(0);
        view.initViews(list.getTitle());
        view.bingTasksData(list.getTasks());

        listResults.addChangeListener(new RealmChangeListener<RealmResults<TodoList>>() {
            @Override
            public void onChange(RealmResults<TodoList> element) {
                view.notifyDataChanged();
            }
        });
    }

    @Override
    public void attachView(ListDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        listResults.removeChangeListeners();
    }
}
