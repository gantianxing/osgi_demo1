package com.sky.osgi.client;

import com.sky.osgi.api.HelloService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Created by gantianxing on 2018/1/23.
 */
public class MyServiceTrackerCustomizer implements ServiceTrackerCustomizer {
    private BundleContext context;

    public MyServiceTrackerCustomizer(BundleContext context) {
        this.context = context;
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        HelloService helloService = (HelloService)context.getService(serviceReference);
        //返回一个包装后的服务
        return new MyHelloService(helloService);
    }

    @Override
    public void modifiedService(ServiceReference serviceReference, Object o) {
        System.out.println("服务被修改了");
    }

    @Override
    public void removedService(ServiceReference serviceReference, Object o) {
        System.out.println("服务被移除了");
    }
}

class MyHelloService implements HelloService {
    private HelloService helloService;

    public MyHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public void sayHello() {
        System.out.println("包装服务开始");
        helloService.sayHello();
        System.out.println("包装服务结束");
    }
}
