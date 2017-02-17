package com.ddmeng.todorealm.detail.list;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.Task;

import java.util.List;

interface ListDetailContract {
    interface View extends BaseView {

        void initViews(String title);

        void bingTasksData(List<Task> taskList);

        void notifyDataChanged(String title);

        void clearInput();

        void showTaskDetail(Task task);

        void startActionMode();

        void onExitActionMode();

        void showEditActionText(CharSequence text);
    }

    interface Presenter extends BasePresenter<ListDetailContract.View> {

        void init();

        void onDestroy();

        void addNewTask(String taskTitle);

        void onTaskItemCheckedChanged(Task task, boolean isDone);

        void onTaskItemClicked(Task task);

        void onTaskItemLongClicked(Task task);

        void onDestroyActionMode();

        void deleteSelectedItems(List<Long> itemIds);

        void onMenuItemActionExpanded();

        void onMenuItemActionCollapsed(String text);
    }
}
