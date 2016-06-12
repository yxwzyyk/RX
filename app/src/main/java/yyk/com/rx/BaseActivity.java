package yyk.com.rx;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yyk on 6/12/16.
 */

public class BaseActivity extends AppCompatActivity {
    final String TAG = this.getClass().getName();
    Context mContext;
    List<Subscriber> mSubscriberList;
    Observable mLoadingObservable;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.v(TAG, "onCreate");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.v(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.v(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.v(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.v(TAG, "onStop");
        clean();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.v(TAG, "onDestroy");
    }

    private void init() {
        mContext = this;

        mSubscriberList = new ArrayList<>();

        mProgressDialog = new ProgressDialog(this);
        mLoadingObservable = Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
            mProgressDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    subscriber.onNext(true);
                }
                return false;
            });
        });
    }

    private void clean() {
        for (Subscriber s : mSubscriberList) {
            if (s.isUnsubscribed()) {
                s.unsubscribe();
            }
        }
    }

    public void showLoading(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideLoading() {
        mProgressDialog.hide();
    }

    public void toast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


}
