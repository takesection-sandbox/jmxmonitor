package jp.pigumer.monitor;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import java.util.Date;
import java.util.stream.Stream;

public class CloudWatchReporter extends Reporter {

    private final String id = "test";

    private MBeanServerConnectionSupplier supplier = new MBeanServerConnectionSupplier();

    private AmazonCloudWatchAsync cloundwatch = AmazonCloudWatchAsyncClientBuilder
            .standard()
            .withRegion("ap-northeast-1")
            .build();

    @Override
    protected void report(int count) {
        MetricDatum metricData = new MetricDatum()
                .withDimensions(new Dimension().withName("Id").withValue(id))
                .withMetricName("thread_count")
                .withUnit(StandardUnit.Count)
                .withValue((double) count)
                .withTimestamp(new Date());
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace("CUSTOM/TEST")
                .withMetricData(metricData);
        cloundwatch.putMetricDataAsync(request);
    }

}
