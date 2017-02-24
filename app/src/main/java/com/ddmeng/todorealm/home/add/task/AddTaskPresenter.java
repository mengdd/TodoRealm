package com.ddmeng.todorealm.home.add.task;

import android.text.TextUtils;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private static final int INVALID_LIST_ID = -1;
    private AddTaskContract.View view;
    private TodoRepository repository;
    private long selectedListId = INVALID_LIST_ID;
    private RealmResults<TodoList> listsResults;

    public AddTaskPresenter(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init() {
        view.initViews();
        listsResults = repository.getAllLists();
        useFirstListAsDefault();

        listsResults.addChangeListener(new RealmChangeListener<RealmResults<TodoList>>() {
            @Override
            public void onChange(RealmResults<TodoList> element) {
                if (view != null) {
                    useFirstListAsDefault();
                }
            }
        });
    }

    private void useFirstListAsDefault() {
        if (listsResults.size() > 0) {
            TodoList list = listsResults.get(0);
            selectedListId = list.getId();
            view.showSelectedList(list.getTitle());
        } else {
            view.showAddNewListHint();
        }
    }


    @Override
    public void onDoneClicked(String taskTitle) {
        if (selectedListId != INVALID_LIST_ID && !TextUtils.isEmpty(taskTitle)) {
            repository.addNewTask(selectedListId, taskTitle, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (view != null) {
                        view.exit();
                    }

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {

                }
            });
        }
    }

    @Override
    public void onCloseClicked() {
        view.exit();
    }

    @Override
    public void onListItemClicked() {
        if (listsResults.size() > 0) {
            view.showSelectListDialog();
        } else {
            view.showAddNewListDialog();
        }
    }

    @Override
    public void onListSelected(long listId, String title) {
        selectedListId = listId;
        view.showSelectedList(title);
    }

    @Override
    public void attachView(AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        listsResults.removeChangeListeners();
    }

}
