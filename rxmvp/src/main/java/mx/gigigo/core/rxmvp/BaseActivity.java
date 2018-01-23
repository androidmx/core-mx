package mx.gigigo.core.rxmvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author JG - December 19, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class BaseActivity
        extends AppCompatActivity {

    private Toolbar toolbar;

    @LayoutRes
    protected abstract int getLayoutId();
    protected abstract void onInitializeUIComponents();
    protected abstract void onInitializeMembers();
    protected abstract void onBindView();
    protected abstract void onUnbindView();

    protected void onRestoreExtras(Bundle arguments) { }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onBindView();

        if (null != getIntent() && null != getIntent().getExtras()) {
            onRestoreExtras(getIntent().getExtras());
        }

        onInitializeMembers();
        onInitializeUIComponents();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnbindView();
    }

    public void setupToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;

        if(null != this.toolbar) {
            setSupportActionBar(this.toolbar);
        }
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setKeyBoardStateFrom(View view, boolean show) {
        if(null == view) {
            throw new NullPointerException("The view must not be null.");
        }

        InputMethodManager inputMethodManager
                = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(null != inputMethodManager) {
            if(show) {
                if(null != view.getApplicationWindowToken()) {
                    inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
                            InputMethodManager.SHOW_FORCED,
                            0);
                }
            } else {
                if(null != view.getWindowToken()) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

}
