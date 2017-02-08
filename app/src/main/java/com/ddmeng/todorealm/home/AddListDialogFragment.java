package com.ddmeng.todorealm.home;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


public class AddListDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "AddListDialogFragment";
    @BindView(R.id.input_edit_text)
    TextInputEditText editText;

    private BottomSheetBehavior.BottomSheetCallback bottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN: {
                    LogUtils.d("Bottom sheet hidden to dismiss");
                    dismiss();
                    break;
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    LogUtils.d("expanded");
                    break;
                }
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    LogUtils.d("collapsed");
                    break;
                }

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

        ButterKnife.bind(this, contentView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint(); // if onCreateView() return null, onViewCreated() will not be invoked
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.done_button)
    void onDoneButtonClicked() {
        final String currentInput = editText.getText().toString();
        if (!TextUtils.isEmpty(currentInput)) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    TodoList list = realm.createObject(TodoList.class);
                    list.setTitle(currentInput);

                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    LogUtils.d("insert success");
                    dismiss();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    LogUtils.d("insert failed");
                    // TODO
                }
            });

        }

    }

    @OnClick(R.id.cancel_button)
    void onCancelButtonClicked() {
        dismiss();
    }
}
