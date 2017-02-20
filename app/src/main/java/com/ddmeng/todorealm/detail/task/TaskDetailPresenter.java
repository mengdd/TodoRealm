package com.ddmeng.todorealm.detail.task;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class TaskDetailPresenter implements TaskDetailContract.Presenter {
    private TaskDetailContract.View view;
    private TodoRepository repository;
    private Task task;

    public TaskDetailPresenter(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void init(long taskId) {
        RealmResults<Task> taskResults = repository.queryTask(taskId);
        if (taskResults.size() > 0) {
            task = taskResults.get(0);
        }
        task.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {

            }
        });
        view.initViews(task.getTitle());
    }

    @Override
    public void onDestroy() {
        task.removeChangeListeners();
    }

    @Override
    public void attachView(TaskDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
