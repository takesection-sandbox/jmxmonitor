package jp.pigumer.monitor;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.concurrent.CompletableFuture;

public class MBeanServerConnectionSupplier {

    private MBeanServerConnection connection = null;

    private MBeanServerConnection connect(String url) {
        if (connection != null) {
            return connection;
        }
        try {
            JMXServiceURL serviceURL = new JMXServiceURL(url);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
            connection = connector.getMBeanServerConnection();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void reset() {
        connection = null;
    }

    protected MBeanServerConnection get() {
        String url = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9010/jmxrmi";
        return connect(url);
    }
}
