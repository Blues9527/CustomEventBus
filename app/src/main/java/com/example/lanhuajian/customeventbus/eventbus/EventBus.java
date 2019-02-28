package com.example.lanhuajian.customeventbus.eventbus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBus {

    private static volatile EventBus instance;
    private Map<Object, List<SubscribeMethod>> cacheMap;

    private EventBus() {
        cacheMap = new HashMap<>();

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
        }

        cacheMap.put(obj, list);


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
                if (null == method.getAnnotation(Subscribe.class)) {
                    continue;
                }
                //限定方法传入的属性
                if (method.getParameterTypes().getClass().isAssignableFrom(Object.class)) {
                    throw new RuntimeException("parameter error");
                }

            }
            clazz = clazz.getSuperclass();
        }

//        list.add();


        return list;
    }

    public void post(Object obj) {

    }
}
