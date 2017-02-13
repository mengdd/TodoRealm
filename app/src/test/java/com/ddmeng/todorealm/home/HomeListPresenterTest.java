package com.ddmeng.todorealm.home;

import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

public class HomeListPresenterTest {
    @Mock
    HomeListContract.View view;
    @Mock
    TodoRepository repository;

    private HomeListPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new HomeListPresenter(repository);
        presenter.attachView(view);
    }

    @Test
    public void shouldInitViewsWhenInit() throws Exception {
        presenter.init();

        verify(view).initViews();
    }

    @Ignore
    @Test
    public void shouldLoadDataFromRepoWhenLoadAllLists() throws Exception {
//        RealmResults mockResults = mock(RealmResults.class);
//        RealmResults<TodoList> todoLists = new RealmResults<TodoList>();
//        when(repository.getAllLists()).thenReturn(mockResults);
        // because the RealmResults can not be mocked or new, this test can't be run

        presenter.loadAllLists();

        verify(repository).getAllLists();
    }

    @Test
    public void shouldShowAddListWhenCreateListClicked() throws Exception {
        presenter.onCreateListItemClicked();

        verify(view).showAddNewList();
    }

    @Test
    public void shouldShowListDetailWhenListItemClicked() throws Exception {
        TodoList list = new TodoList();

        presenter.onListItemClicked(list);

        verify(view).showListDetail(list);
    }

    @Test
    public void shouldStartActionModeWhenListItemLongClicked() throws Exception {
        TodoList list = new TodoList();

        presenter.onListItemLongClicked(list);

        verify(view).startActionMode();
    }

    @Test
    public void shouldDoExitWorkWhenActionModeDestroyed() throws Exception {
        presenter.onDestroyActionMode();

        verify(view).onExitActionMode();
    }

    @Test
    public void shouldDeleteSelectedItemsFromRepo() throws Exception {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        presenter.deleteSelectedItems(ids);

        verify(repository).deleteLists(ids);
    }
}