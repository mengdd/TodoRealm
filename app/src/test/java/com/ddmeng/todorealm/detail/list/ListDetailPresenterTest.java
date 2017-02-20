package com.ddmeng.todorealm.detail.list;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

public class ListDetailPresenterTest {

    @Mock
    ListDetailContract.View view;
    @Mock
    TodoRepository repository;

    private ListDetailPresenter presenter;
    private static final long MOCK_LIST_ID = 123L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ListDetailPresenter(repository, MOCK_LIST_ID);
        presenter.attachView(view);
    }

    @Test
    public void shouldUpdateStateWhenItemCheckedChanged() throws Exception {
        Task task = new Task();
        long taskId = 1L;
        task.setId(taskId);

        presenter.onTaskItemCheckedChanged(task, true);

        verify(repository).updateTaskState(taskId, true);
    }

    @Test
    public void shouldShowTaskDetailWhenItemClicked() throws Exception {
        Task task = new Task();

        presenter.onTaskItemClicked(task);

        verify(view).showTaskDetail(task);
    }

    @Test
    public void shouldStartActionModeWhenItemLongClicked() throws Exception {
        Task task = new Task();

        presenter.onTaskItemLongClicked(task);

        verify(view).startActionMode();
    }

    @Test
    public void shouldInvokeOnExitWhenActionModeDestroyed() throws Exception {
        presenter.onDestroyActionMode();

        verify(view).onExitActionMode();
    }

    @Test
    public void shouldDeleteItemsFromRepositoryWhenDeleting() throws Exception {
        List<Long> itemIds = new ArrayList<>();

        presenter.deleteSelectedItems(itemIds);

        verify(repository).deleteTasks(itemIds);
    }
}