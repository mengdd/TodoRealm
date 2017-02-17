package com.ddmeng.todorealm.detail.list;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActionViewHolder {

    @BindView(R.id.edit_text_view)
    EditText editTextView;
    @BindView(R.id.done_button)
    View doneButton;

    private View itemView;
    private MenuItem menuItem;

    public EditActionViewHolder(View view, MenuItem menuItem) {
        this.itemView = view;
        this.menuItem = menuItem;
        ButterKnife.bind(this, view);
    }

    public void showCurrentText(CharSequence text) {
        editTextView.setText(text);
        editTextView.setSelection(text.length());
        editTextView.requestFocus();
        KeyboardUtils.showKeyboard(itemView.getContext());

        editTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doneButton.setEnabled(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String getCurrentText() {
        return editTextView.getText().toString();
    }

    @OnClick(R.id.done_button)
    void onDoneButtonClicked() {
        menuItem.collapseActionView();
    }
}
