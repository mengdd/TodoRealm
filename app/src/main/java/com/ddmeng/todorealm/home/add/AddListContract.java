package com.ddmeng.todorealm.home.add;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;

interface AddListContract {
    interface View extends BaseView {
        void finish();
    }

    interface Presenter extends BasePresenter<AddListContract.View> {
        void onDoneButtonClick(String title);

        void onCancelButtonClick();
    }
}
