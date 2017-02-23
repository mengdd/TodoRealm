package com.ddmeng.todorealm.detail.task;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.utils.LogUtils;

import io.realm.Realm;
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
                task.getId();
                view.updateViews(task);
            }
        });
        view.initViews();
        view.updateViews(task);
    }

    @Override
    public void onDestroy() {
        task.removeChangeListeners();
    }

    @Override
    public void onEditActionExpanded() {
        view.showEditActionText(task.getTitle());
    }

    @Override
    public void onEditActionCollapsed(String newTitle) {
        if (!task.getTitle().equals(newTitle)) {
            repository.updateTaskTitle(task.getId(), newTitle);
        }
    }

    @Override
    public void onDeleteMenuItemClicked() {
        repository.deleteTask(task.getId(), new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.exit();
                }

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtils.e("TaskDetail", "Delete failed", error);
            }
        });
    }

    @Override
    public void onNoteEditorActionDone(String newNote) {
        LogUtils.d("getNote: " + task.getNote() + ", newNote: " + newNote);
        if ((task.getNote() == null && newNote != null) || !task.getNote().equals(newNote)) {
            repository.updateTaskNote(task.getId(), newNote);
        }
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
