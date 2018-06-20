package jp.pigumer.monitor;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class CloudWatchReporter implements Runnable {

    private AmazonCloudWatchAsync cloundwatch = AmazonCloudWatchAsyncClientBuilder
            .standard()
            .withRegion("ap-northeast-1")
            .build();
    private String id;

    public CloudWatchReporter(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        Double count = (double) ManagementFactory.getThreadMXBean().getThreadCount();
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
    }
}
