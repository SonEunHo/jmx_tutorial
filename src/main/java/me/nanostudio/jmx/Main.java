package me.nanostudio.jmx;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static CountDownLatch shutdownLatch;

    public static void main(String[] args) throws Exception {
        shutdownLatch = new CountDownLatch(1);
        start();
        shutdownLatch.await();
    }

    private static void start() throws Exception {
        System.out.println("process started");
        initMBeanServer();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("shutdown hook invoked");
                shutdownLatch.countDown();
            }
        });
    }

    private static void initMBeanServer() throws Exception{
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("me.nanostudio.jmx:type=Hello");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        server.registerMBean(new Hello(), objectName);
    }

    public interface HelloMBean {
        void doTask();
    }

    static class Hello implements HelloMBean {
        @Override
        public void doTask() {
            System.out.println("do Task");
        }
    }
}
