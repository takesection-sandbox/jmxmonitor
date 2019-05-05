package jp.pigumer.monitor;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:9010/jmxrmi");
        System.out.println(url);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        MBeanServerConnection server = connector.getMBeanServerConnection();

        Set<ObjectInstance> mbeans = server.queryMBeans(null, null);
        mbeans.forEach(mbean -> System.out.println(mbean.toString()));

        Reporter reporter = new CloudWatchReporter(server);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture handler = scheduler.scheduleAtFixedRate(
                reporter,
                0,
                1,
                TimeUnit.MINUTES);

    }
}
