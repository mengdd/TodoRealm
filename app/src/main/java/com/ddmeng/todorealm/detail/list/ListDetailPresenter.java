package com.ddmeng.todorealm.detail.list;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class ListDetailPresenter implements ListDetailContract.Presenter {
    private ListDetailContract.View view;
    private final TodoRepository todoRepository;
    private final long listId;
    private RealmResults<TodoList> listResults;
    private boolean isInActionMode;
    private TodoList list;

    ListDetailPresenter(TodoRepository todoRepository, long listId) {
        this.todoRepository = todoRepository;
        this.listId = listId;
    }

    @Override
    public void init() {
        listResults = todoRepository.queryList(listId);
        list = listResults.get(0);
        view.initViews(list.getTitle());

        bindData();
        listResults.addChangeListener(new RealmChangeListener<RealmResults<TodoList>>() {
            @Override
            public void onChange(RealmResults<TodoList> element) {
                LogUtils.d("tasks size: " + element.get(0).getTasks().size());
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
        todoRepository.deleteTasks(itemIds);
    }

    @Override
    public void onMenuItemActionExpanded() {
        view.showEditActionText(list.getTitle());
    }

    @Override
    public void onMenuItemActionCollapsed(String updatedTitle) {
        if (!list.getTitle().equalsIgnoreCase(updatedTitle)) {
            todoRepository.updateListTitle(listId, updatedTitle);
        }
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
