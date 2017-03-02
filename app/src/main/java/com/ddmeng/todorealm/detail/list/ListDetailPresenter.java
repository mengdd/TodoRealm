package com.ddmeng.todorealm.detail.list;

import android.text.TextUtils;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

class ListDetailPresenter implements ListDetailContract.Presenter {
    private ListDetailContract.View view;
    private final TodoRepository todoRepository;
    private final long listId;
    private boolean isInActionMode;
    private TodoList list;

    ListDetailPresenter(TodoRepository todoRepository, long listId) {
        this.todoRepository = todoRepository;
        this.listId = listId;
    }

    @Override
    public void init() {
        RealmResults<TodoList> listResults = todoRepository.queryList(listId);
        list = listResults.get(0);
        view.initViews(list.getTitle());

        bindData();
        list.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                if (view != null) {
                    bindData();
                    view.notifyDataChanged(list.getTitle());
                }
            }
        });
    }

    private void bindData() {
        RealmResults<Task> todoTasks = todoRepository.queryTasks(listId, false);
        RealmResults<Task> doneTasks = todoRepository.queryTasks(listId, true);
        view.bingTasksData(todoTasks, doneTasks);
    }

    @Override
    public void addNewTask(String taskTitle) {
        if (!TextUtils.isEmpty(taskTitle)) {
            todoRepository.addNewTask(listId, taskTitle, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (view != null) {
                        view.clearInput();
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
    public void onTaskItemCheckedChanged(Task task, boolean isDone) {
        todoRepository.updateTaskState(task.getId(), isDone);
    }

    @Override
    public void onTaskItemClicked(Task task) {
        view.showTaskDetail(task);
    }

    @Override
    public void onTaskItemLongClicked(Task task) {
        if (!isInActionMode) {
            isInActionMode = true;
            view.startDeleteActionMode();
        }
    }

    @Override
    public void onDestroyDeleteActionMode() {
        isInActionMode = false;
        view.onExitDeleteActionMode();
    }

    @Override
    public void deleteSelectedItems(List<Long> itemIds) {
        todoRepository.deleteTasks(itemIds);
    }

    @Override
    public void onEditActionExpanded() {
        view.showEditActionText(list.getTitle());
    }

    @Override
    public void onEditActionCollapsed(String updatedTitle) {
        if (!list.getTitle().equalsIgnoreCase(updatedTitle)) {
            todoRepository.updateListTitle(listId, updatedTitle);
        }
    }

    @Override
    public void onDeleteMenuItemClicked() {
        todoRepository.deleteList(listId, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.exit();
                }

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtils.e("Delete", "delete list failed", error);
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
        list.removeChangeListeners();
    }
}
