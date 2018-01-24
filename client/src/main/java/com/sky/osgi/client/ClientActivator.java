package com.sky.osgi.client;

import com.sky.osgi.api.HelloService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gantianxing on 2018/1/23.
 */
public class ClientActivator implements BundleActivator {

    private ServiceTracker serviceTracker;
    private ExecutorService executorService;

    @Override
    public void start(BundleContext context) throws Exception {
        //创建一个不带定制器的 追踪器
        serviceTracker = new ServiceTracker(context, HelloService.class.getName(), new MyServiceTrackerCustomizer(context));
        serviceTracker.open();//打开追踪器

        //新开一个线程，模拟调用服务
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new TestTask(serviceTracker));
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        serviceTracker.close();//关闭追踪器
        executorService.shutdown();
    }
}
