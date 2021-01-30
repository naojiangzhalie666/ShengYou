package com.xiaoshanghai.nancang.net;


import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ObservableFactory extends CallAdapter.Factory {
    static int maxRetries;
    static int retryDelayMillis;
    static boolean isAsync;

    public static ObservableFactory create() {
        isAsync = false;
        return new ObservableFactory();
    }

    public static ObservableFactory createAsync() {
        isAsync = true;
        return create();
    }

    public static ObservableFactory create(int maxRetries, int retryDelayMillis) {
        ObservableFactory.maxRetries = maxRetries;
        ObservableFactory.retryDelayMillis = retryDelayMillis;
        return new ObservableFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new GACallAdapter(observableType);
    }

    public class GACallAdapter<R> implements CallAdapter<R, Object> {
        Type returnType;

        public GACallAdapter(Type returnType) {
            this.returnType = returnType;
        }

        @Override
        public Type responseType() {
            return returnType;
        }

        @Override
        public Object adapt(Call<R> call) {
            if (maxRetries > 0)
                return new HttpObservable<>(call, maxRetries, retryDelayMillis);
            return new HttpObservable<>(call);
        }
    }
}