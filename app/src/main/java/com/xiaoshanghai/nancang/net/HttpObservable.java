package com.xiaoshanghai.nancang.net;

import com.xiaoshanghai.nancang.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class HttpObservable<T> extends Observable<T> {
    private final Call<T> originalCall;
    private boolean isAsync = false;

    private int maxRetries = 0;
    private int retryDelayMillis = 0;

    public HttpObservable(Call<T> originalCall, boolean isAsync) {
        this.originalCall = originalCall;
        this.isAsync = isAsync;
    }

    public HttpObservable(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    public HttpObservable(Call<T> originalCall, int maxRetries, int retryDelayMillis) {
        this.originalCall = originalCall;
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    public void execOnThread(LifecycleTransformer<T> transformer, Observer<? super T> observer) {
        execOnThread(observer, transformer);
    }

    private void execOnThread(Observer<? super T> observer, LifecycleTransformer<T> transformer) {
        if (transformer == null) {
            LogUtils.e("execOnThread", "transformer is NULL");
            return;
        }
        compose(transformer)
//                .retryWhen(new RetryWithDelay(maxRetries, retryDelayMillis))
                .compose(new ObservableTransformer<T, T>() {
                    @Override
                    public ObservableSource<T> apply(Observable<T> observable) {
                        return observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(observer);

    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        if (isAsync)
            isAsyncSubscribeActual(observer);
        else
            execSubscribeActual(observer);
    }

    /**
     * 需要同步的话 并发
     *
     * @param observer
     */
    private void isAsyncSubscribeActual(Observer<? super T> observer) {
        // Since Call is a one-shot type, clone it for each new observer.
        Call<T> call = originalCall.clone();
        CallDisposable disposable = new CallDisposable(call);
        observer.onSubscribe(disposable);
        if (disposable.isDisposed()) {
            return;
        }

        boolean terminated = false;
        try {
            Response<T> response = call.execute();
            if (!disposable.isDisposed()) {
                observer.onNext(response.body());
            }
            if (!disposable.isDisposed()) {
                terminated = true;
                observer.onComplete();
            }
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            if (terminated) {
                RxJavaPlugins.onError(t);
            } else if (!disposable.isDisposed()) {
                try {
                    observer.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
            }
        }
    }

    private void execSubscribeActual(Observer<? super T> observer) {
        // Since Call is a one-shot type, clone it for each new observer.
        Call<T> call = originalCall.clone();
        CallCallback<T> callback = new CallCallback<>(call, observer);
        observer.onSubscribe(callback);
        if (!callback.isDisposed()) {
            call.enqueue(callback);
        }
    }

    /**
     * 直接执行
     *
     * @param <T>
     */
    private static final class CallCallback<T> implements Disposable, Callback<T> {
        private final Call<?> call;
        private final Observer<? super T> observer;
        private volatile boolean disposed;
        boolean terminated = false;

        CallCallback(Call<?> call, Observer<? super T> observer) {
            this.call = call;
            this.observer = observer;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (disposed) return;

            try {
                if (response.code() != 200) {
                    observer.onError(new HttpException(response));
                } else {
                    observer.onNext(response.body());
                }
                if (!disposed) {
                    terminated = true;
                    observer.onComplete();
                }
            } catch (Throwable t) {
                if (terminated) {
                    RxJavaPlugins.onError(t);
                } else if (!disposed) {
                    try {
                        observer.onError(t);
                    } catch (Throwable inner) {
                        Exceptions.throwIfFatal(inner);
                        RxJavaPlugins.onError(new CompositeException(t, inner));
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (call.isCanceled()) return;

            try {
                observer.onError(t);
            } catch (Throwable inner) {
                Exceptions.throwIfFatal(inner);
                RxJavaPlugins.onError(new CompositeException(t, inner));
            }
        }

        @Override
        public void dispose() {
            LogUtils.e("CallCallback", call.request().toString());
            disposed = true;
            call.cancel();
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }

    /**
     *
     */
    private static final class CallDisposable implements Disposable {
        private final Call<?> call;
        private volatile boolean disposed;

        CallDisposable(Call<?> call) {
            this.call = call;
        }

        @Override
        public void dispose() {
            LogUtils.e("CallDisposable", call.request().toString());
            disposed = true;
            call.cancel();
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }

    private class RetryWithDelay implements
            Function<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> apply(Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Function<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> apply(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                LogUtils.e("get error, it will try after " + retryDelayMillis
                                        + " millisecond, retry count " + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.SECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }
}
