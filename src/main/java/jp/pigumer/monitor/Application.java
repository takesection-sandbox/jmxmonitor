package jp.pigumer.monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {
        Reporter reporter = new StdoutReporter();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture handler = scheduler.scheduleAtFixedRate(
                reporter,
                0,
                5,
                TimeUnit.SECONDS);
    }
}
