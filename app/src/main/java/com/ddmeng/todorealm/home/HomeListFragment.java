package com.ddmeng.todorealm.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeListFragment extends Fragment implements HomeListAdapter.HomeListCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_list)
    RecyclerView homeList;
    private HomeListAdapter homeListAdapter;


    public HomeListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initViews();
    }

    private void initViews() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        homeList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeListAdapter = new HomeListAdapter(this);
        homeList.setAdapter(homeListAdapter);
    }

    @Override
    public void showAddNewList() {
        AddListDialogFragment addListDialogFragment = new AddListDialogFragment();
        addListDialogFragment.show(getChildFragmentManager(), AddListDialogFragment.TAG);
    }
}
