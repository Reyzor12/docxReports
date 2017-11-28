package ru.main.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class TestTime {

    private static Method method;

    public static Long evaluationTime(String className, String methodName,Class[] reflectionArgs, Object[] methodArgs){
        try{
            Class c = Class.forName(className);
            Object t = c.newInstance();
            Method[] methods = c.getDeclaredMethods();
            for(Method m : methods){
                String mName = m.getName();
                if(!(mName == methodName)) continue;
                Class[] types = m.getParameterTypes();
                if(types.length != reflectionArgs.length)continue;
                boolean flag = true;
                for(int i = 0; i < types.length; i++){
                    if(!reflectionArgs[i].isAssignableFrom(types[i])){
                        flag = false;
                    }
                }
                if(!flag) continue;
                m.setAccessible(true);
                m.invoke(t,methodArgs);
                long start = System.nanoTime();
                m.invoke(t,methodArgs);
                long end = System.nanoTime();
                return end - start;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1l;
    }
}
