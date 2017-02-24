package com.ddmeng.todorealm.home.add.task;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.home.add.list.AddListDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class AddTaskFragment extends Fragment implements AddTaskContract.View,
        SelectionsListAdapter.SelectListCallback {
    public static final String TAG = "AddTaskFragment";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.new_task_input_layout)
    TextInputLayout taskInputLayout;
    @BindView(R.id.new_task_edit_text)
    EditText taskEditTextView;
    @BindView(R.id.list_title)
    TextView listTitle;
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
    public void showAddNewListHint() {
        listTitle.setText(R.string.create_a_list);
        taskEditTextView.setHint(R.string.hint_add_a_task);
    }

    @Override
    public void showSelectListDialog() {
        SelectListDialog selectListDialog = new SelectListDialog();
        selectListDialog.setCallback(this);
        selectListDialog.show(getChildFragmentManager(), SelectListDialog.TAG);
    }

    @Override
    public void showAddNewListDialog() {
        AddListDialogFragment addListDialogFragment = new AddListDialogFragment();
        addListDialogFragment.show(getChildFragmentManager(), AddListDialogFragment.TAG);
    }

    @Override
    public void showSelectedList(String title) {
        listTitle.setText(title);
        String editTextHint = String.format(getString(R.string.hint_add_a_task_to_list), title);
        taskEditTextView.setHint(editTextHint);
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
                final String currentInput = taskEditTextView.getText().toString();
                presenter.onDoneClicked(currentInput);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.list_title)
    void onListTitleClicked() {
        presenter.onListItemClicked();
    }

    @OnTextChanged(R.id.new_task_edit_text)
    void onInputTextChanged(CharSequence text, int start, int count, int after) {
        if (text.length() == 0) {
            taskInputLayout.setError(getString(R.string.error_hint_title_can_not_be_empty));
            taskInputLayout.setErrorEnabled(true);

        } else {
            taskInputLayout.setErrorEnabled(false);
        }
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

    @Override
    public void onListSelected(long listId, String title) {
        presenter.onListSelected(listId, title);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
