package com.ddmeng.todorealm.home.add.task;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddTaskFragment extends Fragment implements AddTaskContract.View {
    public static final String TAG = "AddTaskFragment";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.new_task_edit_text)
    EditText newTaskEditTextView;
    private AddTaskContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddTaskPresenter(TodoRepository.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        presenter.init();
    }

    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_task_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                presenter.onCloseClicked();
                return true;
            }
            case R.id.action_done: {
                // TODO use the selected list id
                final String currentInput = newTaskEditTextView.getText().toString();
                presenter.onDoneClicked(currentInput, 0);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void exit() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
