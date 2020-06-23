import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;

import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

public class Main {
    // choose a port number outside of Prometheus default port allocation and outside of known port ranges
    // https://github.com/prometheus/prometheus/wiki/Default-port-allocations
    // https://www.webopedia.com/quick_ref/portnumbers.asp
    private final static int PORT = 20050;

    private static final String REQUEST_TYPE_PREFIX = "ws_type_";

    private static final Gauge lastRequestTime = Gauge.build()
            .name("last_request_time")
            .help("Last request time")
            .labelNames("ws_type", "ws_method")
            .register();

    // this covers a typical request duration from 0.005 to 10 seconds
    private static final Histogram requestDurationHistogram = Histogram.build()
            .name("request_duration")
            .help("Request duration")
            //.buckets(0.1, 0.2, 0.5, 1, 5, 10) //you could also specify buckets manually
            .labelNames("ws_type", "ws_method")
            .register();

    private static void processRequest(String requestType, String requestMethod, int simulatedRequestDuration) throws InterruptedException {
        final Histogram.Timer requestHistogramTimer = requestDurationHistogram.labels(requestType, requestMethod).startTimer();
        try {
            // mock web service request
            Thread.sleep(simulatedRequestDuration);
        } finally {
            lastRequestTime.labels(requestType, requestMethod).setToCurrentTime();
            requestHistogramTimer.observeDuration();
        }
        System.out.printf("type=%s,method=%s,duration=%d\n", requestType, requestMethod, simulatedRequestDuration);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // prometheus embedded simple http server
        new HTTPServer(PORT);

        while (true) {
            // suppose we have 3 requests
            final String requestType = REQUEST_TYPE_PREFIX + ThreadLocalRandom.current().nextInt(1, 3 + 1);

            // suppose we want 80% of requests to be get requests
            final String requestMethod = (Math.random() < 0.8) ? "get" : "post";

            // simulate request duration between 1 and 16,384 ms with an exponential distribution
            final int randRequestDuration = (int) Math.pow(Math.random() + 1.0, 14);
            processRequest(requestType, requestMethod, randRequestDuration);
        }
    }
}
