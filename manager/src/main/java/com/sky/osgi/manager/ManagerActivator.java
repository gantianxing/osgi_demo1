package com.sky.osgi.manager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by gantianxing on 2018/1/24.
 */
public class ManagerActivator implements BundleActivator{
    private Map<String,Bundle> bundleMap = new HashMap<>();
    private Thread thread;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("开始启动Bundle");

        //获取OSGI框架中已经所有Bundle
        Bundle[] bundles = bundleContext.getBundles();
        for (Bundle bundle:bundles){
            //启动server Bundle
            if(bundle.getSymbolicName().equals("com.sky.osgi.server")){
                bundle.start();
                bundleMap.put("com.sky.osgi.server",bundle);
            }
            //启动client Bundle
            if(bundle.getSymbolicName().equals("com.sky.osgi.client")){
                bundle.start();
                bundleMap.put("com.sky.osgi.client",bundle);
            }
            //为什么不启动api bundle呢？
            // 因为它不需要提供服务，也不需要与OSGI框架交互，只是作为导出api接口使用
        }

        System.out.println("所需Bundle启动结束");

        //启动线程修改server Bundle状态
        thread = new Thread(new UpdateTask());
        thread.start();

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        thread.interrupt();//停止线程
        bundleMap.clear();
    }

    class UpdateTask implements Runnable{
        @Override
        public void run() {
            Bundle server = bundleMap.get("com.sky.osgi.server");
            boolean flag = true;
            while(flag){
                try {
                    if (server.getState() != Bundle.ACTIVE){
                        server.start();
                    }else{
                        server.stop();
                    }
                } catch (BundleException e) {
                    e.printStackTrace();
                    System.out.println("Bundle启动停止出现异常");
                }

                try {
                    //模拟每隔10秒交替启动和停止Bundle
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    flag = false;
                    System.out.println("管理Bundle线程停止");
                }
            }

        }
    }
}
