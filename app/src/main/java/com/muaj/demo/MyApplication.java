package com.muaj.demo;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.muaj.demo.common.InstrumentationProxy;

import java.lang.reflect.Field;

/**
 * @auth muaj
 * @date 2020/10/27.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hookActivityThreadInstrumentation();
    }


    private void hookActivityThreadInstrumentation(){
        try {
            Class<?> activityThreadClass=Class.forName("android.app.ActivityThread");
            Field activityThreadField=activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            //获取ActivityThread对象sCurrentActivityThread
            Object activityThread=activityThreadField.get(null);

            Field instrumentationField=activityThreadClass.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            //从sCurrentActivityThread中获取成员变量mInstrumentation
            Instrumentation instrumentation= (Instrumentation) instrumentationField.get(activityThread);
            //创建代理对象InstrumentationProxy
            InstrumentationProxy proxy=new InstrumentationProxy(instrumentation,getPackageManager());
            //将sCurrentActivityThread中成员变量mInstrumentation替换成代理类InstrumentationProxy
            instrumentationField.set(activityThread,proxy);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

