package com.ddmeng.todorealm.detail.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.detail.EditActionViewHolder;
import com.ddmeng.todorealm.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;


public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    public static final String TAG = "TaskDetailFragment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.task_detail_note)
    TextView noteView;

    private static final String ARG_TASK_ID = "task_id";
    private long taskId;
    private TaskDetailContract.Presenter presenter;
    private EditActionViewHolder editActionViewHolder;

    public TaskDetailFragment() {
    }

    public static TaskDetailFragment newInstance(long taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getLong(ARG_TASK_ID);
        }
        presenter = new TaskDetailPresenter(TodoRepository.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.init(taskId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_detail_options_menu, menu);
        MenuItem editItem = menu.findItem(R.id.action_edit);
        View editActionView = MenuItemCompat.getActionView(editItem);
        editActionViewHolder = new EditActionViewHolder(editActionView, editItem);
        MenuItemCompat.setOnActionExpandListener(editItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                presenter.onEditActionExpanded();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                presenter.onEditActionCollapsed(editActionViewHolder.getCurrentText());
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                exit();
                return true;
            }
            case R.id.action_delete: {
                presenter.onDeleteMenuItemClicked();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public void updateViews(final Task task) {
        toolbar.setTitle(task.getTitle());
        noteView.setText(task.getNote());
    }

    @Override
    public void showEditActionText(String title) {
        editActionViewHolder.showCurrentText(title);
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
    public void exit() {
        getFragmentManager().popBackStack();
    }

    @OnEditorAction(R.id.task_detail_note)
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            KeyboardUtils.hideKeyboard(view.getContext(), view);
            view.clearFocus();
            presenter.onNoteEditorActionDone(view.getText().toString());
            return true;
        }
        return false;
    }
}
