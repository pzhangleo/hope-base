package com.being.base.http.retrofit.calladapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangpeng on 17/2/21.
 */
@SuppressWarnings("unchecked")
public class RxThreadCallAdapterFactory extends CallAdapter.Factory {
    private RxJavaCallAdapterFactory rxFactory = RxJavaCallAdapterFactory.create();
    private Scheduler subscribeScheduler;
    private Scheduler observerScheduler;

    public static RxThreadCallAdapterFactory create() {
        return new RxThreadCallAdapterFactory(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public static RxThreadCallAdapterFactory create(Scheduler subscribeScheduler, Scheduler observerScheduler) {
        return new RxThreadCallAdapterFactory(subscribeScheduler, observerScheduler);
    }

    public RxThreadCallAdapterFactory(Scheduler subscribeScheduler, Scheduler observerScheduler) {
        this.subscribeScheduler = subscribeScheduler;
        this.observerScheduler = observerScheduler;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?, Observable<?>> callAdapter = (CallAdapter<?, Observable<?>>) rxFactory.get(returnType, annotations, retrofit);
        return callAdapter != null ? new ThreadCallAdapter(callAdapter) : null;
    }

    private final class ThreadCallAdapter<R> implements CallAdapter<R, Observable<?>> {
        CallAdapter<R, Observable<?>> delegateAdapter;

        ThreadCallAdapter(CallAdapter<R, Observable<?>> delegateAdapter) {
            this.delegateAdapter = delegateAdapter;
        }

        @Override
        public Type responseType() {
            return delegateAdapter.responseType();
        }

        @Override
        public Observable<?> adapt(Call<R> call) {
            return delegateAdapter.adapt(call)
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observerScheduler);
        }
    }
}