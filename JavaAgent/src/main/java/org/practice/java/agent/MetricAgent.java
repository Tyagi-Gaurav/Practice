package org.practice.java.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

public class MetricAgent {
    private static final Logger LOG = LoggerFactory.getLogger(MetricAgent.class);

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        System.out.println("Inside premain");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        LOG.info("Runtime Mx Bean Name: " + runtimeMXBean.getName());
        LOG.info("Runtime Input Arguments: " + runtimeMXBean.getInputArguments());
        LOG.info("Starting with arguments: " + agentArguments);

        MetricReporter.startMetricReporter();
        System.out.println("Started Metric Reporter");

        if (agentArguments != null) {
            Map<String, String> properties = new HashMap<>();
            for (String propertyAndValue : agentArguments.split(",")) {
                String[] tokens = propertyAndValue.split(",", 2);
                if (tokens.length != 2) {
                    continue;
                }
                properties.put(tokens[0], tokens[1]);
            }

            String graphiteHost = properties.get("graphite.host");
            if (graphiteHost != null) {
                int graphitePort = 2003;
                String graphitePrefix = properties.get("graphite.prefix");
                if (graphitePrefix != null) {
                    graphitePrefix = "test";
                }
                String graphitePortString = properties.get("graphite.port");
                if (graphitePortString != null) {
                    try {
                        graphitePort = Integer.parseInt(graphitePortString);
                    } catch (Exception e) {
                        LOG.info("Invalid Graphite Port: " + graphitePortString);
                    }
                }

                MetricReporter.startGraphiteReporter(graphiteHost, graphitePort, graphitePrefix);
            } else {
                System.out.println("No Graphite host found");
            }
        }

        instrumentation.addTransformer(new TimedClassTransformer());
    }
}