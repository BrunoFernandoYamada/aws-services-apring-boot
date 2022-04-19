//[AWS - ClOUDWATCH] [STEP 2] Metric class
package br.com.byamada.awsservicesapringboot.metrics;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MetricRegistrion {

    private static final String NAMESPACE = "order-metrics";

    @Autowired
    private AmazonCloudWatch amazonCloudWatch;

    //[AWS - ClOUDWATCH] [STEP 3] Metric method example
    public void registerOrderCreationMetric() {

        List<Dimension> dimension = Collections.emptyList();

        MetricDatum datum = new MetricDatum()
                .withMetricName("ORDER_CREATED")
                .withUnit(StandardUnit.Count)
                .withValue((double) 1)
                .withDimensions(dimension);

        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(NAMESPACE)
                .withMetricData(datum);

        PutMetricDataResult response = amazonCloudWatch.putMetricData(request);

        if(response.getSdkHttpMetadata().getHttpStatusCode() != 200){
            log.error("Error sending metric: {}", response);
        }
    }




}
