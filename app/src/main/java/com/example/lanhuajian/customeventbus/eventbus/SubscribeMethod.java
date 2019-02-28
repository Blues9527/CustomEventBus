package com.example.lanhuajian.customeventbus.eventbus;

import java.lang.reflect.Method;

public class SubscribeMethod {

    private Method method;

    private ThreadMode threadMode;

    private Class<?> clazz;

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> clazz) {
        this.method = method;
        this.threadMode = threadMode;
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
