package com.sky.osgi.client;

import com.sky.osgi.api.HelloService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created by gantianxing on 2018/1/23.
 */
public class TestTask implements Runnable{
    public ServiceTracker serviceTracker;

    public TestTask(ServiceTracker serviceTracker) {
        this.serviceTracker = serviceTracker;
    }

    @Override
    public void run() {
            boolean flag = true;
            while (flag){
                try {
                    HelloService helloService = (HelloService)serviceTracker.getService();
                    if (helloService!= null) {
                        helloService.sayHello();
                    } else {
                        System.out.println("没有可用的服务");
                    }
                }catch (Exception e){
                    System.out.println("业务异常");
                }

                //睡5秒重试
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("线程池关闭");
                    flag = false;
                }
            }
    }
}
