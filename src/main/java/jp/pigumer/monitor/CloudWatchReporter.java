package jp.pigumer.monitor;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.util.Date;

public class CloudWatchReporter extends Reporter implements Runnable {

    private AmazonCloudWatchAsync cloundwatch = AmazonCloudWatchAsyncClientBuilder
            .standard()
            .withRegion("ap-northeast-1")
            .build();
    private String id;

    public CloudWatchReporter(MBeanServerConnection server) {
        super(server);
    }

    @Override
    public void run() {
        try {
            Double count = (double) getThreadCount();
            MetricDatum metricData = new MetricDatum()
                    .withDimensions(new Dimension().withName("Id").withValue(id))
                    .withMetricName("thread_count")
                    .withUnit(StandardUnit.Count)
                    .withValue(count)
                    .withTimestamp(new Date());
            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace("CUSTOM/TEST")
                    .withMetricData(metricData);
            cloundwatch.putMetricDataAsync(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
