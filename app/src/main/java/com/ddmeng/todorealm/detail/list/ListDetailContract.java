package com.ddmeng.todorealm.detail.list;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.Task;

import java.util.List;

interface ListDetailContract {
    interface View extends BaseView {

        void initViews(String title);

        void bingTasksData(List<Task> taskList);

        void notifyDataChanged();

        void clearInput();

        void showTaskDetail(Task task);

        void startActionMode();

        void onExitActionMode();
    }

    interface Presenter extends BasePresenter<ListDetailContract.View> {

        void init();

        void onDestroy();

        void addNewTask(String taskTitle);

        void onTaskItemClicked(Task task);

        void onTaskItemLongClicked(Task task);

        void onDestroyActionMode();

        void deleteSelectedItems(List<Long> itemIds);
    }
}
