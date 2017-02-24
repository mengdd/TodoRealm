package com.ddmeng.todorealm.home.add.task;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;

public interface AddTaskContract {
    interface View extends BaseView {
        void initViews();

        void showSelectListDialog();

        void showAddNewListDialog();

        void showSelectedList(String title);

        void showAddNewListHint();

        void exit();
    }

    interface Presenter extends BasePresenter<AddTaskContract.View> {
        void init();

        void onDoneClicked(String taskTitle);

        void onCloseClicked();

        void onListItemClicked();

        void onListSelected(long listId, String title);

        void onDestroy();
    }
}
