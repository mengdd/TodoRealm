package com.ddmeng.todorealm.home.add.list;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;

interface AddListContract {
    interface View extends BaseView {
        void exit();
    }

    interface Presenter extends BasePresenter<AddListContract.View> {
        void onDoneButtonClick(String title);

        void onCancelButtonClick();
    }
}
