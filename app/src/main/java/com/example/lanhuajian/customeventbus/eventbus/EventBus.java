package com.example.lanhuajian.customeventbus.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {

    private static volatile EventBus instance;
    private Map<Object, List<SubscribeMethod>> cacheMap;
    private Handler mHandler;
    private ExecutorService mExecutor;

    private EventBus() {
        cacheMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutor = Executors.newCachedThreadPool();

    }

    public static EventBus getDefault() {
        if (null == instance) {
            synchronized (EventBus.class) {
                if (null == instance) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    //obj 对应的是 activity 或者是 fragment
    public void register(Object obj) {

        List<SubscribeMethod> list = cacheMap.get(obj);

        if (list == null) {
            list = findSubscribeMethod(obj);
            cacheMap.put(obj, list);
        }


    }


    private List<SubscribeMethod> findSubscribeMethod(Object obj) {

        List<SubscribeMethod> list = new ArrayList<>();

        //通过反射去获取对应的类
        Class<?> clazz = obj.getClass();

        //判断其父类或者父接口是否也有符合要求的方法
        while (clazz != null) {

            //如果父类或者父接口是系统类，则跳出循环
            if (clazz.getName().startsWith("java.")
                    || clazz.getName().startsWith("javax.")
                    || clazz.getName().startsWith("android.")) {
                break;

            }

            //然后获取类中所有的方法
            Method[] methods = clazz.getMethods();

            //遍历所有的方法
            for (Method method : methods) {
                //判断是否有@Subscribe
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                Class<?>[] types = method.getParameterTypes();
                //限定方法传入的属性
                if (types.length != 1) {
                    throw new RuntimeException("parameter error");
                }
                ThreadMode threadMode = subscribe.threadMode();

                SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, types[0]);

                list.add(subscribeMethod);


            }
            clazz = clazz.getSuperclass();
        }


        return list;
    }

    public void post(final Object type) {

        Set<Object> set = cacheMap.keySet();

        for (final Object object : set) {

            List<SubscribeMethod> list = cacheMap.get(object);

            for (final SubscribeMethod subscribeMethod : list) {

                if (subscribeMethod.getClazz().isAssignableFrom(type.getClass())) {
                    switch (subscribeMethod.getThreadMode()) {
                        case SUB_THREAD:
                            //主 -> 子
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                mExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod, object, type);
                                    }
                                });
                            } else {
                                //子 -> 子
                                invoke(subscribeMethod, object, type);
                            }



                            break;
                        case MAIN_THREAD:
                            //主 -> 主
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribeMethod, object, type);
                            } else {
                                //子 -> 主
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod, object, type);
                                    }
                                });
                            }

                            break;
                    }
                }
            }

        }

    }


    private void invoke(SubscribeMethod subscribeMethod, Object object, Object type) {
        Method method = subscribeMethod.getMethod();
        try {
            //通过反射去执行改方法
            method.invoke(object, type);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
