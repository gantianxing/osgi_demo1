package com.sky.osgi.server.impl;


import com.sky.osgi.api.HelloService;

/**
 * Created by gantianxing on 2017/12/15.
 */
public class HelloServiceImpl implements HelloService {

    private String name;

    public HelloServiceImpl(String name) {
        this.name = name;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello,"+name);
    }
}
