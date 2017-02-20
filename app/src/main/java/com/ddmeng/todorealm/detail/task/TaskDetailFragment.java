package com.ddmeng.todorealm.detail.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskDetailFragment extends Fragment implements TaskDetailContract.View {

    public static final String TAG = "TaskDetailFragment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.task_detail_title)
    TextView titleTextView;

    private static final String ARG_TASK_ID = "task_id";
    private long taskId;
    private TaskDetailContract.Presenter presenter;

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
    public void initViews(String title) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        titleTextView.setText(title);
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
}
