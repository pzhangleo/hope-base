package zhp.base.http.retrofit.calladapter;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;
import zhp.base.http.callback.ICallback;

/**
 * Created by zhangpeng on 17/3/21.
 */

public interface BaseCall<T> {
    void cancel();

    void enqueue(@Nullable ICallback<T> callback);

    Response<T> execute() throws IOException;

    BaseCall<T> clone();
}
