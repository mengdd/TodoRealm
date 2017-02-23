package com.ddmeng.todorealm.home.add.task;

import android.text.TextUtils;

import com.ddmeng.todorealm.data.TodoRepository;

import io.realm.Realm;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private AddTaskContract.View view;
    private TodoRepository repository;

    public AddTaskPresenter(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init() {
        view.initViews();
    }

    @Override
    public void onDoneClicked(String taskTitle, long listId) {
        if (!TextUtils.isEmpty(taskTitle)) {
            repository.addNewTask(listId, taskTitle, new Realm.Transaction.OnSuccess() {
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
    public void attachView(AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
