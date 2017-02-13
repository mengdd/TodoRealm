package com.ddmeng.todorealm.home.add;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;


public class AddListDialogFragment extends BottomSheetDialogFragment implements AddListContract.View {

    public static final String TAG = "AddListDialogFragment";
    @BindView(R.id.input_edit_text)
    TextInputEditText editText;

    private AddListContract.Presenter presenter;

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

        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        presenter = new AddListPresenter();
        presenter.attachView(this);
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
        presenter.onDoneButtonClick(currentInput);
    }

    @OnClick(R.id.cancel_button)
    void onCancelButtonClicked() {
        presenter.onCancelButtonClick();
    }

    @Override
    public void finish() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @OnEditorAction(R.id.input_edit_text)
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            presenter.onDoneButtonClick(editText.getText().toString());
            return true;
        }
        return false;
    }
}
