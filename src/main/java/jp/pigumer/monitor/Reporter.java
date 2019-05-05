package jp.pigumer.monitor;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.stream.Stream;

public abstract class Reporter extends MBeanServerConnectionSupplier implements Runnable {

    protected int getThreadCount(MBeanServerConnection server) {
        try {
            ThreadMXBean mx = ManagementFactory.getPlatformMXBean(server, ThreadMXBean.class);
            return mx.getThreadCount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void report(int count);

    @Override
    public void run() {
        try {
            Stream.generate(() -> get())
                    .limit(1)
                    .map(connection -> getThreadCount(connection))
                    .forEach(count -> report(count));
        } catch (Exception e) {
            reset();
            e.printStackTrace();
        }
    }
}
