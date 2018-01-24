package com.sky.osgi.server;

import com.sky.osgi.api.HelloService;
import com.sky.osgi.server.impl.HelloServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Properties;

/**
 * Created by gantianxing on 2017/12/15.
 */
public class ServerAtivictor implements BundleActivator{
    private BundleContext context;

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

        //元数据，客户端在查询服务是可以根据元数据过滤
        Dictionary dictionary = new Properties();
        dictionary.put("test","test");
        //注册服务
        ServiceRegistration registration = context.registerService(HelloService.class.getName(),new HelloServiceImpl("小明"),dictionary);
        System.out.println("start server bundle");

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("stop server bundle");
    }
}
