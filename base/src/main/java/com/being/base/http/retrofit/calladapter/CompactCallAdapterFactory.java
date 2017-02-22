package com.being.base.http.retrofit.calladapter;

import com.being.base.http.callback.ICallback;
import com.being.base.http.exception.ApiException;
import com.being.base.http.model.IResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 兼容之前回调的适配类
 * Created by zhangpeng on 17/2/21.
 */
public class CompactCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != ICallback.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "CompactCall must have generic type (e.g., CompactCall<ResponseBody>)");
        }
        Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Executor callbackExecutor = retrofit.callbackExecutor();
        return new ErrorHandlingCallAdapter<>(responseType, callbackExecutor);
    }

    @SuppressWarnings("unchecked")
    private static final class ErrorHandlingCallAdapter<R> implements CallAdapter<CompactCall<R>> {
        private final Type responseType;
        private final Executor callbackExecutor;

        ErrorHandlingCallAdapter(Type responseType, Executor callbackExecutor) {
            this.responseType = responseType;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public <R> InternalCallAdapter adapt(Call<R> call) {
            return new InternalCallAdapter<>(call, callbackExecutor);
        }

    }

    public interface CompactCall<T> {
        void cancel();

        void enqueue(ICallback<T> callback);

        CompactCall<T> clone();

        // Left as an exercise for the reader...
        // TODO MyResponse<T> execute() throws MyHttpException;
    }

    /**
     * Adapts a {@link Call} to {@link CompactCall}.
     */
    @SuppressWarnings("CloneDoesntCallSuperClone")
    private static class InternalCallAdapter<T> implements CompactCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        InternalCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public void enqueue(final ICallback<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, final Response<T> response) {
                    if (callbackExecutor != null) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                handleResponse(response, callback);
                            }
                        });
                    } else {
                        handleResponse(response, callback);
                    }
                }

                @Override
                public void onFailure(Call<T> call, final Throwable t) {
                    if (callbackExecutor != null) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFail(ICallback.NO_NETWORK_STATUS_CODE, null, t);
                            }
                        });
                    } else {
                        callback.onFail(ICallback.NO_NETWORK_STATUS_CODE, null, t);
                    }
                }
            });
        }

        private void handleResponse(Response<T> response, ICallback<T> callback) {
            if (response.isSuccessful()) {
                T body = response.body();
                if (body instanceof IResponse) {
                    if (((IResponse) body).isSucceeded()) {
                        callback.onSuccess(body);
                    } else {
                        callback.onFail(((IResponse) body).getErrorCode(), body,
                                new ApiException(((IResponse) body).getErrorCode(), ((IResponse) body).getErrorMessage()));
                    }
                } else {
                    callback.onSuccess(body);
                }
            } else {
                callback.onFail(response.code(), response.body(), new ApiException(response.code(), response.message()));
            }
        }

        @Override
        public CompactCall<T> clone() {
            return new InternalCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}
