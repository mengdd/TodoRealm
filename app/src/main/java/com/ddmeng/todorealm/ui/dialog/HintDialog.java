package com.ddmeng.todorealm.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

public class HintDialog extends DialogFragment {

    private static final String TAG = "HintDialog";
    private String title;
    private String message;
    private String positive;
    private String negative;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message);
        if (negative != null) {
            builder.setNegativeButton(negative, negativeListener);
        }
        if (positive != null) {
            builder.setPositiveButton(positive, positiveListener);
        }
        return builder.create();
    }

    public static class Builder {
        private Context context;
        private String title = null;
        private String message = null;
        private String positive = null;
        private DialogInterface.OnClickListener positiveListener = null;
        private String negative = null;
        private DialogInterface.OnClickListener negativeListener = null;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int resId) {
            return setTitle(context.getString(resId));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(int resId) {
            return setMessage(context.getString(resId));
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(int resId, DialogInterface.OnClickListener listener) {
            return setPositiveButton(context.getString(resId), listener);
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            this.positive = text;
            this.positiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(int resId, DialogInterface.OnClickListener listener) {
            return setNegativeButton(context.getString(resId), listener);
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            this.negative = text;
            this.negativeListener = listener;
            return this;
        }

        public void show(FragmentManager fragmentManager) {
            show(fragmentManager, HintDialog.TAG);
        }

        public void show(FragmentManager fragmentManager, String tag) {
            HintDialog fragment = new HintDialog();
            fragment.title = title;
            fragment.message = message;
            fragment.positive = positive;
            fragment.negative = negative;
            fragment.positiveListener = positiveListener;
            fragment.negativeListener = negativeListener;
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, tag);
            ft.commitAllowingStateLoss();
        }
    }
}
