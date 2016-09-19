package org.practice.java.agent;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class MetricReporter {
    private static final Logger LOG = LoggerFactory.getLogger(MetricReporter.class);

    private static MetricRegistry metricRegistry = new MetricRegistry();


    public static void startMetricReporter() {
        LOG.info("Init JMX Reporter");
        System.out.println("Init JMX Reporter");

        JmxReporter jmxReporter = JmxReporter
                .forRegistry(metricRegistry)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .convertRatesTo(TimeUnit.MINUTES)
                .build();
        jmxReporter.start();
    }

    public static void startGraphiteReporter(String graphiteHost, int graphitePort, String graphitePrefix) {
        LOG.info("Init Graphite Reporter: host={}, port={}, prefix={}", graphiteHost, graphitePort, graphitePrefix);
        Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
        GraphiteReporter graphiteReporter =
                GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith(graphitePrefix)
                .build(graphite);
        graphiteReporter.start(1, TimeUnit.MINUTES);
    }

    public static void reportTime(String name, long timeInMs) {
        System.out.println("Reporting time");
        Timer timer = metricRegistry.timer(name);
        timer.update(timeInMs, TimeUnit.MILLISECONDS);
    }
}
