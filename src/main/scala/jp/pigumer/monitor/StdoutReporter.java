package jp.pigumer.monitor;

import javax.management.MBeanServerConnection;
import java.io.IOException;

public class StdoutReporter extends Reporter {

    public StdoutReporter(MBeanServerConnection server) {
        super(server);
    }

    @Override
    public void run() {
        try {
            int count = getThreadCount();
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
