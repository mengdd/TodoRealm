package com.ddmeng.todorealm.home;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.utils.LogUtils;


public class AddListDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "AddListDialogFragment";
    private BottomSheetBehavior.BottomSheetCallback bottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                LogUtils.d("Bottom sheet hidden to dismiss");
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        LogUtils.footPrint();
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_add_list_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            bottomSheetBehavior.setBottomSheetCallback(bottomSheetBehaviorCallback);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        super.onViewCreated(view, savedInstanceState);
    }
}
