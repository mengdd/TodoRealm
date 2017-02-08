package com.ddmeng.todorealm.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.home.viewholder.HomeFooterViewHolder;
import com.ddmeng.todorealm.home.viewholder.HomeHeaderViewHolder;
import com.ddmeng.todorealm.home.viewholder.HomeListViewHolder;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_LIST = 1;
    private static final int VIEW_TYPE_FOOTER = 2;
    private List<TodoList> todoLists;
    private HomeListCallback callback;

    public HomeListAdapter(HomeListCallback callback) {
        this.callback = callback;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                View headerView = layoutInflater.inflate(R.layout.home_header_view_holder_layout, parent, false);
                return new HomeHeaderViewHolder(headerView);
            }
            case VIEW_TYPE_FOOTER: {
                View footerView = layoutInflater.inflate(R.layout.home_footer_view_holder_layout, parent, false);
                return new HomeFooterViewHolder(footerView, callback);
            }
            case VIEW_TYPE_LIST: {
                View listView = layoutInflater.inflate(R.layout.home_list_view_holder_layout, parent, false);
                return new HomeListViewHolder(listView);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeListViewHolder) {
            ((HomeListViewHolder) holder).populate(todoLists.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == todoLists.size() + 1) {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return todoLists.size() + 2;
    }

    public interface HomeListCallback {
        void showAddNewList();
    }
}
