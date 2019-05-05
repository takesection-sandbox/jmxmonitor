package jp.pigumer.monitor;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Reporter {

    private final MBeanServerConnection server;

    public Reporter(MBeanServerConnection server) {
        this.server = server;
    }

    protected int getThreadCount() throws IOException {
        ThreadMXBean mx = ManagementFactory.getPlatformMXBean(server, ThreadMXBean.class);
        return mx.getThreadCount();
    }
}
