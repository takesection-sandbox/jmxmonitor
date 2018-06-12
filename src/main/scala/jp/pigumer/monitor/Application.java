package jp.pigumer.monitor;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.concurrent.*;

public class Application {

    public static void main(String[] args) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectInstance> mbeans = server.queryMBeans(null, null);
        mbeans.forEach(mbean -> System.out.println(mbean.toString()));

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture handler = scheduler.scheduleAtFixedRate(
                new CloudWatchReporter("test"),
                0,
                1,
                TimeUnit.MINUTES);

        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(60000);
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }).start();
            try {
                Thread.sleep(30000);
            } catch (Exception e) {
                // ignore
            }
        }
        handler.cancel(false);
        System.out.println("exit");
    }
}
