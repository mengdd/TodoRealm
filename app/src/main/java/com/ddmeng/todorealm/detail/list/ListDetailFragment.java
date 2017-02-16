package com.ddmeng.todorealm.detail.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.ui.multiselect.MultiSelector;
import com.ddmeng.todorealm.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

public class ListDetailFragment extends Fragment implements ListDetailContract.View, TaskListAdapter.TaskListCallback {

    public static final String TAG = "ListDetailFragment";
    private static final String ARG_LIST_ID = "list_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.new_task_input)
    EditText newTaskInput;
    @BindView(R.id.tasks_list)
    RecyclerView tasksList;

    private ListDetailPresenter presenter;
    private long listId;
    private TaskListAdapter adapter;
    private MultiSelector multiSelector;
    private ActionMode actionMode;

    public static ListDetailFragment newInstance(long id) {
        ListDetailFragment fragment = new ListDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_LIST_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listId = getArguments().getLong(ARG_LIST_ID);
        }
        presenter = new ListDetailPresenter(TodoRepository.getInstance(), listId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.init();
    }

    @Override
    public void initViews(String title) {
        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle(title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tasksList.setLayoutManager(new LinearLayoutManager(getContext()));
        multiSelector = new MultiSelector();
        adapter = new TaskListAdapter(this, multiSelector);
        tasksList.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            LogUtils.d("home button clicked <-");
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bingTasksData(List<Task> taskList) {
        adapter.setTaskList(taskList);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearInput() {
        newTaskInput.setText(null);
    }

    @Override
    public void showTaskDetail(Task task) {

    }

    @Override
    public void startActionMode() {
        actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.task_list_context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete: {
                        presenter.deleteSelectedItems(multiSelector.getSelectedItemIds());
                        mode.finish();
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                presenter.onDestroyActionMode();
            }
        });
    }

    @Override
    public void onExitActionMode() {
        multiSelector.setSelectable(false);
        multiSelector.clearSelections();
        actionMode = null;
    }

    @OnEditorAction(R.id.new_task_input)
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        LogUtils.d("actionId: " + actionId + ", event Action: " + event);
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            presenter.addNewTask(newTaskInput.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onTaskItemClicked(Task task) {
        presenter.onTaskItemClicked(task);
    }

    @Override
    public void onTaskItemLongClicked(Task task) {
        presenter.onTaskItemLongClicked(task);
    }
}
