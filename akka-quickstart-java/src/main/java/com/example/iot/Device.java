package com.example.iot;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import javax.swing.text.html.Option;
import java.util.Optional;

public class Device extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private String groupId;
    private String deviceId;

    public Device(String deviceId, String groupId) {
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    public static Props props(String deviceId, String groupId) {
        return Props.create(Device.class, () -> new Device(deviceId, groupId));
    }

    public static final class TemperatureRecorded {
        final long requestId;

        public TemperatureRecorded(long requestId) {
            this.requestId = requestId;
        }
    }

    public static final class RecordTemperature {
        final long requestId;
        final double value;

        public RecordTemperature(long requestId, double value) {
            this.requestId = requestId;
            this.value = value;
        }
    }

    public static final class ReadTemperature {
        final long requestId;

        public ReadTemperature(long requestId) {
            this.requestId = requestId;
        }
    }

    public static final class RespondTemperature {
        final long requestId;
        final Optional<Double> value;

        public RespondTemperature(long requestId, Optional<Double> value) {
            this.requestId = requestId;
            this.value = value;
        }
    }

    Optional<Double> lastTemperatureReading = Optional.empty();

    @Override
    public void preStart() throws Exception {
        log.info("Device Actor {}-{} started", groupId, deviceId);
    }

    @Override
    public void postStop() throws Exception {
        log.info("Device Actor {}-{} stopped", groupId, deviceId);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReadTemperature.class, r -> {
                    getSender().tell(new RespondTemperature(r.requestId, lastTemperatureReading), getSelf());
                })
                .match(RecordTemperature.class, r -> {
                    log.info("Recorded temperature reading {} with {}", r.value, r.requestId);
                    lastTemperatureReading = Optional.ofNullable(r.value);
                    getSender().tell(new TemperatureRecorded(r.requestId), getSelf());
                })
                .match(RequestTrackDevice.class, r -> {
                    if (r.groupId.equals(this.groupId) && this.deviceId.equals(r.deviceId)) {
                        getSender().tell(new DeviceRegistered(), getSelf());
                    } else {
                        log.warning("Ignoring TrackDevice request for {}-{}.This actor is responsible for {}-{}.",
                                r.groupId,
                                r.deviceId,
                                this.groupId,
                                this.deviceId);
                    }
                })
                .build();
    }
}
