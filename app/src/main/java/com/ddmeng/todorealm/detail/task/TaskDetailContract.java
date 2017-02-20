package com.ddmeng.todorealm.detail.task;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;

public interface TaskDetailContract {
    interface View extends BaseView {
        void initViews(String title);
    }

    interface Presenter extends BasePresenter<TaskDetailContract.View> {
        void init(long taskId);

        void onDestroy();
    }
}
