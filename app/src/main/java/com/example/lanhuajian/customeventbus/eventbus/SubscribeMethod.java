package com.example.lanhuajian.customeventbus.eventbus;

import java.lang.reflect.Method;

public class SubscribeMethod {

    private Method[] methods;

    private ThreadMode threadMode;

    private Class<?> clazz;

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
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
