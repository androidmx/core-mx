package mx.gigigo.core.rxmvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class BaseFragment
        extends Fragment {

    protected Context context;

    @LayoutRes
    protected abstract int getLayoutId();
    protected abstract void onInitializeUIComponents();
    protected abstract void onInitializeMembers();
    protected abstract void onBindView(View root);
    protected abstract void onUnbindView();

    protected void onRestoreExtras(Bundle arguments) { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getArguments()) {
            onRestoreExtras(getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(getLayoutId(), container, false);
        onBindView(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitializeMembers();
        onInitializeUIComponents();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnbindView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
