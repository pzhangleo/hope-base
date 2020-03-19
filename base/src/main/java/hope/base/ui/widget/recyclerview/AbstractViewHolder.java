package hope.base.ui.widget.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.LayoutRes;

/**
 * Created by zhangpeng on 17/1/13.
 */
public abstract class AbstractViewHolder<T> extends BaseViewHolder {

    public AbstractViewHolder(View view) {
        super(view);
    }

    public AbstractViewHolder(ViewGroup parent, @LayoutRes int id) {
        super(LayoutInflater.from(parent.getContext()).inflate(id, parent, false));

    }

    public abstract void bindData(T data);
}
