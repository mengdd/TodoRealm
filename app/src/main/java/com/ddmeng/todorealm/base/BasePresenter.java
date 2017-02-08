package com.ddmeng.todorealm.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
