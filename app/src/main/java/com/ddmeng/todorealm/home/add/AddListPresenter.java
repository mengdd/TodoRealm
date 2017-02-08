package com.ddmeng.todorealm.home.add;

import android.text.TextUtils;

import com.ddmeng.todorealm.data.TodoRepository;

import io.realm.Realm;

class AddListPresenter implements AddListContract.Presenter {

    private AddListContract.View view;
    private TodoRepository repository;

    AddListPresenter() {
        repository = TodoRepository.getInstance();
    }

    @Override
    public void onDoneButtonClick(String title) {
        if (!TextUtils.isEmpty(title)) {
            repository.addNewList(title, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (view != null) {
                        view.finish();
                    }
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.finish();
                    }
                }
            });
        }

    }

    @Override
    public void onCancelButtonClick() {
        if (view != null) {
            view.finish();
        }
    }

    @Override
    public void attachView(AddListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
