package com.ddmeng.todorealm.data;

import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoRepository {

    private Realm realm;
    private volatile static TodoRepository instance;

    private TodoRepository() {
    }

    public static TodoRepository getInstance() {
        if (instance == null) {
            synchronized (TodoRepository.class) {
                if (instance == null) {
                    instance = new TodoRepository();
                }
            }
        }
        return instance;
    }

    public RealmResults<TodoList> getAllLists() {
        return realm.where(TodoList.class).findAll();
    }

    public void addNewList(final String title, final Realm.Transaction.OnSuccess onSuccess,
                           final Realm.Transaction.OnError onError) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoList list = realm.createObject(TodoList.class);
                list.setTitle(title);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                LogUtils.d("insert success");
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtils.d("insert failed");
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }

    public void getRealm() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }
}
