package com.ddmeng.todorealm.data;

import android.support.annotation.NonNull;

import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

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
        return realm.where(TodoList.class).findAll().sort("createdTime", Sort.ASCENDING);
    }

    public RealmResults<TodoList> queryList(long id) {
        return realm.where(TodoList.class).equalTo("id", id).findAll();
    }

    public void addNewList(final String title, final Realm.Transaction.OnSuccess onSuccess,
                           final Realm.Transaction.OnError onError) {
        Number maxIdNumber = realm.where(TodoList.class).max("id");
        final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoList list = realm.createObject(TodoList.class, nextId);
                list.setTitle(title);
                list.setCreatedTime(new Date().getTime());
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
                LogUtils.e("insert failed: " + error);
                if (onError != null) {
                    onError.onError(error);
                }
            }
        });
    }

    public void deleteLists(@NonNull final List<Long> listIds) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LogUtils.d("delete: " + listIds);
                Long[] ids = listIds.toArray(new Long[listIds.size()]);
                RealmResults<TodoList> toDeleteLists = realm.where(TodoList.class).in("id", ids).findAll();
                LogUtils.d("queryed results: " + toDeleteLists.size());
                toDeleteLists.deleteAllFromRealm();
            }
        });
    }

    public void updateListTitle(final long listId, final String newTitle) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TodoList> listResults = realm.where(TodoList.class).equalTo("id", listId).findAll();
                if (listResults.size() > 0) {
                    TodoList list = listResults.get(0);
                    list.setTitle(newTitle);
                    realm.copyToRealmOrUpdate(list);
                }
            }
        });
    }

    public void addNewTask(final long listId, final String taskTitle, final Realm.Transaction.OnSuccess onSuccess,
                           final Realm.Transaction.OnError onError) {
        LogUtils.d("add New Task to list " + listId + ", " + taskTitle);
        Number maxIdNumber = realm.where(Task.class).max("id");
        final long nextId = maxIdNumber != null ? maxIdNumber.longValue() + 1 : 1;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TodoList> results = realm.where(TodoList.class).equalTo("id", listId).findAll();
                if (results.size() > 0) {
                    TodoList list = results.get(0);
                    Task task = new Task();
                    task.setId(nextId);
                    task.setTitle(taskTitle);
                    task.setListId(listId);
                    task.setCreatedTime(new Date().getTime());
                    list.addTask(task);
                    realm.copyToRealmOrUpdate(list);
                }
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
                LogUtils.e("insert failed: " + error);
                if (onError != null) {
                    onError.onError(error);
                }

            }
        });
    }

    public void deleteTasks(@NonNull final List<Long> taskIds) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LogUtils.d("delete: " + taskIds);
                Long[] ids = taskIds.toArray(new Long[taskIds.size()]);
                RealmResults<Task> toDeleteTasks = realm.where(Task.class).in("id", ids).findAll();
                toDeleteTasks.deleteAllFromRealm();
            }
        });
    }

    public void updateTaskState(final long taskId, final boolean isDone) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
                if (results.size() > 0) {
                    Task task = results.get(0);
                    task.setDone(isDone);
                    realm.copyToRealmOrUpdate(task);
                }
            }
        });
    }

    public RealmResults<Task> queryTasks(final long listId, final boolean isDone) {
        return realm.where(Task.class)
                .equalTo("listId", listId)
                .equalTo("isDone", isDone)
                .findAll()
                .sort("createdTime", Sort.ASCENDING);
    }

    public RealmResults<Task> queryTask(final long taskId) {
        return realm.where(Task.class)
                .equalTo("id", taskId)
                .findAll();
    }

    public void getRealm() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }
}
